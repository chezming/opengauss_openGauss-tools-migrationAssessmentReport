/*
 * Copyright (c) 2021 Huawei Technologies Co.,Ltd.
 */
package com.huawei.jdbc.pojo;

import java.util.Date;

/**
 * Objects info in the database.
 */
public class DBAObject {
    private String owner;

    private String objectName;

    private String subObjectName;

    private long objectId;

    private long dataObjectId;

    private String objectType;

    private Date created;

    private Date lastDdlTime;

    private String timestamp;

    private String status;

    private String temporary;

    private String generated;

    private String secondary;

    private long namespace;

    private String editionName;

    private String sharing;

    private String editionable;

    private String oracleMaintained;

    private String application;

    private String defaultCollation;

    private String duplicated;

    private String sharded;

    private long createdAppid;

    private long createdVsnid;

    private long modifiedAppid;

    private long modifiedVsnid;

    public String getOwner() {
        return owner;
    }

    public DBAObject setOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public DBAObject setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getSubObjectName() {
        return subObjectName;
    }

    public DBAObject setSubObjectName(String subObjectName) {
        this.subObjectName = subObjectName;
        return this;
    }

    public long getObjectId() {
        return objectId;
    }

    public DBAObject setObjectId(long objectId) {
        this.objectId = objectId;
        return this;
    }

    public long getDataObjectId() {
        return dataObjectId;
    }

    public DBAObject setDataObjectId(long dataObjectId) {
        this.dataObjectId = dataObjectId;
        return this;
    }

    public String getObjectType() {
        return objectType;
    }

    public DBAObject setObjectType(String objectType) {
        this.objectType = objectType;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public DBAObject setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getLastDdlTime() {
        return lastDdlTime;
    }

    public DBAObject setLastDdlTime(Date lastDdlTime) {
        this.lastDdlTime = lastDdlTime;
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

    public String getEditionName() {
        return editionName;
    }

    public DBAObject setEditionName(String editionName) {
        this.editionName = editionName;
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

    public String getOracleMaintained() {
        return oracleMaintained;
    }

    public DBAObject setOracleMaintained(String oracleMaintained) {
        this.oracleMaintained = oracleMaintained;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public DBAObject setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getDefaultCollation() {
        return defaultCollation;
    }

    public DBAObject setDefaultCollation(String defaultCollation) {
        this.defaultCollation = defaultCollation;
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

    public long getCreatedAppid() {
        return createdAppid;
    }

    public DBAObject setCreatedAppid(long createdAppid) {
        this.createdAppid = createdAppid;
        return this;
    }

    public long getCreatedVsnid() {
        return createdVsnid;
    }

    public DBAObject setCreatedVsnid(long createdVsnid) {
        this.createdVsnid = createdVsnid;
        return this;
    }

    public long getModifiedAppid() {
        return modifiedAppid;
    }

    public DBAObject setModifiedAppid(long modifiedAppid) {
        this.modifiedAppid = modifiedAppid;
        return this;
    }

    public long getModifiedVsnid() {
        return modifiedVsnid;
    }

    public DBAObject setModifiedVsnid(long modifiedVsnid) {
        this.modifiedVsnid = modifiedVsnid;
        return this;
    }
}
