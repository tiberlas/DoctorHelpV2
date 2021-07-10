import React, { Component, Fragment } from 'react';
import TableCell from '@material-ui/core/TableCell';
import ModalMessage from '../ModalMessage';
import axios from 'axios';

class PredefinedAppointmentItem extends Component {
    state = {
        id: this.props.value.id,
        doctorID: this.props.value.doctorId,
        nurseID: this.props.value.nurseId,
        roomID: this.props.value.roomId,
        procedureTypeID: this.props.value.procedureTypeId,
        price: this.props.value.price,
        disscount: this.props.value.disscount,
        dateAndTime: this.props.value.dateAndTime,
        clinicID: this.props.value.clinicId,

        procedureName: '',
        doctorName: '',
        nurseName: '',
        roomName: '',
        priceWithDiss: (this.props.value.price * (1 - (this.props.value.disscount / 100))).toFixed(2),

        globalError: false,
        messageShow: false,
        message: '',
        title: ''
    }

    componentDidMount() {
        axios.get("/api/procedure+types/id=" + this.state.procedureTypeID)
            .then(response => {
                this.setState({ procedureName: response.data.name })
            }).catch(error => {
                this.setState({ globalError: true })
            })

        axios.get("/api/doctors/clinic=" + this.state.clinicID + "/one/doctor=" + this.state.doctorID)
            .then(response => {
                this.setState({ doctorName: response.data.firstName + ' ' + response.data.lastName })
            }).catch(error => {
                this.setState({ globalError: true })
            })

        axios.get("/api/rooms/one/room=" + this.state.roomID)
            .then(response => {
                this.setState({ roomName: response.data.name + ' ' + response.data.number })
            }).catch(error => {
                this.setState({ globalError: true })
            })

        axios.get("/api/nurses/nurse=" + this.state.nurseID)
            .then(response => {
                this.setState({ nurseName: response.data.firstName + " " + response.data.lastName })
            }).catch(error => {
                this.setState({ globalError: true })
            })

    }

    onDelite = () => {
        axios.delete("/api/predefined+appointments/delete/id=" + this.state.id)
            .then(response => {
                this.props.handleUpdate(this.state.id);
            }).catch(error => {
                this.setState({
                    messageShow: true,
                    message: 'Could not delete room. Please reload page and try again!',
                    title: 'Some error has occured'
                })
            })
    };

    setMessageHide = () => {
        this.setState({ messageShow: false })
    }

    render() {

        return (
            <Fragment>
                <TableCell class="text-white">{this.state.procedureName}</TableCell>
                <TableCell class="text-white">{this.state.doctorName}</TableCell>
                <TableCell class="text-white">{this.state.nurseName}</TableCell>
                <TableCell class="text-white">{this.state.roomName}</TableCell>
                <TableCell class="text-white">{this.state.dateAndTime}</TableCell>
                <TableCell class="text-white">{this.state.priceWithDiss}&#x20bf;</TableCell>
                <TableCell class="text-danger">-{this.state.disscount}%</TableCell>
                <TableCell><button onClick={this.onDelite} class='btn btn-danger'>delete</button></TableCell>


                <ModalMessage
                    title={this.state.title}
                    message={this.state.message}
                    show={this.state.messageShow}
                    onHide={this.setMessageHide} />


            </Fragment>
        );
    }
}

export default PredefinedAppointmentItem;