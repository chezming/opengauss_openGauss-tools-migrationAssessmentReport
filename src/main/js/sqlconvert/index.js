import React from 'react';
import 'antd/dist/antd.css';
import './index.css';
import {Button, Col, Input, Modal, Row} from 'antd';
import Title from "antd/lib/typography/Title";
import {format} from 'sql-formatter'
import SQLShow from "../sqlformatter";

const {TextArea} = Input;
const client = require('../client'); // <3>

class SQLConvert extends React.Component {
    constructor(props) {
        super(props);
        this.state = {sql: "select * from \"openGauss\""}
    }

    readSQL(e) {
        this.setState({sql: e.target.value})
    }

    componentDidMount() {
        this.convert();
    }

    convert() {
        client({
            method: 'POST', path: '/sql/convert',
            entity: {
                sql: this.state.sql
            },
            headers: {
                'Content-Type': 'application/json'
            }
        }).done(response => {
            if (response.entity[false]) {
                this.error(response.entity[false]);
                this.setState({result: response.entity[false]});
            } else {
                this.setState({result: format(response.entity[true])});

            }
        });
    }

    error(message) {
        Modal.error({
            title: 'ERROR',
            content: message,
            width: 800
        });
    }

    render() {
        const title = '语法转换工具';
        const button_css = {
            display: 'block',
            marginLeft: 'auto',
            marginRight: 'auto',
        };
        return (
            <div>
                <Title level={2}>{title}</Title>
                <Row>
                    <Col span={11}><TextArea rows={4} autoSize='true' onChange={(e) => this.readSQL(e)}
                                             defaultValue={'select * from "openGauss"'}/></Col>
                    <Col span={2}><Button style={button_css} type="primary"
                                          onClick={() => this.convert()}>=></Button></Col>
                    <Col span={11}><SQLShow sql={this.state.result}/></Col>
                </Row>
            </div>
        );
    }
}

export default SQLConvert;