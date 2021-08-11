import {Table, Typography} from "antd";
import "antd/dist/antd.css";
import * as echarts from 'echarts';

const React = require('react'); // <1>
const client = require('../client'); // <3>
const {Title, Paragraph, Text, Link} = Typography;

class SQLCompatibility extends React.Component {
    constructor(props) {
        super(props);
    }

    columns = [
        {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            render: text => <a>{text}</a>,
            sorter: (a, b) => a.type.localeCompare(b.type),
        },
        {
            title: '总数',
            dataIndex: 'total',
            key: 'total',
            sorter: (a, b) => {
                let left = parseInt(a.total);
                let right = parseInt(b.total);
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        },
        {
            title: '源数据库导出支持',
            dataIndex: 'originError',
            key: 'originError',
            sorter: (a, b) => {
                let left = parseInt(a.originError);
                let right = parseInt(b.originError);
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        },
        {
            title: '目标数据库原生支持',
            dataIndex: 'targetSupport',
            key: 'targetSupport',
            sorter: (a, b) => {
                let left = parseInt(a.targetSupport);
                let right = parseInt(b.targetSupport);
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        },
        {
            title: '目标数据库插件支持',
            dataIndex: 'extensionSupport',
            key: 'extensionSupport',
            sorter: (a, b) => {
                let left = parseInt(a.extensionSupport);
                let right = parseInt(b.extensionSupport);
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        },
        {
            title: '转化率',
            dataIndex: 'conversionRate',
            key: 'conversionRate',
            sorter: (a, b) => {
                let left = Number(a.conversionRate.replace("%", ""));
                let right = Number(b.conversionRate.replace("%", ""));
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        }
    ];

    state = {
        "top": 'topLeft',
        "bottom": 'bottomRight'
    };


    componentDidMount() { // <2>
        client({method: 'GET', path: '/summary/object/sqlcompatibility'}).done(response => {
            const data = [];
            response.entity.forEach(compatiblity => {
                let items = {
                    'key': compatiblity.type,
                    'type': compatiblity.type,
                    'total': compatiblity.total,
                    'originError': compatiblity.originError,
                    'targetSupport': compatiblity.targetSupport,
                    'extensionSupport': compatiblity.extensionSupport,
                    'conversionRate': compatiblity.conversionRate,
                };
                data.push(items);
            });
            this.setState({compatiblities: data});
        });
    }


    render() {
        const text = '兼容性总览';
        return (
            <div>
                <Title level={2}>{text}</Title>
                <Table columns={this.columns} pagination={false}
                       dataSource={this.state.compatiblities}/>
            </div>
        );
    }
}

export default SQLCompatibility;