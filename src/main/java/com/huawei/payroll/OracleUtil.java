package com.huawei.payroll;

import com.huawei.jdbc.ObjectType;
import com.huawei.jdbc.pojo.DatabaseObject;
import com.huawei.json.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.huawei.jdbc.pojo.PathConst.DDL_DIR;
import static com.huawei.jdbc.pojo.PathConst.OBJECT_DETAILS;

public class OracleUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentCache.class);

    private static final Pattern pattern = Pattern.compile("^([0-9]+)\\.sql");

    public static Map<String, String> initObjectDDL() {
        Map<String, String> map = new HashMap<>();
        File[] files = new File(DDL_DIR).listFiles();
        if (files == null) {
            LOGGER.error("{}  path is empty!", DDL_DIR);
            return Collections.emptyMap();
        }
        for (File file : files) {
            File[] subFiles = file.listFiles();
            if (subFiles == null) {
                LOGGER.error("{}  path is empty!", file.getAbsolutePath());
                return Collections.emptyMap();
            }
            for (File ddlFile : subFiles) {
                Matcher matcher = pattern.matcher(ddlFile.getName());
                if (matcher.matches()) {
                    final String id = matcher.group(1);
                    DefinitionPathCache.INSTANCE.putDDLPath(id, ddlFile.getAbsolutePath());
                    map.put(id, readObjectDDL(ddlFile));
                }
            }
        }
        return map;
    }

    public static Map<String, DatabaseObject> initObjectDetails() {
        Map<String, DatabaseObject> result = new HashMap<>();
        for (ObjectType objectType : ObjectType.values()) {
            String objectTypePath = String.format(OBJECT_DETAILS, objectType.getName());
            if (new File(objectTypePath).exists()) {
                JsonUtil.jsonLoadMap(objectTypePath, DatabaseObject.class).ifPresent(result::putAll);
            }
        }
        return result;
    }

    private static String readObjectDDL(File file) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
            return stringBuilder.toString();
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
        return "";
    }

    public static void refreshDDL(File file) {
        final Matcher matcher = pattern.matcher(file.getName());
        if (matcher.matches()) {
            final String ddlId = matcher.group(1);
            final String ddlSQL = readObjectDDL(file);
            AssessmentCache.refresh(ddlId, ddlSQL);
        }
    }
}
