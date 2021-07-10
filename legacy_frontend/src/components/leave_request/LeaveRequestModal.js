import React, {Fragment} from 'react'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import {Link} from 'react-router-dom'
import axios from 'axios'
import Moment from 'moment'; //library for comparing dates

import './fade-modal.css'

class LeaveRequestModal extends React.Component {

    state = {
        ableToRequest: true,
        leaveType: "Personal leave",
        note: "", 
        showNote: false, //show textarea when button is clicked
        successInfo: false, //if request is saved on back-end -> true
        id: this.props.id, //medical staff id
        role: this.props.role, //medical staff role
        operation: false
    }


    handleChange = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        })
    }

    componentDidMount() { //validate my request with this.props.selectedDates which contains startDate and endDate
        let able = true //able means able to make a leave request
        for(let i = 0; i < this.props.appointments.length; i++) {
            let appointmentStartDate = new Date(this.props.appointments[i].startDate)
            let selectedBeginDate = new Date(this.props.selectedDates.startStr)
            let selectedEndDate = new Date(this.props.selectedDates.endStr)
            if(Moment(appointmentStartDate).isAfter(selectedBeginDate) && Moment(appointmentStartDate).isBefore(selectedEndDate)) { //are there any appointments between my selected dates?
                able = false
                this.setState({ableToRequest: false})
            }
        }

        if (typeof this.props.operations !== 'undefined') {
            for(let i = 0; i < this.props.operations.length; i++) {
                let operationStartDate = new Date(this.props.operations[i].startDate)
                console.log('a', operationStartDate)
                let selectedBeginDate = new Date(this.props.selectedDates.startStr)

                let selectedEndDate = new Date(this.props.selectedDates.endStr)
                if(Moment(operationStartDate).isAfter(selectedBeginDate) && Moment(operationStartDate).isBefore(selectedEndDate) || Moment(operationStartDate).isSame(selectedBeginDate, 'day')) { //are there any appointments between my selected dates?
                    able = false
                    this.setState({ableToRequest: false, operation: true})
                }
            }
        }

        //this.setState({ableToRequest: able})

    }


    displayUnableToRequest = () => { //if overlapping dates, unable to make a request
        let startDate = new Date(this.props.selectedDates.startStr).toLocaleDateString("en-US")
        
        let endDate = new Date(this.props.selectedDates.endStr)
        endDate.setDate(endDate.getDate()-1)
        endDate = endDate.toLocaleDateString("en-US")

        let type = ''
        if(this.state.operation)
            type = 'operations'
        else
            type = 'appointments'
        return <Fragment>  
            <div class="row d-flex justify-content-center">
                <div class='col-md-11'> 
                        {'Unable to make a request between ' + startDate + ' and ' + endDate + 
                ' because some ' + type + ' overlap.\nPlease try another date.' }  
                </div>
            </div> 
            </Fragment>
    }

    displayAbleToRequest = () => {

        let endDate = new Date(this.props.selectedDates.endStr)
        endDate.setDate(endDate.getDate()-1) //javascript calendar goes 1 day ahead, so i must reduce it by 1

       return <Fragment>                
                    <div class="row d-flex justify-content-center">
                        <div class='col-md-11'>
                          
                            <strong> <i class="fas fa-calendar-minus"></i> Start:  </strong>&emsp; {new Date(this.props.selectedDates.startStr).toLocaleDateString("en-US")}
                            <br/>
                            <strong> <i class="fas fa-calendar-plus"></i> End: </strong> &emsp; {endDate.toLocaleDateString("en-US")}
                        </div>
                    </div>

                    <div class="row d-flex justify-content-center">
                        <div class='col-md-11'>
                        <br/>
                        <label>
                            <input required
                                type="radio" 
                                name="leaveType"
                                value="Personal leave"
                                checked={this.state.leaveType === "Personal leave"}
                                onChange={this.handleChange}
                            /> Personal leave
                        </label> &nbsp;
                        <label>
                            <input required
                                type="radio" 
                                name="leaveType"
                                value="Annual leave"
                                checked={this.state.leaveType === "Annual leave"}
                                onChange={this.handleChange}
                            /> 
                        </label> Annual leave

                        </div>
                    </div>

                    <div class="row d-flex justify-content-center">
                        <div class='col-md-11'>
                           {this.state.showNote === false && <Button className="btn btn-info " onClick={()=>{this.setState({showNote: true})}}>Add note</Button>}
                            {this.state.showNote === true 
                            && <textarea name="note" onChange={this.handleChange} 
                                                    placeholder="Aditional note will make your request better!" 
                                                    maxLength={50}
                                                    style={{resize: "none"}}/>}

                        </div>
                    </div>

                    {this.state.successInfo &&  <div class="row d-flex justify-content-center">
                                                 <div class='col-md-11'>
                                                    <div class="valid-feedback d-block"> Request successfully sent! </div>  
                                                </div> 
                                                </div>}

                  
                                            
                </Fragment>
    }

    handleConfirm = () => {
        let leaveTypeData = ""
        if(this.state.leaveType === 'Annual leave') {
            leaveTypeData = "ANNUAL"
        } else {
            leaveTypeData = "PERSONAL"
        }
        
        let endDate = new Date(this.props.selectedDates.endStr)
        endDate.setDate(endDate.getDate()-1)

        if(this.state.role === 'NURSE') {
            axios.post('/api/leave-requests/add/nurse='+this.state.id, 
            {
                leaveType: leaveTypeData,
                note: this.state.note,
                staffId: this.state.id,
                staffRole: "NURSE",
                startDate: new Date(this.props.selectedDates.startStr),
                endDate: endDate

            }).then(response => {
                this.setState({successInfo: true}, () => {
                    setTimeout(() => { this.props.toggle()}, 1000)
                })
            })
            
        } else if(this.state.role === 'DOCTOR') {
            axios.post('/api/leave-requests/add/doctor='+this.state.id, 
            {
                leaveType: leaveTypeData,
                note: this.state.note,
                staffId: this.state.id,
                staffRole: "DOCTOR",
                startDate: new Date(this.props.selectedDates.startStr),
                endDate: endDate

            }).then(response => {
                this.setState({successInfo: true}, () => {
                    setTimeout(() => { this.props.toggle()}, 1000)
                })
            })
            
        } 
    }

    render() {
        return (
            <Fragment>
                            <Modal
                            isOpen={this.props.modal}
                            toggle={this.props.toggle}
                            closeTimeoutMS={2000} >
                            <ModalHeader toggle={this.props.toggle}>
                            {!this.state.ableToRequest ? <>Unable to request</> : <> New request overview </> } 
                            </ModalHeader>
                                    <ModalBody> {/* able to request means the selected dates are validated */}
                                    
                                            {!this.state.ableToRequest && this.displayUnableToRequest()}
                                            {this.state.ableToRequest && this.displayAbleToRequest()}            
                                
                                    </ModalBody>
                            <ModalFooter>
                                
                        <Button color="secondary" onClick={this.props.toggle}> Cancel</Button>
                        {this.state.ableToRequest 
                        && <Button color="primary" onClick={this.handleConfirm}> Submit </Button>}
                            </ModalFooter>
                        </Modal>
                        
            </Fragment>
        )
    }
}


export default LeaveRequestModal