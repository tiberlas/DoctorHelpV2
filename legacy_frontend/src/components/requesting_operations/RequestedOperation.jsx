import React, { Component } from 'react';
import RoomList from '../requesting_appointment/RoomList.jsx';
import axios from 'axios';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';

class requestedOperation extends Component {
    state = {
        operationId: '',
        date: '',
        procedureName: '',
        procedureId: '',
        procedureDuration: '',
        patient: ''
    }

    componentDidMount() {
        let path_parts = window.location.pathname.split('operation/')
        axios.get('/api/operations/requests/id=' + path_parts[1])
            .then(responce => {
                this.setState({
                    operationId: responce.data.operationId,
                    date: responce.data.date,
                    procedureName: responce.data.procedureName,
                    procedureId: responce.data.procedureId,
                    procedureDuration: responce.data.procedureDuration,
                    patient: responce.data.patient
                })
            })
    }

    render() {
        return (
            <div>
                <div class='row d-flex justify-content-center'>
                    <div class='col-md-5'>
                        <br/>
                            <h3> Requested operation. </h3>
                        </div>
                        </div>

                        <div class='row d-flex justify-content-center'>
                    <div class='col-md-11'>
                        <br/>
                        <Table class="table table-hover ">
                            <TableHead>
                                <TableRow>
                                    <TableCell class="text-success cursor-pointer" ><i class="fas fa-clock"></i> Date</TableCell>
                                    <TableCell class="text-success cursor-pointer" ><i class="fas fa-procedures"></i> Procedure</TableCell>
                                    <TableCell class="text-success cursor-pointer" ><i class="fas fa-user-injured"></i> Patient</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableCell class="text-white" >{this.state.date}</TableCell>
                                <TableCell class='text text-white'>{this.state.procedureName}&nbsp;{this.state.procedureDuration}h</TableCell>
                                <TableCell class='text text-white'>{this.state.patient}</TableCell>
                            </TableBody>
                        </Table>
                    </div>
                </div>

                <RoomList operation={true} operationId={this.state.operationId} date={this.state.date} type={this.state.procedureId} />

            </div>
        );
    }
}

export default requestedOperation;