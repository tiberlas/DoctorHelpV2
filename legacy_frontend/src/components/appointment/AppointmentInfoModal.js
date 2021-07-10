import React, {Fragment} from 'react'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import {Link} from 'react-router-dom'
import axios from 'axios'
import {DoctorContext} from '../../context/DoctorContextProvider'
import DoctorShowExaminationReport from './DoctorShowExaminationReport'
import BootstrapButton from 'react-bootstrap/Button'

class AppointmentInfoModal extends React.Component {

    static contextType = DoctorContext

    state = {
        operation: this.props.event.operation,
        showConfirmModal: false,
        canDecline: false,
        showRequestDeclinedOK: false,
        showRequestDeclinedBAD: false, 
        showReport: false,
        report: {}
    }

    handleDelete = () => {
        axios.delete('/api/appointments/requested='+this.props.event.id+'/delete')
            .then(response => {
                this.setState({showRequestDeclinedOK: true})
            }).catch(error=> {
                this.setState({showRequestDeclinedBAD: true})
            })

    }

    checkCurrentDate = () => { //proverava da li se prosledjeni appointment slaze sa tekucim danom

        let today = new Date();
        let isToday = (today.toDateString() == this.props.event.start.toDateString());
        return isToday
    }


    componentWillReceiveProps(props) {
        this.setState({ showConfirmModal: props.showConfirmModal, canDecline: false, showRequestDeclinedOK: false, showRequestDeclinedBAD: false}, () => {

            if(props.event.status == 'DONE') {
                axios.get('/api/appointments/get-examination-report/appointment='+props.event.id+'/doctor='+this.context.doctor.id).then(response => {
                    this.setState({report: response.data, showReport: true}, () => {this.handleCanDecline()})
                })
            } else {
                this.setState({showReport: false}, () => {this.handleCanDecline()})
            }
        })
    }

    handleCanDecline = () => {
        if(this.props.event.id > 0) {
            axios.get('/api/appointments/requested='+this.props.event.id+'/can+delete')
            .then(response => {
                if(response.data == 'CAN BE DELETED') {
                    this.setState({canDecline: true});
                }
            }).catch(error => {
                this.setState({canDecline: false});
            })
        }
    }

    updateReport = (note) => {
        this.setState( prevState => ({
            report: {
                ...prevState.report,
                note: note
            }
        }), ()=>{this.forceUpdate()})
    }

    displayStatus = () => {
        let firstCharacter = this.props.event.status.substr(0,1)
        let restOfCharacters = this.props.event.status.substr(1, this.props.event.status.length).toLowerCase().replace(/\_/g,' ')
        return firstCharacter + restOfCharacters
    }

    displayDiscount = () => {
        if(this.props.event.discount == -1) {
            return '0'
        } else 
            return this.props.event.discount
    }

    showAppointmentInfo = () => {
        let profileUrl = '/profile/' + this.props.event.patientInsurance
        return <Fragment> 
                    <br/>
                    <i class="fas fa-user-injured"></i>  Patient:  {this.props.event.patient.trim() === '-' 
                     ? <span style={{fontStyle: "italic", color: "#cdcdcd"}}> unassigned </span> : <Fragment> <Link to = {profileUrl}> {this.props.event.patient} </Link> </Fragment> }
                     <br/>
                     <i class="fas fa-user-nurse"></i> Nurse: {this.props.event.nurse} <br/>
                     <i class="fas fa-sign-out-alt"></i> Status: <strong> {this.displayStatus()} </strong> <br/>
                    <i class="fas fa-procedures"></i> Procedure: {this.props.event.procedure}<br/>
                    <i class="fab fa-bitcoin"></i> Price: {this.props.event.price}<br/>
                    <i class="fas fa-hand-holding-usd"></i> Discount: {this.displayDiscount()}% <br/>
                    <i class="fab fa-bitcoin"></i> Total: {(this.props.event.price * (1 - (this.props.event.discount / 100)).toFixed(2))}<br/>
        </Fragment>
    }

    showOperationInfo = () => {
        let profileUrl = '/profile/' + this.props.event.patientInsurance
        return <Fragment> 
             <i class="fas fa-user-injured"></i> Patient: <Link to = {profileUrl}> {this.props.event.patient} </Link> <br/>
             <i class="fas fa-user-md"></i> Doctor #1: {this.props.event.firstDoctor} <br/>
             <i class="fas fa-user-md"></i> Doctor #2: {this.props.event.secondDoctor} <br/>
             <i class="fas fa-user-md"></i> Doctor #3: {this.props.event.thirdDoctor} <br/>
             <i class="fas fa-procedures"></i> Procedure: {this.props.event.procedure}<br/>
             <i class="fab fa-bitcoin"></i> Price: {this.props.event.price}<br/>
        </Fragment>
    }

    render() {
       let appStart = new Date(this.props.event.start).toLocaleDateString("en-US")
       console.log('PR000ps', this.props.event)
        return (
            <Fragment> 
                <Modal
                isOpen={this.props.modal}
                toggle={this.props.toggle}
                className={this.props.className} >
                <ModalHeader toggle={this.props.toggle}>
                {this.props.event.title}
                </ModalHeader>
                <ModalBody>
                <div> {this.props.event.operation ? this.showOperationInfo() : this.showAppointmentInfo()}
                   
                </div>
                <div>
                {this.state.showReport && <DoctorShowExaminationReport
                                                event = {this.props.event} 
                                                report = {this.state.report}
                                                updateReport={this.updateReport}/>}
                </div>

                </ModalBody>
                <ModalFooter>

        {this.props.event.operation 
        ? <Button color="secondary" onClick={() => {this.props.toggle(this.props.event.id, this.state.showRequestDeclinedOK)}}> Close</Button> 
        : 
            !this.state.showConfirmModal ? <Fragment>
            { this.state.canDecline &&
                <Button color='warning' onClick={this.handleDelete} disabled={this.state.showRequestDeclinedOK}>Decline</Button>
            }
            <Button color="secondary" onClick={() => {this.props.toggle(this.props.event.id, this.state.showRequestDeclinedOK)}}> Close</Button> 
        {(this.checkCurrentDate() && this.props.event.status === 'APPROVED') ? 
        <Fragment>
            <Button color="btn btn-success" onClick = {() => {this.setState({showConfirmModal: true})}}>Start</Button>
        </Fragment>
         : 
         this.props.event.status === 'APPROVED' && 
         <Fragment> 
         <BootstrapButton variant = 'outline-success' disabled>Start</BootstrapButton>
         <p> You can only start the appointment on {appStart}</p> 
         </Fragment> } </Fragment> :  <Fragment>
              <p> You are about to start an appointment with {this.props.event.patient}. &emsp;&emsp;&emsp;&emsp;&emsp;&emsp;
             Are you sure? </p> 
             <Button color = "secondary" onClick = {() => {this.setState({showConfirmModal: false})}}> Cancel</Button> 
             <Button color = "btn btn-success" onClick = {() => {this.props.toggleAppointment()}}>  Yes  </Button> 
            </Fragment>}

            {this.state.showRequestDeclinedOK &&
                <p> The appointment has been successfuly declined. </p>
            }
            {this.state.showRequestDeclinedBAD &&
                <p class='text-warning'> The appointment could not declined. Try refreshing you page. </p>
            } 

                </ModalFooter> 

              
            </Modal>
            
            </Fragment>
        )
    }
}

export default AppointmentInfoModal