import React, {Fragment} from 'react'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import {Link} from 'react-router-dom'
import ShowExaminationReport from './NurseShowExaminationReport'
import axios from 'axios'


class NurseAppointmentInfoModal extends React.Component {

    state = {
        showReport: false,
        report: {}
    }

    componentWillReceiveProps(props) { //ako je zavrsen appointment, prikazi izvestaj, u suprotnom ga ne prikazuj
        if(props.event.status === 'DONE') {
            axios.get('/api/nurses/perscription/appointment='+props.event.id).then(response => {
                this.setState({report: response.data, showReport: true})
            })
        } else {
            this.setState({showReport: false})
        }
    }

    signOff = () => {
        axios.put('/api/nurses/signOff/appointment='+this.props.event.id).then(response => {
        })
    }

    displayStatus = () => {
        let firstCharacter = this.props.event.status.substr(0,1)
        let restOfCharacters = this.props.event.status.substr(1, this.props.event.status.length).toLowerCase().replace(/\_/g,' ')
        return firstCharacter + restOfCharacters
    }


    render() {
        let profileUrl = '/profile/' + this.props.event.patientInsurance
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
                <div>
                <i class="fas fa-user-injured"></i> Patient:  <Link to = {profileUrl}>  {this.props.event.patient} </Link> <br/>
                <i class="fas fa-user-md"></i> Doctor: {this.props.event.doctor} <br/>
                <i class="fas fa-sign-out-alt"></i> Status: <strong>{this.displayStatus()}</strong> <br/>
                <i class="fas fa-procedures"></i>  Procedure: {this.props.event.procedure} <br/>
                <i class="fab fa-bitcoin"></i> Price: {this.props.event.price} <br/>
                <i class="fas fa-hand-holding-usd"></i> Discount: {this.props.event.discount}% <br/>
                    <i class="fab fa-bitcoin"></i> Total: {(this.props.event.price * (1 - (this.props.event.discount / 100))).toFixed(2)} <br/>
                </div>
                <div> 
                {this.state.showReport && <ShowExaminationReport
                                                event = {this.props.event} 
                                                report = {this.state.report}
                                                signOff = {this.signOff}/>}
                </div>
                </ModalBody>
                <ModalFooter>

            <Button color="secondary" onClick={this.props.toggle}> Close</Button> 
                </ModalFooter> 

              
            </Modal>
            
            </Fragment>
        )
    }
}


export default NurseAppointmentInfoModal