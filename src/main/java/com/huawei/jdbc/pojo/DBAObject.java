package com.huawei.jdbc.pojo;

import java.util.Date;

public class DBAObject {
    private String owner;

    private String object_name;

    private String subobject_name;

    private long object_id;

    private long data_object_id;

    private String object_type;

    private Date created;

    private Date last_ddl_time;

    private String timestamp;

    private String status;

    private String temporary;

    private String generated;

    private String secondary;

    private long namespace;

    private String edition_name;

    private String sharing;

    private String editionable;

    private String oracle_maintained;

    private String application;

    private String default_collation;

    private String duplicated;

    private String sharded;

    private long created_appid;

    private long created_vsnid;

    private long modified_appid;

    private long modified_vsnid;

    public String getOwner() {
        return owner;
    }

    public DBAObject setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getObject_name() {
        return object_name;
    }

    public DBAObject setObject_name(String object_name) {
        this.object_name = object_name;
        return this;
    }

    public String getSubobject_name() {
        return subobject_name;
    }

    public DBAObject setSubobject_name(String subobject_name) {
        this.subobject_name = subobject_name;
        return this;
    }

    public long getObject_id() {
        return object_id;
    }

    public DBAObject setObject_id(long object_id) {
        this.object_id = object_id;
        return this;
    }

    public long getData_object_id() {
        return data_object_id;
    }

    public DBAObject setData_object_id(long data_object_id) {
        this.data_object_id = data_object_id;
        return this;
    }

    public String getObject_type() {
        return object_type;
    }

    public DBAObject setObject_type(String object_type) {
        this.object_type = object_type;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public DBAObject setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getLast_ddl_time() {
        return last_ddl_time;
    }

    public DBAObject setLast_ddl_time(Date last_ddl_time) {
        this.last_ddl_time = last_ddl_time;
        return this;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public DBAObject setTimestamp(String timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public DBAObject setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getTemporary() {
        return temporary;
    }

    public DBAObject setTemporary(String temporary) {
        this.temporary = temporary;
        return this;
    }

    public String getGenerated() {
        return generated;
    }

    public DBAObject setGenerated(String generated) {
        this.generated = generated;
        return this;
    }

    public String getSecondary() {
        return secondary;
    }

    public DBAObject setSecondary(String secondary) {
        this.secondary = secondary;
        return this;
    }

    public long getNamespace() {
        return namespace;
    }

    public DBAObject setNamespace(long namespace) {
        this.namespace = namespace;
        return this;
    }

    public String getEdition_name() {
        return edition_name;
    }

    public DBAObject setEdition_name(String edition_name) {
        this.edition_name = edition_name;
        return this;
    }

    public String getSharing() {
        return sharing;
    }

    public DBAObject setSharing(String sharing) {
        this.sharing = sharing;
        return this;
    }

    public String getEditionable() {
        return editionable;
    }

    public DBAObject setEditionable(String editionable) {
        this.editionable = editionable;
        return this;
    }

    public String getOracle_maintained() {
        return oracle_maintained;
    }

    public DBAObject setOracle_maintained(String oracle_maintained) {
        this.oracle_maintained = oracle_maintained;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public DBAObject setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getDefault_collation() {
        return default_collation;
    }

    public DBAObject setDefault_collation(String default_collation) {
        this.default_collation = default_collation;
        return this;
    }

    public String getDuplicated() {
        return duplicated;
    }

    public DBAObject setDuplicated(String duplicated) {
        this.duplicated = duplicated;
        return this;
    }

    public String getSharded() {
        return sharded;
    }

    public DBAObject setSharded(String sharded) {
        this.sharded = sharded;
        return this;
    }

    public long getCreated_appid() {
        return created_appid;
    }

    public DBAObject setCreated_appid(long created_appid) {
        this.created_appid = created_appid;
        return this;
    }

    public long getCreated_vsnid() {
        return created_vsnid;
    }

    public DBAObject setCreated_vsnid(long created_vsnid) {
        this.created_vsnid = created_vsnid;
        return this;
    }

    public long getModified_appid() {
        return modified_appid;
    }

    public DBAObject setModified_appid(long modified_appid) {
        this.modified_appid = modified_appid;
        return this;
    }

    public long getModified_vsnid() {
        return modified_vsnid;
    }

    public DBAObject setModified_vsnid(long modified_vsnid) {
        this.modified_vsnid = modified_vsnid;
        return this;
    }
}
