package com.huawei.rest;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.jdbc.mapper.SchemaMapper;
import com.huawei.jdbc.pojo.DataFiles;
import com.huawei.jdbc.pojo.DatabaseVersion;
import com.huawei.jdbc.pojo.DbInfo;
import com.huawei.jdbc.pojo.OSState;
import com.huawei.jdbc.pojo.SystemDetail;

@RestController
@RequestMapping("/home")
public class HomePageRest {
    @Resource
    private SchemaMapper schemaMapper;

    private SystemDetail systemDetail;

    @Resource
    private Environment environment;

    private void generateAwrReport(Long dbId, Long instanceNum, List<Long> snapIds) {
        final String awrPath = "./target/classes/static/awr.html";
        try {
            FileWriter writer = new FileWriter(awrPath);
            writer.write("The AWR report is generating, please wait...\n");
            writer.flush();
            writer.close();

            if (snapIds.size() == 2) {
                final List<String> awrLines = schemaMapper.generateAwr(dbId, instanceNum, snapIds.get(1),
                        snapIds.get(0));

                writer = new FileWriter(awrPath);
                for (String awrLine : awrLines) {
                    if (awrLine != null) {
                        writer.write(awrLine + "\n");
                    } else {
                        writer.write("\n");
                    }
                }

            } else {
                writer = new FileWriter(awrPath);
                writer.write("The number of history snapshot is less than 2, can't generate AWR report!\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
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
        final Matcher matcher = pattern.matcher(datasourceUrl);
        if (matcher.matches()) {
            systemDetail.setIp(matcher.group(1));
            systemDetail.setPort(matcher.group(2));
        }
        final List<OSState> osStates = schemaMapper.systemVersion();
        final DatabaseVersion databaseVersion = schemaMapper.databaseVersion();
        final List<DataFiles> dataFiles = schemaMapper.dbaDataFiles();
        final String racStatus = schemaMapper.racStatus();
        final DbInfo dbInfo = schemaMapper.getDbInfo();

        systemDetail.setInstanceName(dbInfo.getInstanceName());
        if (racStatus.equalsIgnoreCase("TRUE")) {
            systemDetail.setDeployStructure("RAC");
        } else {
            if (dbInfo.getSwitchoverStatus().equalsIgnoreCase("NOT ALLOWED")) {
                systemDetail.setDeployStructure("单机");
            } else {
                systemDetail.setDeployStructure("主备");
            }
        }

        systemDetail.setDatabaseVersion(databaseVersion.getFullVersion());
        systemDetail.setDatabaseName(databaseVersion.getProduct());
        long idleTime = 0;
        long busyTime = 0;
        //        final String ip = schemaMapper.ip();
        for (OSState osState : osStates) {
            switch (osState.getSTAT_NAME()) {
                case "NUM_CPUS":
                    systemDetail.setNumOfCpus(osState.getVALUE());
                    break;
                case "NUM_CPU_CORES":
                    systemDetail.setNumOfCpuCores(osState.getVALUE());
                    break;
                case "PHYSICAL_MEMORY_BYTES": {
                    final long value = Long.parseLong(osState.getVALUE()) / 1024 / 1024 / 1024;
                    systemDetail.setPhysicalMem(value + "GB");
                    break;
                }
                case "INACTIVE_MEMORY_BYTES": {
                    final long value = Long.parseLong(osState.getVALUE()) / 1024 / 1024 / 1024;
                    systemDetail.setVisualMem(value + "GB");
                    break;
                }
                case "LOAD": {
                    systemDetail.setLoad(osState.getVALUE());
                    break;
                }
                case "IDLE_TIME": {
                    idleTime = Long.parseLong(osState.getVALUE());
                    break;
                }
                case "BUSY_TIME": {
                    busyTime = Long.parseLong(osState.getVALUE());
                    break;
                }
                default:
                    break;
            }
        }

        if (idleTime != 0 && busyTime != 0) {
            final String utilization = String.format("%.2f%%", ((float) busyTime / (idleTime + busyTime)) * 100);
            systemDetail.setUtilization(utilization);
        } else {
            systemDetail.setUtilization("N/A");
        }

        systemDetail.setDataFiles(dataFiles);

        final Long dbId = dbInfo.getDbId();
        new Thread(() -> generateAwrReport(dbId, schemaMapper.getInstanceNum(), schemaMapper.getSnapId(dbId))).start();
        return this.systemDetail;
    }
}


