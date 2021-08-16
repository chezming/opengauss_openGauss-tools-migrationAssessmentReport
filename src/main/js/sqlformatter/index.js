import React from 'react';
import 'highlight.js/styles/vs.css';
import 'antd/dist/antd.css';
import Highlight from 'react-highlight';
import {Input} from 'antd';

const {TextArea} = Input;

class SQLShow extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        const {sql} = this.props;
        return (
            <Highlight languageName="sql" style={{whiteSpace: 'pre-wrap'}}>{sql}</Highlight>
        );
    }
}
export default SQLShow;