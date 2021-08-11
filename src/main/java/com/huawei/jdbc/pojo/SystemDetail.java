package com.huawei.jdbc.pojo;

import java.util.List;

import com.huawei.jdbc.pojo.DataFiles;

public class SystemDetail {
    // database details
    private String databaseName;
    private String databaseVersion;
    private String instanceName;

    // system version
    private String ip;
    private String systemVersion;
    private String numOfCpus;
    private String numOfCpuCores;
    private String physicalMem;
    private String visualMem;
    private String load;
    private String utilization;

    private List<DataFiles> dataFiles;
    private String deployStructure;

    public String getPort() {
        return port;
    }

    public SystemDetail setPort(String port) {
        this.port = port;
        return this;
    }

    private String port;

    // todo
    private String databaseSize;

    public String getDatabaseName() {
        return databaseName;
    }

    public SystemDetail setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
        return this;
    }

    public String getDatabaseVersion() {
        return databaseVersion;
    }

    public SystemDetail setDatabaseVersion(String databaseVersion) {
        this.databaseVersion = databaseVersion;
        return this;
    }

    public String getInstanceName() {
        return instanceName;
    }

    public SystemDetail setInstanceName(String instanceName) {
        this.instanceName = instanceName;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public SystemDetail setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public String getSystemVersion() {
        return systemVersion;
    }

    public SystemDetail setSystemVersion(String systemVersion) {
        this.systemVersion = systemVersion;
        return this;
    }

    public String getNumOfCpus() {
        return numOfCpus;
    }

    public SystemDetail setNumOfCpus(String numOfCpus) {
        this.numOfCpus = numOfCpus;
        return this;
    }

    public String getNumOfCpuCores() {
        return numOfCpuCores;
    }

    public SystemDetail setNumOfCpuCores(String numOfCpuCores) {
        this.numOfCpuCores = numOfCpuCores;
        return this;
    }

    public String getPhysicalMem() {
        return physicalMem;
    }

    public SystemDetail setPhysicalMem(String physicalMem) {
        this.physicalMem = physicalMem;
        return this;
    }

    public String getVisualMem() {
        return visualMem;
    }

    public SystemDetail setVisualMem(String visualMem) {
        this.visualMem = visualMem;
        return this;
    }

    public String getDatabaseSize() {
        return databaseSize;
    }

    public SystemDetail setDatabaseSize(String databaseSize) {
        this.databaseSize = databaseSize;
        return this;
    }

    public String getLoad() {
        return load;
    }

    public SystemDetail setLoad(String load) {
        this.load = load;
        return this;
    }

    public String getUtilization() {
        return utilization;
    }

    public SystemDetail setUtilization(String utilization) {
        this.utilization = utilization;
        return this;
    }

    public List<DataFiles> getDataFiles() {
        return dataFiles;
    }

    public SystemDetail setDataFiles(List<DataFiles> dataFiles) {
        this.dataFiles = dataFiles;
        return this;
    }

    public String getDeployStructure() {
        return deployStructure;
    }

    public SystemDetail setDeployStructure(String deployStructure) {
        this.deployStructure = deployStructure;
        return this;
    }

}
