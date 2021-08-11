import React from 'react';
import 'antd/dist/antd.css';
import './index.css';
import {Collapse} from 'antd';
import SQLShow from "../sqlformatter";

const {Panel} = Collapse;
const client = require('../client'); // <3>

function callback(key) {
    console.log(key);
}


class SQL extends React.Component {
    render() {
        const {header, key, text} = this.props;
        return (
            <Panel header={header} key={key}>
                <p>{text}</p>
            </Panel>
        );
    }
}

class SqlTable extends React.Component {
    constructor(props) {
        super(props);
        this.state = {sqlDetails: [], keys: []}
    }

    componentDidMount() { // <2>
        client({method: 'GET', path: '/summary/object/sqldetails'}).done(response => {
            let sqlDetails = [];
            let keys = [];
            response.entity.forEach(sql => {
                sql.header = sql.schema + "             " + sql.objectName;
                sqlDetails.push(sql);
                keys.push(sql.id);
            });
            this.setState({sqlDetails: sqlDetails});
            this.setState({keys: keys});
        });
    }

    render() {
        const panels = this.state.sqlDetails.map(sql =>
            <Panel key={sql.id} header={sql.header}>
                <SQLShow sql={sql.definition}/>
            </Panel>
        );
        return (
            <div>
                <Collapse activeKey={this.state.keys}>
                    {panels}
                </Collapse>
            </div>
        )
    }
}

export default SqlTable;