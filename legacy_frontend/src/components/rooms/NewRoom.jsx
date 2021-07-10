import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import Modal from "react-bootstrap/Modal";
import axios from 'axios';

class NewRoom extends Component {
    state = {
        number: 0,
        name: '',
        procedureTypeId: '',
        procedureList: {},
        errorProcedureType: true,
        errorNumber: true,
        errorName: true,
        errorBack: false,
        go_profile: false,
    }

    componentDidMount() {
        axios.get('/api/procedure+types/all')
            .then(response => {
                this.setState({ procedureList: response.data })
            })
    }

    handleValidation = () => {
        if (!this.state.name.trim() || this.state.name.length < 3) {
            this.setState({ errorName: true })
        } else {
            this.setState({ errorName: false })
        }

        if (this.state.number < 1) {
            this.setState({ errorNumber: true })
        } else {
            this.setState({ errorNumber: false, errorBack: false })
        }
    }

    handlerChange = (event) => {
        let nam = event.target.name
        let val = event.target.value
        this.setState({ [nam]: val }, () => { this.handleValidation() })
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios.post('/api/rooms/new+room', {
            name: this.state.name,
            number: parseInt(this.state.number),
            procedureTypeId: this.state.procedureTypeId
        }).then((response) => {
            this.props.onSubmit();
        }).catch((error) => {
            this.setState({
                errorBack: true
            })
        });
    }

    handleCancel = () => {
        this.props.onHide();
    }

    setMessageHide = () => {
        this.setState({ messageShow: false })
    }

    handlerChangeProcedureType = (event) => {
        let val = event.target.value.split("-")
        if (val[0] === '') {
            this.setState({ errorProcedureType: true })
        } else {
            this.setState({ procedureTypeId: parseInt(val[0]), errorProcedureType: false })
        }
    }

    createProcedureItems() {
        let items = [];
        var size = Object.keys(this.state.procedureList).length;
        items.push(<option key={size} name='procedureTypeId' value="" selected="selected"> ---- </option>);
        for (let i = 0; i < size; i++) {
            let durationParts = this.state.procedureList[i].duration.split(":");
            items.push(<option key={i} name="procedureTypeId" value={this.state.procedureList[i].id + '-' + this.state.procedureList[i].price} >
                {this.state.procedureList[i].name}
                &nbsp;({this.state.procedureList[i].price}&#x20bf;)
                        &nbsp;{durationParts[0]}:{durationParts[1]} H
                        &nbsp;{this.state.procedureList[i].operation ? 'OPERATION' : ''}
            </option>);
        }
        return items;
    }

    render() {
        return (
            <Modal
                size="md"
                aria-labelledby="contained-modal-title-vcenter"
                centered
                show={this.props.show}
            >
                <Modal.Header>
                    <h5>Add new room</h5>
                </Modal.Header>
                <Modal.Body>
                    <form onSubmit={this.handleSubmit}>
                        <div className={`form-group ${this.state.errorName ? 'has-danger' : ''}`}>
                            <label class="form-control-label" for="name">name:</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">&#9963;</span>
                                </div>
                                <input type='text' name='name' id='name' className={`form-control ${this.state.errorName ? 'is-invalid' : 'is-valid'}`} value={this.state.name} onChange={this.handlerChange} />
                            </div>
                        </div>

                        <div className={`form-group ${this.state.errorNumber ? 'has-danger' : ''}`}>
                            <label class="form-control-label" for="number">number:</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">&#x2116;</span>
                                </div>
                                <input type='number' name='number' id='number' className={`form-control ${this.state.errorNumber ? 'is-invalid' : 'is-valid'}`} value={this.state.number} onChange={this.handlerChange} />
                            </div>
                            {(this.state.errorNumber) && <div class="invalid-feedback"> Must enter a positive value. </div>}
                            {(this.state.errorBack) && <div class="text text-danger"> Room number already exists. Please try with a different number. </div>}
                        </div>

                        <div className={`form-group ${this.state.errorProcedureType ? 'has-danger' : ''}`}>
                            <label for="procedureTypeId">procedure type</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">&#9815;</span>
                                </div>
                                <select multiple="" className={`form-control ${this.state.errorProcedureType ? 'is-invalid' : 'is-valid'}`} id="procedureTypeId" name='procedureTypeId' onChange={this.handlerChangeProcedureType} >
                                    {this.createProcedureItems()}
                                </select>
                            </div>
                            {(this.state.errorProcedureType) && <div class="invalid-feedback"> Must select a procedure type. </div>}
                        </div>

                        <Modal.Footer>
                            <div class="form-group row">
                                <div class='col-md text-left'>
                                    <input type="submit" class="btn btn-success" disabled={this.state.errorName || this.state.errorNumber || this.state.errorProcedureType} value="submit" />
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

export default NewRoom;