import React, { Component } from 'react';
import { ClinicAdminContext } from '../../context/ClinicAdminContextProvider';
import axios from 'axios';
import TimePicker from 'react-time-picker/dist/TimePicker';
import FormControl from 'react-bootstrap/FormControl';
import Modal from "react-bootstrap/Modal";

class NewPredefinedAppointment extends Component {
    state = {
        dateAndTime: '',
        procedureTypeId: '',
        roomId: '',
        doctorId: '',
        nurseId: '',
        price: 0,
        disscount: 0,
        time: '',
        date: '',

        doctorListRender: {},
        roomListRender: {},

        roomList: {},
        doctorList: {},
        procedureList: {},

        doctorDisabled: true,
        roomDisabled: true,
        errorRoom: true,
        errorDisscount: true,
        errorTime: true,
        errorDate: true,
        errorTimeAndDate: true,
        errorPrice: true,
        go_profile: false,
        recomendedDate: '',
        errorNotAvailable: false,
        fatalError: false,
        whomNotDateFit: ''
    }

    static contextType = ClinicAdminContext

    componentDidMount() {
        axios.get('/api/doctors/clinic=' + this.context.admin.clinicId + '/all')
            .then(response => {
                this.setState({ doctorList: response.data })
            })

        axios.get('/api/rooms/all')
            .then(response => {
                this.setState({ roomList: response.data })
            })

        axios.get('/api/procedure+types/appointments/all')
            .then(response => {
                this.setState({ procedureList: response.data })
            })
    }

    handleValidation = () => {
        this.setState({ errorDisscount: false, errorPrice: false, doctorDisabled: false, roomDisabled: false, errorRoom: false }, () => {

            if (this.state.price === null || this.state.price === '' || this.state.price < 1) {
                this.setState({ errorPrice: true });
            }

            if (this.state.disscount === null || this.state.disscount === '' || this.state.disscount < 0 || this.state.disscount > 100) {
                this.setState({ errorDisscount: true });
            }

            if (this.state.procedureTypeId === null || this.state.procedureTypeId === "" || this.state.procedureTypeId === NaN) {
                this.setState({ doctorDisabled: true, roomDisabled: true, errorRoom: true, doctorId: "" })
            } else {
                var size = Object.keys(this.state.doctorList).length;
                let items = []
                for (let i = 0; i < size; ++i) {
                    if (this.state.doctorList[i].procedureTypeId == this.state.procedureTypeId) {
                        items.push(this.state.doctorList[i])
                    }
                }

                this.setState({ doctorListRender: items })
            }

            if (this.state.doctorId === null || this.state.doctorId === "") {
                this.setState({ roomDisabled: true, errorRoom: true, roomId: "" })
            } else {
                var size = Object.keys(this.state.roomList).length;
                let items = []
                for (let i = 0; i < size; ++i) {
                    if (this.state.roomList[i].procedureTypeId == this.state.procedureTypeId) {
                        items.push(this.state.roomList[i])
                    }
                }
                this.setState({ roomListRender: items })
            }

            if (this.state.roomId === null || this.state.roomId === "") {
                this.setState({ errorRoom: true })
            }

        })
    }

    handleValidationTimeAndDate = () => {
        this.setState({ errorDate: false, errorTime: false, errorTimeAndDate: false }, () => {
            if (this.state.date === null || this.state.date === "") {
                this.setState({ errorDate: true, errorTimeAndDate: true })
            } else if (this.state.time === null || this.state.time === "") {
                this.setState({ errorTime: true, errorTimeAndDate: true })
            } else {
                this.setState({ dateAndTime: this.state.date + ' ' + this.state.time })
            }
        })
    }

    handlerChange = (event) => {
        let nam = event.target.name
        let val = event.target.value
        console.log(event.target)
        this.setState({ [nam]: val }, () => { this.handleValidation() })
    }

    handlerChangeProcedureType = (event) => {
        let val = event.target.value.split("-")
        if (val[0] === '') {
            this.setState({ procedureTypeId: '' }, () => { this.handleValidation() })
        } else {
            this.setState({ procedureTypeId: parseInt(val[0]), price: parseInt(val[1]) }, () => { this.handleValidation() })
        }
    }

    handleChangeTime = (time) => {
        this.setState({ time: time }, () => { this.handleValidationTimeAndDate() })
    }

    handleChangeDate = (event) => {
        //jquery :(
        let date = document.getElementById('date').value;
        this.setState({ date: date }, () => { this.handleValidationTimeAndDate() })
    }

    createDoctorItems() {
        let items = [];
        var size = Object.keys(this.state.doctorListRender).length;
        items.push(<option key={size} name='doctorId' value="" selected="selected"> ---- </option>);
        for (let i = 0; i < size; ++i) {
            items.push(<option key={i} name='doctorId' value={this.state.doctorListRender[i].id}> dr. {this.state.doctorListRender[i].lasttName} {this.state.doctorListRender[i].firstName} </option>);
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
        }
        return items;
    }

    createRoomItems() {
        let items = [];
        var size = Object.keys(this.state.roomListRender).length;
        items.push(<option key={size} name='roomId' value="" selected="selected"> ---- </option>);
        for (let i = 0; i < size; i++) {
            items.push(<option key={i} name='roomId' value={this.state.roomListRender[i].id}>{this.state.roomListRender[i].name} {this.state.roomListRender[i].number} </option>);
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
        }
        return items;
    }

    createProcedureItems() {
        let items = [];
        var size = Object.keys(this.state.procedureList).length;
        items.push(<option key={size} name='procedureTypeId' value="-" selected="selected"> ---- </option>);
        for (let i = 0; i < size; i++) {
            let durationParts = this.state.procedureList[i].duration.split(":");
            items.push(<option key={i} name="procedureTypeId" value={this.state.procedureList[i].id + '-' + this.state.procedureList[i].price} >
                {this.state.procedureList[i].name}
                &nbsp;({this.state.procedureList[i].price}&#x20bf;)
                &nbsp;{durationParts[0]}:{durationParts[1]} H
                </option>);
            //here I will be creating my options dynamically based on
            //what props are currently passed to the parent component
        }
        return items;
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.setState({ errorNotAvailable: false, fatalError: false }, () => {

            axios.post('/api/predefined+appointments/newPredefinedAppointment', {
                dateAndTime: this.state.dateAndTime,
                procedureTypeId: this.state.procedureTypeId,
                roomId: this.state.roomId,
                doctorId: this.state.doctorId,
                price: this.state.price,
                disscount: this.state.disscount
            }).then((response) => {
                //201 created
                this.props.onSubmit();
            }).catch((error) => {
                if (error.response.status == 409) {
                    this.setState({
                        recomendedDate: error.response.data.dateAndTime
                    }, () => {
                        if (error.response.data.status == 'DOCTOR_NOT_FREE') {
                            this.setState({ whomNotDateFit: 'Doctor is ', errorNotAvailable: true })
                        } else if (error.response.data.status == 'NURSE_NOT_FREE') {
                            this.setState({ whomNotDateFit: 'Nurses are ', errorNotAvailable: true })
                        } else {
                            this.setState({ whomNotDateFit: 'Room is ', errorNotAvailable: true })
                        }
                    })
                } else {
                    this.setState({
                        fatalError: true
                    })
                }
            });
        });
    }

    handleCancel = () => {
        this.props.onHide();
    }

    render() {
        return (
            <Modal
                size="md"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={this.props.show}
            >
                    <Modal.Header closeButton onClick={this.props.onHide}>
                    <Modal.Title id="contained-modal-title-vcenter">
                        <h4>Create a predefined appointment</h4>
                     </Modal.Title>
                     </Modal.Header>
                    <Modal.Body>
                    <form onSubmit={this.handleSubmit}>

                        <div className={`form-group ${this.state.doctorDisabled ? 'has-danger' : ''}`} >
                            <label for="procedureTypeId">appointment type</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#9815;</span>
                                </div>
                                <select multiple="" className={`form-control ${this.state.doctorDisabled ? 'is-invalid' : 'is-valid'}`} id="procedureTypeId" name='procedureTypeId' onChange={this.handlerChangeProcedureType} >
                                    {this.createProcedureItems()}
                                </select>
                            </div>
                        </div>

                        <div className={`form-group ${this.state.roomDisabled ? 'has-danger' : ''}`}>
                            <label for="doctor">doctor</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#9960;</span>
                                </div>
                                <select multiple="" className={`form-control ${this.state.roomDisabled ? 'is-invalid' : 'is-valid'}`} id="doctor" name='doctorId' onChange={this.handlerChange} disabled={this.state.doctorDisabled}>
                                    {this.createDoctorItems()}
                                </select>
                            </div>
                        </div>

                        <div className={`form-group ${this.state.errorRoom ? 'has-danger' : ''}`}>
                            <label for="room">room</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#9963;</span>
                                </div>
                                <select multiple="" className={`form-control ${this.state.errorRoom ? 'is-invalid' : 'is-valid'}`} id="room" name='roomId' onChange={this.handlerChange} disabled={this.state.roomDisabled}>
                                    {this.createRoomItems()}
                                </select>
                            </div>
                        </div>

                        <div>
                            <label for='date'>date</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#x1F4C5;</span>
                                </div>
                                <FormControl type="date" id='date' locale="en-us" placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeDate} className={`form-control ${this.state.errorDate ? 'is-invalid' : 'is-valid'}`} />
                            </div>
                        </div>

                        <div class='form-group'>
                            <label for='time'>time</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#x231A;</span>
                                </div>
                                    <TimePicker name='duration' id='time' onChange={this.handleChangeTime} locale="en-us" value={this.state.time} className={`form-control ${this.state.errorTime ? 'is-invalid' : 'is-valid'}`} />
                            </div>
                        </div>

                        <div class='form-group'>
                            <label class="form-control-label" for="price">price:</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#x20bf;</span>
                                </div>
                                <input type='number' name='price' id='price' class='form-control' value={this.state.price} disabled />
                            </div>
                        </div>

                        <div className={`form-group ${this.state.errorDisscount ? 'has-danger' : ''}`}>
                            <label class="form-control-label" for="price">disscount:</label>
                            <div class='input-group'>
                                <input type='number' min="0" max="100" name='disscount' id='disscount' className={`form-control ${this.state.errorDisscount ? 'is-invalid' : 'is-valid'}`} value={this.state.disscount} onChange={this.handlerChange} />
                                <div class="input-group-append">
                                    <span class="input-group-text">%</span>
                                </div>
                            </div>
                        </div>

                        {this.state.errorNotAvailable &&
                            <p class="text-danger">{this.state.whomNotDateFit}not free. Please try with date: {this.state.recomendedDate}.</p>
                        }
                        {this.state.fatalError &&
                            <p class="text-danger">Please select a date in future</p>
                        }

                        <Modal.Footer>
                        <div class="form-group row">
                            <div class='col-md text-left'>
                                <input type="submit" class="btn btn-success" disabled={this.state.errorDisscount || this.state.errorTimeAndDate || this.state.errorRoom || this.state.errorNurse} value="submit" />
                            </div>
                            <div class='col-md text-right'>
                                <button type="button" class="btn btn-danger" onClick={this.handleCancel}>Cancel</button>
                            </div>
                        </div>
                        </Modal.Footer>
                    </form>
                    </Modal.Body>
               </Modal>
        );
    }
}

export default NewPredefinedAppointment;