import React, { Component } from 'react';
import axios from 'axios';
import Modal from 'react-bootstrap/Modal';
import TimePicker from 'react-time-picker';
import Checkbox from '@material-ui/core/Checkbox';

class ChangeProcedureTypeModal extends Component {
    state = {
        id: this.props.id,
        price: this.props.price,
        name: this.props.name,
        operation: this.props.operation,
        duration: this.props.duration,
        errorPrice: false,
        errorName: false,
        errorDuration: false
    }
    
    handleValidation = () => {
        this.setState({errorName: false, errorPrice: false, errorDuration: false})

        if(this.state.name === undefined || this.state.name === null || !this.state.name.trim() || this.state.name.length < 3) {
            this.setState({errorName: true});
        }

        if(this.state.price === undefined || this.state.price === null || this.state.price === "" || this.state.price < 1) {
            this.setState({errorPrice: true});
        }

        if(this.state.duration === undefined || this.state.duration === null || this.state.duration === "") {
            this.setState({errorDuration: true});
        }
    }

    handlerChange = (event) => {
        
        let nam = event.target.name
        let val = event.target.value
        console.log(nam, val );
        this.setState({[nam]: val}, () => { this.handleValidation()})
    }

    handleChangeTime = (time) => {

        this.setState({duration: time}, () => { this.handleValidation()})
    }

    handleChecked = (event) => {
        this.setState({operation: event.target.checked});
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios.put('/api/procedure+types/change', {
                    id: this.state.id,            
                    name: this.state.name,
                    price: parseInt(this.state.price),
                    duration: this.state.duration,
                    operation: this.state.operation
        }).then( (response) => {
            this.props.handleUpdate(response.data.name, response.data.price, response.data.operation, response.data.duration)
        }).catch((error) => {
            this.setState({ 
                errorName: true
            })
        });
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
                <Modal.Title id="contained-modal-title-vcenter">Change procedure type</Modal.Title>
              </Modal.Header>
                <form onSubmit={this.handleSubmit}> 
                    <Modal.Body>
                        <div className={`form-group ${this.state.errorName? 'has-danger': ''}`}>
                            <label class="form-control-label" for="name">name:</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">&#9815;</span>
                                </div>
                                <input type='text' name='name' id='name' className={`form-control ${this.state.errorName? 'is-invalid': 'is-valid'}`} value={this.state.name} onChange={this.handlerChange} />
                            </div>
                            {(this.state.errorName) && <div class="invalid-feedback"> Procedure type name already exists. </div>}
                        </div>

                        <div className={`form-group ${this.state.errorPrice? 'has-danger': ''}`}>
                            <label class="form-control-label" for="price">price:</label>
                            <div class="input-group mb-3">
                            <div class="input-group-prepend">
                                    <span class="input-group-text">&#x20bf;</span>
                            </div>
                            <input type='number' min="1" name='price' id='price' className={`form-control ${this.state.errorPrice? 'is-invalid': 'is-valid'}`} value={this.state.price} onChange={this.handlerChange} />
                        </div>
                        </div>

                        <div className={`form-group ${this.state.errorDuration? 'has-danger': ''}`}>
                            <label class="form-control-label" for="duration">duration:</label>
                            <div class="input-group mb-3">
                                <div class="input-group-prepend">
                                    <span class="input-group-text">&#8987;</span>
                                </div>
                                <TimePicker name='duration' id='duration' onChange={this.handleChangeTime} locale="en-us" value={this.state.duration} className={`form-control ${this.state.errorDuration? 'is-invalid': 'is-valid'}`}/>
                            </div>
                        </div>

                        <div>
                            <label class="form-control-label" for="operation">is operation:</label>
                            <Checkbox checked={this.state.operation} name='operation' value='operation' onChange={this.handleChecked} />
                        </div>


                    </Modal.Body>
                    <Modal.Footer>
                        <input type="submit" class="btn btn-success" disabled={this.state.errorName || this.state.errorPrice || this.state.errorDuration} value="submit"/>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onClick={this.props.onHide}>Close</button>
                    </Modal.Footer>
                </form>
            </Modal>
          );
        }
}
 
export default ChangeProcedureTypeModal;