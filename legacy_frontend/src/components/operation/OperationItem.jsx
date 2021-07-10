import React, { Component, Fragment } from 'react';
import axios from 'axios';
import ModalMessage from '../ModalMessage';
import TableCell from '@material-ui/core/TableCell';

class OperationItem extends Component {
    state = { 
        id: this.props.value.id, 
        patientName: this.props.value.patientName,
        procedureName: this.props.value.procedureName,
        date: this.props.value.date,
        doctor0: this.props.value.dr0Name,
        doctor1: this.props.value.dr1Name,
        doctor2: this.props.value.dr2Name,
        status: this.props.value.status,

        messageShow: false
    }

    onDelite = () => {
        axios.delete('/api/operations/requested='+this.state.id+'/delete')
            .then(response => {
                this.props.handleUpdate(this.state.id);
            }).catch(error => {
                this.setState({messageShow: true});
            })
    }

    setMessageHide = () => {
        this.setState({messageShow: false})
    }

    render() { 
        return ( 
            <Fragment>
                <TableCell class="text-white">{this.state.date}</TableCell>
                <TableCell class="text-white">{this.state.procedureName}</TableCell>
                <TableCell class="text-white">{this.state.patientName}</TableCell>
                <TableCell class="text-white">{this.state.doctor0}</TableCell>
                <TableCell class="text-white">{this.state.doctor1}</TableCell>
                <TableCell class="text-white">{this.state.doctor2}</TableCell>
                <TableCell class="text-white">{this.state.status}</TableCell>
                <TableCell class="text-white"><button onClick={this.onDelite} class='btn btn-danger'>Cancel</button></TableCell>
                <TableCell>

                <ModalMessage
                    title={"Error"}
                    message={"Unable to cancel the request because it starts in less than 24 hours."} 
                    show={this.state.messageShow}
                    onHide={this.setMessageHide}/>
                </TableCell>
            </Fragment>
         );
    }
}
 
export default OperationItem;
