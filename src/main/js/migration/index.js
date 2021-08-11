import React from 'react';
import 'antd/dist/antd.css';
import {Collapse} from 'antd';
import SQLCompatibility from "../sqlcompatibility";
import TableStatisticTable from "../statistic";
import DBAObjectTable from "../summary";

const {Panel} = Collapse;
const client = require('../client'); // <3>

function callback(key) {
    console.log(key);
}


class MigrationReport extends React.Component {
    render() {
        return (
            <div>
                <SQLCompatibility/>
                <TableStatisticTable/>
                <DBAObjectTable/>
            </div>
        );
    }
}

export default MigrationReport;