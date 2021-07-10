import React, { Component } from 'react';
import axios from 'axios';
import Modal from 'react-bootstrap/Modal'

class ChangeRoomModal extends Component {
    state = {
        id: this.props.id,
        number: this.props.number,
        name: this.props.name,
        type: this.props.type,
        typeId: this.props.typeId,
        procedureList: {},
        errorNumber: false,
        errorName: false,
    }

    componentDidMount() {
        axios.get('/api/procedure+types/all')
        .then(response => {
            this.setState({procedureList: response.data})
        })
    }

    handleValidation = () => {
        this.setState({errorName: false, errorNumber: false})

        if(!this.state.name.trim() || this.state.name.length < 3) {
            this.setState({errorName: true})
        }

        if(this.state.number == undefined || this.state.number == null) {
            this.setState({errorLast: true})
        }
    }

    handlerChange = (event) => {
        let nam = event.target.name
        let val = event.target.value
        this.setState({[nam]: val}, () => { this.handleValidation()})
    }

    handleSubmit = (event) => {
        event.preventDefault();
        axios.put('/api/rooms/change', {
                    id: this.state.id,            
                    name: this.state.name,
                    number: parseInt(this.state.number),
                    procedureTypeId: this.state.typeId,
                    procedureTypeName: this.state.type
        }).then( (response) => {
            this.props.handleUpdate(response.data.name, response.data.number, response.data.procedureTypeName, response.data.procedureTypeId)
        }).catch((error) => {
            this.setState({ 
                errorNumber: true
            })
        });
    }

    handlerChangeProcedureType = (event) => {
        let val = event.target.value.split("-")
        this.setState({typeId: parseInt(val[0]), type: val[1]})
    }

    createProcedureItems() {
        let items = []; 
        var size = Object.keys(this.state.procedureList).length;
        for (let i = 0; i < size; i++) {
            if(this.state.typeId === this.state.procedureList[i].id) {
                items.push(<option key={i} name = "procedureTypeId" selected="selected" value={this.state.procedureList[i].id+'-'+this.state.procedureList[i].price} >{this.state.procedureList[i].name}: {this.state.procedureList[i].price}</option>);
            } else {
                items.push(<option key={i} name = "procedureTypeId" value={this.state.procedureList[i].id+'-'+this.state.procedureList[i].name} >{this.state.procedureList[i].name}: {this.state.procedureList[i].price}&#x20bf;</option>);
            }
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
              <Modal.Header closeButton onClick={this.props.onHide}>
                <Modal.Title id="contained-modal-title-vcenter">Change room</Modal.Title>
              </Modal.Header>
                <form onSubmit={this.handleSubmit}> 
                    <Modal.Body>
                        <div className={`form-group ${this.state.errorName? 'has-danger': ''}`}>
                            <label class="form-control-label" for="name">name:</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#9963;</span>
                                </div>
                                    <input type='text' name='name' id='name' className={`form-control ${this.state.errorName? 'is-invalid': 'is-valid'}`} value={this.state.name} onChange={this.handlerChange} />
                                </div>
                        </div>

                        <div className={`form-group ${this.state.errorNumber? 'has-danger': ''}`}>
                            <label class="form-control-label" for="number">number:</label>
                            <div class="input-group">
                                <div class="input-group-preppend">
                                    <span class="input-group-text">&#x2116;</span>
                                </div>
                                    <input type='number' name='number' id='number' className={`form-control ${this.state.errorNumber? 'is-invalid': 'is-valid'}`} value={this.state.number} onChange={this.handlerChange} />
                                </div>
                            {(this.state.errorNumber) && <div class="invalid-feedback"> Room number already exists. </div>}
                        </div>

                        <div class='form-group'>
                        <label for="procedureTypeId">appointment type</label>
                        <div class="input-group">
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#9815;</span>
                            </div>
                        <select multiple="" class='form-control' id="procedureTypeId" name='procedureTypeId' onChange={this.handlerChangeProcedureType} >
                            {this.createProcedureItems()}
                        </select>
                        </div>
                    </div>

                    </Modal.Body>
                    <Modal.Footer>
                        <input type="submit" class="btn btn-success" disabled={this.state.errorName || this.state.errorNumber} value="submit"/>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onClick={this.props.onHide}>Close</button>
                    </Modal.Footer>
                </form>
            </Modal>
          );
    }
}

export default ChangeRoomModal;