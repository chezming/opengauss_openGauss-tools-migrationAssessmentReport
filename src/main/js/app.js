import {hashHistory, IndexRoute, Route, Router} from 'react-router'
import SQLConvert from "./sqlconvert";
import SQLCompatibility from "./sqlcompatibility";
import MigrationMenu from "./menu";
import MigrationReport from "./migration";
import SqlTable from "./sql";
import HomePage from "./homepage";

const React = require('react');
const ReactDOM = require('react-dom');

'use strict';

class About extends React.Component {
    render() {
        return <h3>About</h3>
    }
}

class Inbox extends React.Component {
    render() {
        return (
            <SQLCompatibility/>
        )
    }
}

class Message extends React.Component {
    render() {
        return <h3>Message {this.props.params.id}</h3>
    }
}

ReactDOM.render((
    <Router history={hashHistory}>
        <Route path="/" component={MigrationMenu}>
            <IndexRoute component={HomePage}/>
            <Route path="index" component={MigrationReport}/>
            <Route path="convert" component={SQLConvert}/>
            <Route path="details" component={SqlTable}/>
        </Route>
    </Router>
), document.getElementById('react'));