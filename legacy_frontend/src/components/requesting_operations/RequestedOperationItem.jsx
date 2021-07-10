import React, { Component, Fragment } from 'react';
import TableCell from '@material-ui/core/TableCell';

class RequestedOperationItem extends Component {
    state = {
        operationId: this.props.value.operationId,
        date: this.props.value.date,
        procedureName: this.props.value.procedureName,
        procedureDuration: this.props.value.procedureDuration,
        procedureId: this.props.value.procedureId,
        dr0: this.props.value.dr0,
        dr1: this.props.value.dr1,
        dr2: this.props.value.dr2,
        patient: this.props.value.patient
    }

    render() {
        return (
            <Fragment>
                <TableCell class='text text-white'>{this.state.date}</TableCell>
                <TableCell class='text text-white'>{this.state.procedureName}&nbsp;{this.state.procedureDuration}&nbsp;H</TableCell>
                <TableCell class='text text-white'>{this.state.patient}</TableCell>
            </Fragment>
        );
    }
}

export default RequestedOperationItem;