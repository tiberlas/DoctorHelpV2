import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import ModalMessage from '../ModalMessage';
import Modal from "react-bootstrap/Modal";

class CreateMedicalStaff extends Component {
    state = {
        firstName: '',
        lastName: '',
        email: '',
        birthday: '',
        procedures: {},
        procedureID: '',
        monday: 'NONE',
        tuesday: 'NONE',
        wednesday: 'NONE',
        thursday: 'NONE',
        friday: 'NONE',
        saturday: 'NONE',
        sunday: 'NONE',

        messageShow: false,

        errorFirstName: true,
        errorLastName: true,
        errorEmail: true,
        errorBirthday: true,
        errorShift: true,
        errorProcedure: true,
        errorSpecialization: true,

        typeDoctor: true,
        go_profile: false
    }

    componentDidMount() {
        axios.get('/api/procedure+types/all')
            .then(response => {
                this.setState({ procedures: response.data })
            })
    }

    handleValidation = () => {
        if (this.state.firstName !== null && this.state.firstName !== '' && this.state.firstName.length > 2) {
            this.setState({ errorFirstName: false })
        } else {
            this.setState({ errorFirstName: true })
        }

        if (this.state.lastName !== null && this.state.lastName !== '' && this.state.lastName.length > 2) {
            this.setState({ errorLastName: false })
        } else {
            this.setState({ errorLastName: true })
        }

        if (this.state.email !== null && this.state.email !== '' && this.state.email.length > 2) {
            this.setState({ errorEmail: false })
        } else {
            this.setState({ errorEmail: true })
        }

        if (this.state.birthday !== null && this.state.birthday !== '') {
            this.setState({ errorBirthday: false })
        } else {
            this.setState({ errorBirthday: true })
        }

        let workingDays = 0
        if (this.state.monday !== 'NONE') { ++workingDays }
        if (this.state.tuesday !== 'NONE') { ++workingDays }
        if (this.state.wednesday !== 'NONE') { ++workingDays }
        if (this.state.thursday !== 'NONE') { ++workingDays }
        if (this.state.friday !== 'NONE') { ++workingDays }
        if (this.state.saturday !== 'NONE') { ++workingDays }
        if (this.state.sunday !== 'NONE') { ++workingDays }
        if (workingDays < 3) {
            this.setState({ errorShift: true })
        } else {
            this.setState({ errorShift: false })
        }

    }

    handlerChange = (event) => {
        let nam = event.target.name
        let val = event.target.value
        this.setState({ [nam]: val }, () => { this.handleValidation() })
    }

    handleSpecialization = () => {
        if (this.state.typeDoctor === false) {
            this.setState({ errorSpecialization: false, errorProcedure: true, procedureID: '' })
        } else {
            this.setState({ errorSpecialization: true, errorProcedure: true, procedureID: '' })
        }
    }

    handleSubmit = (event) => {
        event.preventDefault();
        let url = ''
        if (this.state.typeDoctor === true) {
            url = '/api/doctors/new+doctor'
        } else {
            url = '/api/nurses/new+nurse'
        }
        let id = ''
        if (this.state.procedureID !== "") {
            id = this.state.procedureID
        }
        axios.post(url, {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            email: this.state.email,
            procedureId: id,
            birthday: this.state.birthday,
            monday: this.state.monday,
            tuesday: this.state.tuesday,
            wednesday: this.state.wednesday,
            thursday: this.state.thursday,
            friday: this.state.friday,
            saturday: this.state.saturday,
            sunday: this.state.sunday
        }).then((response) => {
            this.props.onHide();
            //this.setState({ go_profile: true })
        }).catch((error) => {
            this.setState({
                messageShow: true
            })
        });
    }

    handleCancel = () => {
        this.props.onHide();
    }

    setMessageHide = () => {
        this.setState({ messageShow: false })
    }

    createShift() {
        let items = [];
        items.push(<option key='1' value="NONE" selected="selected" class='text-muted'> free day </option>);
        items.push(<option key='2' value="FIRST" > first shift </option>);
        items.push(<option key='3' value="SECOND" > second shift </option>);
        items.push(<option key='4' value="THIRD" > third shift </option>);

        return items;
    }

    handlerChangeProcedureType = (event) => {
        let val = event.target.value
        if (val === "" || val === null) {
            this.setState({ errorSpecialization: true, errorProcedure: true })
        } else {
            this.setState({ procedureID: val, errorSpecialization: false, errorProcedure: false })
        }

    }

    createProcedure = () => {
        let items = [];
        var size = Object.keys(this.state.procedures).length;
        items.push(<option key={size} name='procedureID' value="" selected="selected"> ---- </option>);
        for (var i = 0; i < size; ++i) {
            items.push()
            items.push(<option key={i} name='procedureID' value={this.state.procedures[i].id} >{this.state.procedures[i].name}</option>);
        }

        return items;
    }

    handleChangeRoleForCreating = (changeEvent) => {
        console.log('event', changeEvent)
        if (changeEvent.target.value == 'DOCTOR') {
            this.setState({ typeDoctor: true }, () => { this.handleSpecialization() })
        } else {
            this.setState({ typeDoctor: false }, () => { this.handleSpecialization() })
        }
    }

    render() {
        return (
            <Modal
                size="xl"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={this.props.show}
            >
                <Modal.Header closeButton onClick={this.props.onHide}>
                    <Modal.Title id="contained-modal-title-vcenter">
                        {this.state.typeDoctor ?
                            <h2>Create a new Doctor</h2> :
                            <h2>Create a new Nurse</h2>
                        }
                    </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                <form onSubmit={this.handleSubmit}>
                    <div class = 'row'>
                        <div class = 'col'>
                    <ul>
                        <li>
                            <label class="text-white">select&nbsp;role:</label>
                        </li>
                        <li>
                            <input
                                type="radio"
                                id="crateDoctor"
                                name="crateDoctor"
                                value="DOCTOR"
                                checked={this.state.typeDoctor}
                                onClick={this.handleChangeRoleForCreating}
                            />
                            <label
                                class="text-white"
                                for="crateDoctor"
                            >
                                doctor
                        </label>
                            <div class="check"></div>
                        </li>
                        <li>
                            <input
                                type="radio"
                                id="createNurse"
                                name="createNurse"
                                value="NURSE"
                                checked={!this.state.typeDoctor}
                                onClick={this.handleChangeRoleForCreating}
                            />
                            <label
                                class="text-white"
                                for="createNurse"
                            >
                                nurse
                        </label>
                            <div class="check"></div>
                        </li>
                    </ul>
                    <br />
                        <div className={`form-group ${this.state.errorFirstName ? 'has-danger' : ''}`}>
                            <label class="form-control-label" for="firstName">First name:</label>
                            <input type='text' name='firstName' id='firstName' className={`form-control ${this.state.errorFirstName ? 'is-invalid' : 'is-valid'}`} value={this.state.firstName} onChange={this.handlerChange} />
                        </div>

                        <div className={`form-group ${this.state.errorLastName ? 'has-danger' : ''}`}>
                            <label class="form-control-label" for="lastName">Last name:</label>
                            <input type='text' name='lastName' id='lastName' className={`form-control ${this.state.errorLastName ? 'is-invalid' : 'is-valid'}`} value={this.state.lastName} onChange={this.handlerChange} />
                        </div>

                        <div class='form-group'>
                            <label class="form-control-label" for="email">Email:</label>
                            <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">@</span>
                            </div>
                            <input type='email' name='email' id='email' className={`form-control ${this.state.errorEmail ? 'is-invalid' : 'is-valid'}`} value={this.state.email} onChange={this.handlerChange} />
                            </div>
                        </div>

                        <div class='form-group'>
                            <label class="form-control-label" for="birthday">Birthday:</label>
                            <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#x1F382;</span>
                            </div>
                            <input type='date' name='birthday' id='birthday' className={`form-control ${this.state.errorBirthday ? 'is-invalid' : 'is-valid'}`} value={this.state.birthday} onChange={this.handlerChange} />
                            </div>
                        </div>

                        {
                            this.state.typeDoctor &&
                                <div class='form-group'>
                                    <label class="form-control-label" for="procedure">Specialization</label>
                                    <div class='input-group'>
                                    <div class="input-group-preppend">
                                        <span class="input-group-text">&#9815;</span>
                                    </div>
                                    <select multiple="" className={`form-control ${this.state.errorProcedure ? 'is-invalid' : 'is-valid'}`} id="procedure" name='procedureID' onChange={this.handlerChangeProcedureType} >
                                        {this.createProcedure()}
                                    </select>
                                    </div>
                                </div>
                        }

                        </div>
                        <div class = 'col'>
                        <h5>Work shift</h5>
                        <br />

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Monday   </span>
                            </div>
                            <select multiple="" class="form-control" id="monday" name='monday' onChange={this.handlerChange} >
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Tuesday  </span>
                            </div>
                            <select multiple="" class="form-control" id="tuesday" name='tuesday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Wednesday</span>
                            </div>
                            <select multiple="" class="form-control" id="wednesday" name='wednesday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Thursday </span>
                            </div>
                            <select multiple="" class="form-control" id="thursday" name='thursday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Friday   </span>
                            </div>
                            <select multiple="" class="form-control" id="friday" name='friday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Saturday </span>
                            </div>
                            <select multiple="" class="form-control" id="saturday" name='saturday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div class='form-group'>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">Sunday   </span>
                            </div>
                            <select multiple="" class="form-control" id="sunday" name='sunday' onChange={this.handlerChange}>
                                {this.createShift()}
                            </select>
                            </div>
                        </div>

                        <div hidden={!this.state.errorShift}>
                            <p class='text-danger'>must dafined a minimum of three working days</p>
                        </div>

                        </div>
                        </div>

                        <Modal.Footer>
                            <div class="form-group row">
                                <div class='col-md text-left'>
                                    <input type="submit" class="btn btn-success" disabled={this.state.errorFirstName || this.state.errorLastName || this.state.errorEmail || this.state.errorBirthday || this.state.errorShift || this.state.errorSpecialization} value="submit" />
                                </div>
                                <div class='col-md text-right'>
                                    <button type="button" class="btn btn-danger" onClick={this.handleCancel}>Cancel</button>
                                </div>
                            </div>
                        </Modal.Footer>
                    </form>
                </Modal.Body>

                <ModalMessage
                    title='Can not create a new medical staff!'
                    message='Email already exists! Please change email and check if all the fields are valid'
                    show={this.state.messageShow}
                    onHide={this.setMessageHide} />
            </Modal>
        );
    }
}

export default CreateMedicalStaff;