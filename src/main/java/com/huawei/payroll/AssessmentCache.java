package com.huawei.payroll;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import com.huawei.jdbc.DatabaseType;
import com.huawei.jdbc.JsonPathType;
import com.huawei.jdbc.ObjectType;
import com.huawei.jdbc.mapper.SchemaMapper;
import com.huawei.jdbc.pojo.DatabaseObject;
import com.huawei.jdbc.pojo.TableStatistic;
import com.huawei.jdbc.pojo.ValidateResult;
import com.huawei.json.JsonUtil;
import com.huawei.shell.ExecUtil;

public class AssessmentCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssessmentCache.class);

    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private static Environment env;

    private static volatile SchemaMapper schemaMapper;

    private static Map<String, DatabaseObject> databaseObjects = new HashMap<>();

    private static Map<String, String> databaseObjectDefinitions = new HashMap<>();

    private static List<TableStatistic> tableStatistics = new ArrayList<>();

    private static Map<String, ValidateResult> validateResults = new HashMap<>();

    public static Map<String, ValidateResult> getValidateResults() {
        return validateResults;
    }

    public static Map<String, DatabaseObject> getDatabaseObjects() {
        return databaseObjects;
    }

    public static Map<String, String> getDatabaseObjectDefinitions() {
        return databaseObjectDefinitions;
    }

    public static List<TableStatistic> getTableStatistics() {
        return tableStatistics;
    }

    public static void schemaMapperInit(SchemaMapper schemaMapper) {
        AssessmentCache.schemaMapper = schemaMapper;
    }

    public static void envInit(Environment env) {
        AssessmentCache.env = env;
    }

    public static boolean initSuccess() {
        return atomicInteger.get() >= 0;
    }

    private static Map<String, ValidateResult> validate() {
        return AssessmentCache.getDatabaseObjectDefinitions().keySet().stream()
                .map(AssessmentCache::validate)
                .collect(Collectors.toMap(ValidateResult::getId, Function.identity()));
    }

    public static ValidateResult validate(String id) {
        final Map<String, String> databaseObjectDefinitions = AssessmentCache.getDatabaseObjectDefinitions();
        final String sql = databaseObjectDefinitions.get(id);
        String type = Optional.ofNullable(AssessmentCache.getDatabaseObjects().get(id))
                .map(DatabaseObject::getType)
                .orElse(ObjectType.OTHER.getName());
        Map.Entry<Boolean, String> openGaussResult = ExecUtil.sqlValidate(sql, DatabaseType.OPENGAUSS);
        Map.Entry<Boolean, String> oracleResult = ExecUtil.sqlValidate(sql, DatabaseType.ORACLE);
        return new ValidateResult().setId(id).setSql(sql).setSqlType(type).setSourceDatabaseType(DatabaseType.ORACLE)
                .setTargetDatabaseType(DatabaseType.OPENGAUSS)
                .setTarget(openGaussResult.getKey())
                .setTargetError(openGaussResult.getValue())
                .setExtension(oracleResult.getKey())
                .setExtensionError(oracleResult.getValue());
    }

    public static void refresh(String ddlId, String ddlSQL) {
        AssessmentCache.databaseObjectDefinitions.put(ddlId, ddlSQL);
        AssessmentCache.validateResults.put(ddlId, validate(ddlId));
    }

    static void loadStatistic(){
        LOGGER.info("start init AssessmentCache.tableStatistics");
        final Optional<Map<String, TableStatistic>> stringTableStatisticMap = JsonUtil.jsonLoadMap(
                JsonPathType.STATISTIC.getPath(), TableStatistic.class);
        AssessmentCache.tableStatistics = new ArrayList<>(
                stringTableStatisticMap.orElse(Collections.emptyMap()).values());
        LOGGER.info("init AssessmentCache.tableStatistics success! AssessmentCache.tableStatistics.size()="
                + AssessmentCache.tableStatistics.size());
        AssessmentCache.atomicInteger.addAndGet(1);
    }

    private static void loadDatabaseObjects() {
        LOGGER.info("start init AssessmentCache.databaseObjects");
        Optional<Map<String, DatabaseObject>> stringDatabaseObjectMap = JsonUtil.jsonLoadMap(
                JsonPathType.SUMMARY.getPath(), DatabaseObject.class, () -> schemaMapper.selectDatabaseObject()
                        .parallelStream()
                        .collect(Collectors.toMap(databaseObject -> String.valueOf(databaseObject.getId()),
                                Function.identity())));
        AssessmentCache.databaseObjects = stringDatabaseObjectMap.orElse(Collections.emptyMap());
        LOGGER.info("init AssessmentCache.databaseObjects success! AssessmentCache.databaseObjects.size()="
                + AssessmentCache.databaseObjects.size());
        AssessmentCache.atomicInteger.addAndGet(1);
    }

    private static void loadDatabaseObjectDefinition() {
        LOGGER.info("start init AssessmentCache.databaseObjectDefinitions");
        Optional<Map<String, String>> stringTMap = JsonUtil.jsonLoadMap(JsonPathType.OBJECT_SQL.getPath(),
                String.class, () -> {
                    Map<String, String> cache = new ConcurrentHashMap<>();
                    List<Long> longs = schemaMapper.selectDistinctId();
                    LOGGER.info("sql ids.size():" + longs.size());
                    longs.parallelStream().forEach(id -> {
                        try {
                            String s = schemaMapper.selectDefinitionById(id);
                            LOGGER.info("search id :" + id + " success");
                            cache.put(String.valueOf(id), s);
                        } catch (RuntimeException e) {
                            LOGGER.info("selectDefinitionById failed, id = " + id);
                        }
                    });
                    return cache;
                });
        AssessmentCache.databaseObjectDefinitions = stringTMap.orElse(Collections.emptyMap());
        LOGGER.info(
                "init AssessmentCache.databaseObjectDefinitions success! AssessmentCache.databaseObjectDefinitions.size()="
                        + AssessmentCache.databaseObjectDefinitions.size());
        AssessmentCache.atomicInteger.addAndGet(1);

    }

    private static void loadValidateResult() {
        while (atomicInteger.get() < 3) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                LOGGER.error("sleep failed: {}", e.getMessage());
            }
        }

        try {
            String jsonPath = JsonPathType.VALIDATE_RESULT.getPath();
            final Map<String, ValidateResult> validate = AssessmentCache.validate();
            JsonUtil.jsonSave(validate, jsonPath);
            AssessmentCache.validateResults = validate;
        } catch (RuntimeException e) {
            LOGGER.error(e.getMessage());
        }
        AssessmentCache.atomicInteger.addAndGet(1);
    }

    @PostConstruct
    public void initCache() {
        new Thread(AssessmentCache::loadStatistic).start();
        if ("oracle.jdbc.OracleDriver".equalsIgnoreCase(env.getProperty("spring.datasource.driver-class-name"))) {
            AssessmentCache.databaseObjectDefinitions = OracleUtil.initObjectDDL();
            AssessmentCache.databaseObjects = OracleUtil.initObjectDetails();
            AssessmentCache.atomicInteger.addAndGet(2);
        } else {
            new Thread(AssessmentCache::loadDatabaseObjects).start();
            new Thread(AssessmentCache::loadDatabaseObjectDefinition).start();
        }
        new Thread(AssessmentCache::loadValidateResult).start();
    }

    @Resource
    public void setSchemaMapper(SchemaMapper schemaMapper) {
        schemaMapperInit(schemaMapper);
    }

    @Resource
    public void setEnv(Environment env) {
        envInit(env);
    }
}
