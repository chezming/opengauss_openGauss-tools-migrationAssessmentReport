import React from 'react';
import 'antd/dist/antd.css';
import './index.css';
import {Layout, Menu} from 'antd';
import {Link} from 'react-router'

const {Header, Content, Footer} = Layout;


const css = {
    float: "left",
    width: "120px",
    height: "31px",
    margin: "16px 24px 16px 0",
    backgroundImage: `url('image/logo2.png')`,
    backgroundRepeat: "no-repeat",
    backgroundSize: "100% 100%",
};

class MigrationMenu extends React.Component {

    constructor(props) {
        super(props);
        this.state = {select: []}
    }

    render() {
        return (
            <Layout>
                <Header style={{position: 'fixed', zIndex: 1, width: '100%'}}>
                    <Link to="/" onClick={() => {
                        this.state.select = []
                    }}>
                        <div className="logo" style={css}/>
                    </Link>
                </Header>
                <Content className="site-layout" style={{padding: '0 50px', marginTop: 64}}>
                    <div className="site-layout-background" style={{padding: 24, minHeight: 380}}>
                        {this.props.children}
                    </div>
                </Content>
                <Footer style={{textAlign: 'center'}}>版权所有 © 华为技术有限公司 2021 保留一切权利</Footer>
            </Layout>
        )
    };
}

export default MigrationMenu
