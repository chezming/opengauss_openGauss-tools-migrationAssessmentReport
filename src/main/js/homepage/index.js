import {Descriptions, Table, Typography} from 'antd';
import React from 'react';
import CompatibilityChart from "./echart_compatibility";

const {Title} = Typography;

const client = require('../client');


class HomePage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            databaseName: "",
            databaseVersion: "",
            instanceName: "",
            numOfCpus: "",
            numOfCpuCores: "",
            physicalMem: "",
            visualMem: "",
            load: "",
            utilization: "",
            databaseSize: "",
            columns: [
              {
                title: 'File Name',
                dataIndex: 'filename',
                key: 'filename',
              },
              {
                title: 'Tablespace Name',
                dataIndex: 'tblspcname',
                key: 'tblspcname',
              },
              {
                title: 'Size(MB)',
                dataIndex: 'size',
                key: 'size',
              },
            ]
        };
    }

    componentDidMount() {
        client({method: 'GET', path: '/home/overview'}).done(response => {
            this.setState({databaseName: response.entity['databaseName']});
            this.setState({databaseVersion: response.entity['databaseVersion']});
            this.setState({instanceName: response.entity['instanceName']});
            this.setState({numOfCpus: response.entity['numOfCpus']});
            this.setState({numOfCpuCores: response.entity['numOfCpuCores']});
            this.setState({physicalMem: response.entity['physicalMem']});
            this.setState({visualMem: response.entity['visualMem']});
            this.setState({load: response.entity['load']});
            this.setState({utilization: (response.entity['utilization'])});
            this.setState({deployStructure: response.entity['deployStructure']});
            let fileData = [];
            let i = 0;
            let totalSize = 0;
            response.entity.dataFiles.forEach(dataFile => {
              let items = {
                'key': '' + i,
                'filename': dataFile.fileName,
                'tblspcname': dataFile.tablespaceName,
                'size': parseInt(dataFile.bytes) / 1024 / 1024,
              };
              fileData.push(items);
              i++;
              totalSize += parseInt(dataFile.bytes) / 1024 / 1024 / 1024;
            });

            this.setState({data: fileData});
            this.setState({ip: response.entity['ip']});
            this.setState({port: response.entity['port']});
            this.setState({databaseSize: totalSize.toFixed(2).toString() + "GB"});
        });
    }

    render() {
        return (
            <div>
                <Descriptions title="Oracle">
                    <Descriptions.Item label="数据库名称">{this.state.databaseName}</Descriptions.Item>
                    <Descriptions.Item label="数据库版本">{this.state.databaseVersion}</Descriptions.Item>
                    <Descriptions.Item label="实例名称">{this.state.instanceName}</Descriptions.Item>
                    <Descriptions.Item label="数据库端口">{this.state.port}</Descriptions.Item>
                    <Descriptions.Item label="数据库大小">{this.state.databaseSize}</Descriptions.Item>
                    <Descriptions.Item label="部署结构">{this.state.deployStructure}</Descriptions.Item>
                    <Descriptions.Item label="AWR报告"><a href="awr.html">最近两次快照的AWR报告</a></Descriptions.Item>
                </Descriptions>
                
                <Table pagination={ false } columns={this.state.columns} dataSource={this.state.data} />
                <Descriptions title="操作系统">
                    <Descriptions.Item label="IP地址">{this.state.ip}</Descriptions.Item>
                    <Descriptions.Item label="CPU数">{this.state.numOfCpus}</Descriptions.Item>
                    <Descriptions.Item label="CPU核数">{this.state.numOfCpuCores}</Descriptions.Item>
                    <Descriptions.Item label="物理内存大小">{this.state.physicalMem}</Descriptions.Item>
                    <Descriptions.Item label="虚拟内存大小">{this.state.visualMem}</Descriptions.Item>
                    <Descriptions.Item label="CPU使用率">{this.state.utilization}</Descriptions.Item>
                    <Descriptions.Item label="CPU负载">{this.state.load}</Descriptions.Item>
                </Descriptions>
                {/* <Descriptions title="表行数统计">
                {}</Descriptions>
                <CompatibilityChart/> */}

            </div>
        );
    }
}

export default HomePage;