import {Button, Input, Modal, Table, Typography} from "antd";
import "antd/dist/antd.css";
import SQLShow from "../sqlformatter/index.js";
import "./index.css"
import {format} from 'sql-formatter'

const React = require('react'); // <1>
const client = require('../client'); // <3>
const {Title, Paragraph, Text, Link} = Typography;
const {TextArea} = Input;

class DBAObjectTable extends React.Component {
    constructor(props) {
        super(props);
        this.state.refreshId = "";
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
            title: 'ObjectName',
            dataIndex: 'objectName',
            key: 'objectName',
            sorter: (a, b) => a.objectName.localeCompare(b.objectName),

        },
        {
            title: 'TargetDatabase',
            dataIndex: 'targetDatabase',
            key: 'targetDatabase',
            render: validateResult => {
                if (validateResult) {
                    if (validateResult.target) {
                        return (<div><font color='#7fff00'>true</font></div>)
                    } else {
                        return (<ShowButton text={validateResult.targetError}/>)
                    }
                } else {
                    return (<div><font color='red'>false</font></div>)

                }
            }

        },
        {
            title: 'ExtensionDatabase',
            dataIndex: 'extensionDatabase',
            key: 'extensionDatabase',
            render: validateResult => {
                if (validateResult) {
                    if (validateResult.extension) {
                        return (<div><font color='#7fff00'>true</font></div>)
                    } else {
                        return (<ShowButton text={validateResult.extensionError}/>)
                    }
                } else {
                    return (<div><font color='red'>false</font></div>)
                }
            }

        },
        {
            title: 'Type',
            dataIndex: 'type',
            key: 'type',
            sorter: (a, b) => a.type.localeCompare(b.type),
        },
        {
            title: 'Definition',
            key: 'definition',
            dataIndex: 'definition',
            render: definition => <ShowButton id={definition} refresh={this.refresh}/>
        }
    ];

    refresh = (id) => {
        client({method: 'GET', path: '/summary/object/validate/' + id}).done(response => {
            if (response.entity) {
                let buffers = this.state.databaseObjects;
                for (let i = 0; i < buffers.length; i++) {
                    if (buffers[i].key === '' + id) {
                        buffers[i].targetDatabase = response.entity;
                        buffers[i].extensionDatabase = response.entity;
                        this.setState({
                            databaseObjects: buffers,
                            refreshId: '' + id
                        });
                        break;
                    }
                }
            }
        });
    };

    state = {
        "top": 'topLeft',
        "bottom": 'bottomRight'
    };


    componentDidMount() { // <2>
        client({method: 'GET', path: '/summary/objects'}).done(response => {
            const data = [];
            let i = 0;
            response.entity.forEach(databaseObject => {
                let items = {
                    'key': '' + databaseObject.id,
                    'targetDatabase': databaseObject.validateResult,
                    'extensionDatabase': databaseObject.validateResult,
                    'schema': databaseObject.schema,
                    'objectName': databaseObject.objectName,
                    'type': databaseObject.type,
                    'definition': databaseObject.id,
                };
                data.push(items);
                i++;
            });
            this.setState({databaseObjects: data});
        });
    }


    render() {
        return (
            <div>
                <Title level={2}>兼容性详情</Title>
                {/*<Alert message="SUMMARY" type="success" />*/}
                <div>
                    <Table columns={this.columns}
                           pagination={{position: [this.state.bottom]}}
                           dataSource={this.state.databaseObjects}
                           rowClassName={(record, index) => {
                               if (this.state.refreshId) {
                                   if (record.key === this.state.refreshId) {
                                       return 'current_row';
                                   }
                               }
                               return '';
                           }}
                    />
                </div>
            </div>
        );
    }
}

class DialogCustom extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            modifyVisible: false
        }
    }

    handleOK = () => {
        this.props.onClose();
    };

    modify = () => {
        this.setState({modifyVisible: true});
    };
    closeModifyModal = () => {
        this.setState({modifyVisible: false});
    };

    render() {
        const {visible, onClose, text, id, refresh} = this.props;
        const result = text == null ? '' : format(text);
        return (
            <div>
                <Modal visible={visible} onOk={this.handleOK} onCancel={this.handleOK} width={1200} footer={[
                    <Button key="back" onClick={this.handleOK}>返回</Button>,
                    <Button key="modify" type="primary" onClick={this.modify}>修改</Button>
                ]}>
                    <SQLShow sql={result}/>
                </Modal>
                <ModifyModel visible={this.state.modifyVisible} onClose={() => {
                    this.closeModifyModal();
                    this.handleOK();
                }} text={text} id={id} refresh={refresh}/>
            </div>
        );
    }
}

class ModifyModel extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            defaultValue: ''
        }
    }

    modify = () => {
        if (!this.state.sql || this.state.defaultValue === format(this.state.sql)) {
            this.info("语句无变动，修改无效。");
        } else {
            client({
                method: 'POST', path: '/sql/modify',
                entity: {
                    sql: this.state.sql,
                    id: this.state.id,
                },
                headers: {
                    'Content-Type': 'application/json'
                }
            }).done(response => {
                if (response.entity === "") {
                    this.info("修改成功！");
                    this.props.refresh(this.state.id);
                    this.props.onClose();
                } else {
                    this.error(response.entity);
                }
            });
        }

    };

    error(message) {
        Modal.error({
            title: 'ERROR',
            content: message,
            width: 800
        });
    }


    info(message) {
        Modal.info({
            title: message,
            onOk() {
            },
        });
    }

    readSQL(e) {
        this.setState({sql: e.target.value})
    }

    render() {
        const {visible, onClose, text, id, refresh} = this.props;
        const result = text == null ? '' : format(text);
        this.state.defaultValue = result;
        this.state.id = id;
        return (
            <div>
                <Modal visible={visible} onCancel={onClose} width={1200} footer={[
                    <Button key="modify" type="primary" onClick={() => {
                        this.modify();
                    }}>修改</Button>
                ]}>
                    <TextArea autoSize={true} defaultValue={result} onChange={(e) => this.readSQL(e)}/>
                </Modal>
            </div>
        );
    }
}


class ShowButton extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            visible: false, text: props.text
        }
    }

    handleClickOpen = () => {
        if (this.props.text) {
            this.setState({text: this.props.text});
        } else {
            client({method: 'GET', path: '/summary/object/definition/' + this.props.id}).done(response => {
                this.setState({text: response.entity[this.props.id]});
            });
        }
        this.setState({visible: true});
    };


    handleClose = (value) => {
        this.setState({visible: false});
    };


    render() {
        const {refresh} = this.props;
        return (
            <div>
                <Button variant="outlined" color="primary" onClick={this.handleClickOpen}>
                    Details
                </Button>
                <DialogCustom visible={this.state.visible} text={this.state.text} id={this.props.id}
                              onClose={this.handleClose} refresh={refresh}/>
            </div>
        );
    }
}

export default DBAObjectTable;