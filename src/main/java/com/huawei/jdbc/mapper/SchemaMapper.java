package com.huawei.jdbc.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.huawei.jdbc.pojo.DBAObject;
import com.huawei.jdbc.pojo.DatabaseObject;
import com.huawei.jdbc.pojo.DatabaseVersion;
import com.huawei.jdbc.pojo.OSState;
import com.huawei.jdbc.pojo.TableStatistic;
import com.huawei.jdbc.pojo.DataFiles;
import com.huawei.jdbc.pojo.DbInfo;

@Mapper
public interface SchemaMapper {
    List<TableStatistic> selectLineCount();

    List<DBAObject> findByState();

    List<DatabaseObject> selectDatabaseObject();

    String selectDefinitionById(@Param("id") long id);

    List<Long> selectDistinctId();

    DatabaseVersion databaseVersion();

    List<OSState> systemVersion();

    List<DataFiles> dbaDataFiles();

    String racStatus();

    String ip();

    List<String> generateAwr(@Param("dbid") long dbid, @Param("instanceNum") long instanceNum,
        @Param("bid") long bid, @Param("eid") long eid);

    DbInfo getDbInfo();

    Long getInstanceNum();

    List<Long> getSnapId(@Param("dbid") long dbid);
}


