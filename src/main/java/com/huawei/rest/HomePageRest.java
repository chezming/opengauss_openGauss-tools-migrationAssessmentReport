/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.rest;

import com.huawei.jdbc.mapper.SchemaMapper;
import com.huawei.jdbc.pojo.DatabaseVersion;
import com.huawei.jdbc.pojo.DbInfo;
import com.huawei.jdbc.pojo.OSState;
import com.huawei.jdbc.pojo.SystemDetail;
import com.huawei.payroll.AssessmentCache;
import com.huawei.util.ParseOsState;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * Rest controller of the home page
 */
@RestController
@RequestMapping("/home")
public class HomePageRest {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentCache.class);

    @Resource
    private SchemaMapper schemaMapper;

    private SystemDetail systemDetail;

    @Resource
    private Environment environment;

    private void generateAwrReport(Long dbId, Long instanceNum, List<Long> snapIds) {
        final String awrPath = "./target/classes/static/awr.html";
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(awrPath), UTF_8)) {
            if (snapIds.size() == 2) {
                writer.write("The AWR report is generating, please wait...\n");
            } else {
                writer.write("The number of history snapshot is less than 2, can't generate AWR report!\n");
                return;
            }
        } catch (IOException e) {
            LOGGER.error("write awr failed, error: {}", e.getMessage());
            return;
        }
        List<String> awrLines = schemaMapper.generateAwr(dbId, instanceNum, snapIds.get(1), snapIds.get(0));
        try (OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(awrPath), UTF_8)) {
            writer.write("<meta charset=\"UTF-8\"/>\n");
            for (String awrLine : awrLines) {
                if (awrLine != null) {
                    writer.write(awrLine);
                }
                writer.write("\n");
            }
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }

    @RequestMapping(value = "overview")
    public Object overview() {
        if (this.systemDetail != null) {
            return this.systemDetail;
        }
        SystemDetail systemDetail = new SystemDetail();
        this.systemDetail = systemDetail;
        final String datasourceUrl = environment.getProperty("spring.datasource.url");
        Pattern pattern = Pattern.compile("[a-zA-Z:@]+([0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+):([0-9]+):.*");
        Matcher matcher = pattern.matcher(datasourceUrl == null ? Strings.EMPTY : datasourceUrl);
        if (matcher.matches()) {
            systemDetail.setIp(matcher.group(1));
            systemDetail.setPort(matcher.group(2));
        }
        final List<OSState> osStates = schemaMapper.systemVersion();
        final DatabaseVersion databaseVersion = schemaMapper.databaseVersion();
        final String racStatus = schemaMapper.racStatus();
        final DbInfo dbInfo = schemaMapper.getDbInfo();

        systemDetail.setInstanceName(dbInfo.getInstanceName());
        if (racStatus.equalsIgnoreCase("TRUE")) {
            systemDetail.setDeployStructure("RAC");
        } else {
            if (dbInfo.getSwitchoverStatus().equalsIgnoreCase("NOT ALLOWED")) {
                systemDetail.setDeployStructure("SINGLE");
            } else {
                systemDetail.setDeployStructure("PRIMARY/STANDBY");
            }
        }

        systemDetail.setDatabaseVersion(databaseVersion.getFullVersion());
        systemDetail.setDatabaseName(databaseVersion.getProduct());

        for (OSState osState : osStates) {
            String statName = osState.getStatName();
            ParseOsState.call(statName).ifPresent(consumer -> consumer.accept(systemDetail, osState.getValue()));
        }

        final long idleTime = Long.parseLong(systemDetail.getIdleTime());
        final long busyTime = Long.parseLong(systemDetail.getBusyTime());
        if (idleTime != 0 && busyTime != 0) {
            final String utilization = String.format("%.2f%%", ((float) busyTime / (idleTime + busyTime)) * 100);
            systemDetail.setUtilization(utilization);
        } else {
            systemDetail.setUtilization("N/A");
        }

        systemDetail.setDataFiles(schemaMapper.dbaDataFiles());

        final Long dbId = dbInfo.getDbId();
        new Thread(() -> generateAwrReport(dbId, schemaMapper.getInstanceNum(), schemaMapper.getSnapId(dbId))).start();
        return this.systemDetail;
    }
}


