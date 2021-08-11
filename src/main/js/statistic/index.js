import {Modal, Table, Typography} from "antd";
import "antd/dist/antd.css";
import * as echarts from 'echarts';

const React = require('react');
const client = require('../client');
const { Title, Paragraph, Text, Link } = Typography;

class TableStatisticBar extends React.Component {
    constructor(props) {
        super(props);

    }

    options = {
        title: {
            text: '某站点用户访问来源',
            subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                name: '访问来源',
                type: 'pie',
                radius: '50%',
                data: [
                    {value: 0, name: 'small'},
                    {value: 0, name: 'middle'},
                    {value: 0, name: 'large'},
                ],
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };

    componentDidMount() {
        const myChart = echarts.init(document.getElementById('main'));
        myChart.setOption(this.options, true);
        this.setState({myChart: myChart});
    }

    componentWillReceiveProps(newProps, nextContext) {
        this.options.series[0].data = [
            {value: newProps.small, name: 'small'},
            {value: newProps.middle, name: 'middle'},
            {value: newProps.large, name: 'large'}
        ];
        this.state.myChart.setOption(this.options, true)
    }

    render() {
        return (<div id="main" style={{width: 400, height: 400}}>test</div>);
    }
}

class TableStatisticTable extends React.Component {
    constructor(props) {
        super(props);
    }

    columns = [
        {
            title: 'Schema',
            dataIndex: 'schema',
            key: 'schema',
            render: text => <a>{text}</a>,
            sorter: (a, b) => a.schema.localeCompare(b.schema),
        },
        {
            title: 'TableName',
            dataIndex: 'objectName',
            key: 'objectName',
            sorter: (a, b) => a.objectName.localeCompare(b.objectName),

        },
        {
            title: 'Row',
            dataIndex: 'numRows',
            key: 'numRows',
            sorter: (a, b) => {
                let left = parseInt(a.numRows);
                let right = parseInt(b.numRows);
                if (left - right > 0) {
                    return 1;
                } else if (left - right < 0) {
                    return -1;
                } else {
                    return 0;
                }
            },
        },
    ];

    state = {
        "top": 'topLeft',
        "bottom": 'bottomRight'
    };


    componentDidMount() {
        client({method: 'GET', path: '/summary/object/tablestatistic'}).done(response => {
            const data = [];
            let small = 0;
            let middle = 0;
            let large = 0;
            response.entity.forEach(tableStatistic => {
                let items = {
                    'key': '' + tableStatistic.schema + '_' + tableStatistic.objectName,
                    'schema': tableStatistic.schema,
                    'objectName': tableStatistic.objectName,
                    'numRows': tableStatistic.numRows,
                };
                data.push(items);
                if (tableStatistic.numRows < 1000) {
                    small++;
                } else if (tableStatistic.numRows < 4000) {
                    middle++;
                } else {
                    large++;
                }
            });
            this.setState({tableStatistics: data});
            this.setState({small: small});
            this.setState({middle: middle});
            this.setState({large: large});
        });
    }


    render() {
        return (
            <div>
                {/*<TableStatisticBar small={this.state.small} middle={this.state.middle} large={this.state.large}/>*/}
                <Title level={2}>表行数统计</Title>
                <Table columns={this.columns}
                       pagination={{position: [this.state.bottom]}}
                       dataSource={this.state.tableStatistics}/>
            </div>
        );
    }
}

class DialogCustom extends React.Component {
    constructor(props) {
        super(props);
    }

    handleOK = () => {
        this.props.onClose();
    };

    render() {
        const {visible, onClose, text} = this.props;
        return (
            <Modal visible={visible} onOk={this.handleOK} onCancel={this.handleOK}>{text}</Modal>
        );
    }
}
export default TableStatisticTable;