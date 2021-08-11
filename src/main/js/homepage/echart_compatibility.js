import React from 'react';

const client = require('../client'); // <3>
import * as echarts from 'echarts';

class CompatibilityChart extends React.Component {
    constructor(props) {
        super(props);
        this.state = {option: this.option};
    }

    option = {
        tooltip: {
            trigger: 'axis',
            axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
            }
        },
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            containLabel: true
        },
        xAxis: [
            {
                type: 'category',
                data: [],
                axisTick: {
                    alignWithLabel: true
                }
            }
        ],
        yAxis: [
            {
                type: 'value'
            }
        ],
        series: [
            {
                name: '直接访问',
                type: 'bar',
                barWidth: '60%',
                data: []
            }
        ]
    };

    componentDidMount() {
        const myChart = echarts.init(document.getElementById('compatibilityChart'));
        client({method: 'GET', path: '/summary/object/sqlcompatibility'}).done(response => {
            let columns = [];
            let datas = [];
            response.entity.forEach(compatiblity => {
                if (compatiblity.type !== 'TOTAL' && compatiblity.total !== 0) {
                    columns.push(compatiblity.type);
                    datas.push(Number(compatiblity.conversionRate.replace("%", "")) / 100);
                }
            });
            this.option.xAxis[0].data = columns;
            this.option.series[0].data = datas;
            console.log(this.option);
            myChart.setOption(this.option, true);
        });
    }

    render() {
        return (<div id="compatibilityChart" style={{height: 400}}>test</div>);
    }
}

export default CompatibilityChart
