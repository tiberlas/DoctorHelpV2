import React, { Component } from 'react';
import axios from 'axios';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import RoomList from './RoomList';

class RequestedAppointment extends Component {
    state = {  
        id: '',
        date: '',
        procedure: '',
        duration: '',
        typeId: 0,
        doctor: '',
        nurse: '',
        patient: ''
    }

    componentDidMount() {
        let path_parts = window.location.pathname.split('appointment/')
        axios.get('/api/appointments/requests/id=' + path_parts[1])
            .then(responce => {
                this.setState({
                    id: responce.data.id,
                    date: responce.data.date,
                    procedure: responce.data.type,
                    doctor: responce.data.doctor,
                    nurse: responce.data.nurse,
                    patient: responce.data.patient,
                    typeId: responce.data.typeId,
                    duration: responce.data.duration
                })
            })
    }

    render() {
        return (
            <div>
          <div class='row d-flex justify-content-center'>
                    <div class='col-md-5'>
                        <br/>
                            <h3> Requested appointment. </h3>
                        </div>
                    </div>

                    <div class='row d-flex justify-content-center'>
                    <div class='col-md-11'>
                <Table class="table table-hover ">
                    <TableHead class="table-active">
                        <TableRow>
                            <TableCell class="text-success cursor-pointer" ><i class="fas fa-clock"></i>  Date</TableCell>
                            <TableCell class="text-success cursor-pointer" ><i class="fas fa-procedures"></i> Procedure</TableCell>
                            <TableCell class="text-success cursor-pointer" ><i class="fas fa-user-md"></i>  Doctor</TableCell>
                            <TableCell class="text-success cursor-pointer" ><i class="fas fa-user-nurse"></i>  Nurse</TableCell>
                            <TableCell class="text-success cursor-pointer" ><i class="fas fa-user-injured"></i>  Patient</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        <TableCell class='text text-white'>{this.state.date}</TableCell>
                        <TableCell class='text text-white'>{this.state.procedure}&nbsp;{this.state.duration}h</TableCell>
                        <TableCell class='text text-white'>{this.state.doctor}</TableCell>
                        <TableCell class='text text-white'>{this.state.nurse}</TableCell>
                        <TableCell class='text text-white'>{this.state.patient}</TableCell>
                    </TableBody>
                </Table>
            </div>
            </div>

                <RoomList operation={false} appointment={this.state.id} date={this.state.date} type={this.state.typeId} nurse={this.state.nurse}></RoomList>

            </div>
        );
    }
}
 
export default RequestedAppointment;