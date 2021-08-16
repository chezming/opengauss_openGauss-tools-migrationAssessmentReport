/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huawei.jdbc.ObjectType;
import com.huawei.jdbc.mapper.SchemaMapper;
import com.huawei.jdbc.pojo.Compatibility;
import com.huawei.jdbc.pojo.DatabaseObject;
import com.huawei.jdbc.pojo.Pair;
import com.huawei.jdbc.pojo.SQLDetail;
import com.huawei.jdbc.pojo.TableStatistic;
import com.huawei.jdbc.pojo.ValidateResult;
import com.huawei.payroll.AssessmentCache;

/**
 * Rest controller of the summary page
 */
@RestController
@RequestMapping("/summary")
public class IndexPageRest {
    @Resource
    private SchemaMapper schemaMapper;

    @RequestMapping(value = "object/validate/{id}")
    public ValidateResult validation(@PathVariable String id) {
        return AssessmentCache.getValidateResults().get(id);
    }

    @RequestMapping(value = "objects")
    public List<DatabaseObject> getAllObject() {
        Map<String, ValidateResult> validateResults = AssessmentCache.getValidateResults();
        for (DatabaseObject databaseObject : AssessmentCache.getDatabaseObjects().values()) {
            databaseObject.setValidateResult(validateResults.get(String.valueOf(databaseObject.getId())));
        }
        return new ArrayList<>(AssessmentCache.getDatabaseObjects().values());
    }

    @RequestMapping(value = "object/definition/{id}")
    public Map.Entry<Long, String> objectDefinition(@PathVariable long id) {
        return new Pair<Long, String>(id, AssessmentCache.getDatabaseObjectDefinitions().get(String.valueOf(id)));
    }

    @RequestMapping(value = "object/tablestatistic")
    public List<TableStatistic> tableStatistic() {
        return AssessmentCache.getTableStatistics();
    }

    @RequestMapping(value = "object/sqldetails")
    public List<SQLDetail> sqlDetails() {
        return AssessmentCache.getDatabaseObjects().values().stream().map(databaseObject -> {
            SQLDetail sqlDetail = new SQLDetail();
            sqlDetail.setDefinition(
                    AssessmentCache.getDatabaseObjectDefinitions().get(String.valueOf(databaseObject.getId())));
            sqlDetail.setId(databaseObject.getId());
            sqlDetail.setObjectName(databaseObject.getObjectName());
            sqlDetail.setSchema(databaseObject.getSchema());
            return sqlDetail;
        }).collect(Collectors.toList());
    }

    @RequestMapping(value = "object/validation")
    public List<ValidateResult> validation() {
        return new ArrayList<>(AssessmentCache.getValidateResults().values());
    }

    @RequestMapping(value = "object/sqlcompatibility")
    public List<Compatibility> sqlCompatibility() {
        Map<ObjectType, Compatibility> map = new TreeMap<>();
        for (ObjectType objectType : ObjectType.values()) {
            if (objectType != ObjectType.TOTAL) {
                map.put(objectType, new Compatibility(objectType));
            }
        }
        for (Map.Entry<String, DatabaseObject> entry : AssessmentCache.getDatabaseObjects().entrySet()) {
            ValidateResult validateResult = AssessmentCache.getValidateResults().get(entry.getKey());
            DatabaseObject databaseObject = entry.getValue();
            ObjectType objectType = ObjectType.getObjectType(databaseObject.getType().toUpperCase());
            Compatibility compatibility = map.get(objectType);
            compatibility.setTotal(compatibility.getTotal() + 1);
            if (validateResult == null) {
                continue;
            }

            compatibility.setOriginError(compatibility.getOriginError() + 1);
            if (validateResult.isTarget()) {
                compatibility.setTargetSupport(compatibility.getTargetSupport() + 1);
            }
            if (validateResult.isExtension()) {
                compatibility.setExtensionSupport(compatibility.getExtensionSupport() + 1);
            }
        }

        Compatibility totalComp = new Compatibility(ObjectType.TOTAL);

        for (Compatibility com : map.values()) {
            com.setConversionRate();
            totalComp.setTotal(totalComp.getTotal() + com.getTotal());
            totalComp.setOriginError(totalComp.getOriginError() + com.getOriginError());
            totalComp.setExtensionSupport(totalComp.getExtensionSupport() + com.getExtensionSupport());
            totalComp.setTargetSupport(totalComp.getTargetSupport() + com.getTargetSupport());
        }
        totalComp.setConversionRate();
        map.put(ObjectType.TOTAL, totalComp);
        return new ArrayList<>(map.values());
    }
}
