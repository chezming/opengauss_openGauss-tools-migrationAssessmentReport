/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.huawei.jdbc.pojo.DBAObject;
import com.huawei.jdbc.pojo.DatabaseObject;
import com.huawei.jdbc.pojo.DatabaseVersion;
import com.huawei.jdbc.pojo.OSState;
import com.huawei.jdbc.pojo.TableStatistic;
import com.huawei.jdbc.pojo.DataFiles;
import com.huawei.jdbc.pojo.DbInfo;

/**
 * This interface defines how to get statistics from database.
 */
@Mapper
public interface SchemaMapper {
    /**
     * Get line count for every table
     *
     * @return a list of TableStatistic
     */
    List<TableStatistic> selectLineCount();

    /**
     * Get all objects in the database
     *
     * @return a list of DBAObject
     */
    List<DBAObject> findByState();

    /**
     * Get all objects accessible to the current user
     *
     * @return a list of DatabaseObject
     */
    List<DatabaseObject> selectDatabaseObject();

    /**
     * Get original DDL statement by object id
     *
     * @param id object id
     * @return original DDL statement
     */
    String selectDefinitionById(@Param("id") long id);

    /**
     * Get distinct objects id
     *
     * @return a list of objects id
     */
    List<Long> selectDistinctId();

    /**
     * Get current database version
     *
     * @return DatabaseVersion
     */
    DatabaseVersion databaseVersion();

    /**
     * Get current system version
     * @return a list of OSState
     */
    List<OSState> systemVersion();

    /**
     * Get data files info in current database
     *
     * @return a list if DataFiles
     */
    List<DataFiles> dbaDataFiles();

    /**
     * Get RAC status of current database
     *
     * @return RAC status string
     */
    String racStatus();

    /**
     * Get the IP address of current database
     *
     * @return IP string
     */
    String ip();

    /**
     * Generate AWR report
     *
     * @param dbid database id
     * @param instanceNum instance num
     * @param bid begin snapshot id
     * @param eid end snapshot id
     * @return HTML format AWR report string
     */
    List<String> generateAwr(@Param("dbid") long dbid, @Param("instanceNum") long instanceNum,
        @Param("bid") long bid, @Param("eid") long eid);

    /**
     * Get database basic info
     *
     * @return DbInfo
     */
    DbInfo getDbInfo();

    /**
     * Get database instance num
     *
     * @return instance num
     */
    Long getInstanceNum();

    /**
     * Get latest two snapshot id
     *
     * @param dbid database id
     * @return two snapshot id
     */
    List<Long> getSnapId(@Param("dbid") long dbid);
}
