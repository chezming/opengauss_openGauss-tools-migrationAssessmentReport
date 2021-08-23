import React from 'react';
import 'antd/dist/antd.css';
import './index.css';
import {PageHeader} from 'antd';
import {Typography} from "antd";

const {Title, Paragraph, Text, Link} = Typography;

class MigrationPageHeader extends React.Component {
    constructor(props) {
        super(props);
        let title = "异构数据库迁移OpenGauss评估报告";

        if (props.title) {
            title = props.title
        }
        this.state = {
            title: title
        }
    }

    render() {
        return (
            <PageHeader
                className="site-page-header"
                title={this.state.title}
            />
        );
    }
}

export default MigrationPageHeader