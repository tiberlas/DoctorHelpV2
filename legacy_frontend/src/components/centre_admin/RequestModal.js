import React, { Fragment } from 'react'
import { Button as ModalButton, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import Button from 'react-bootstrap/Button'
import axios from 'axios'

class RequestModal extends React.Component {
    
    state = {
        decline: false,
        declineReason: "",
        sending: false,
        success: false,
        conflict: false
    }

    displayUserRequestTable = () => {
        return <Fragment> 
            <table> 
                <tr> 
                    <th scole="row"> <i class="fas fa-user-circle"></i> First name: </th>
                    <td> &emsp;{this.props.request.firstName} </td>
                </tr>
                <tr> 
                    <th scole="row"> <i class="fas fa-user-circle"></i> Last name: </th>
                    <td> &emsp;{this.props.request.lastName} </td>
                </tr>
                <tr> 
                    <th scole="row"> <i class="fas fa-envelope-open"></i> Email: </th>
                    <td> &emsp;{this.props.request.email} </td>
                </tr>
                <tr> 
                    <th scole="row"><i class="fas fa-map-marker-alt"></i> Address: </th>
                    <td>  &emsp;{this.props.request.address} </td>
                </tr>
                <tr> 
                    <th scole="row"><i class="fas fa-city"></i> City: </th>
                    <td>  &emsp;{this.props.request.city} </td>
                </tr>
                <tr> 
                    <th scole="row"><i class="fas fa-globe-africa"></i> State: </th>
                    <td>  &emsp;{this.props.request.state} </td>
                </tr>
                <tr> 
                    <th scole="row"><i class="fas fa-file-medical"></i> Insur. num.: </th>
                    <td >  &emsp;{this.props.request.insuranceNumber} </td>
                </tr>
                <tr> 
                    <th scole="row"><i class="fas fa-mobile-alt"></i> Phone num.: </th>
                    <td>  &emsp;{this.props.request.phoneNumber} </td>
                </tr>

            </table>
        </Fragment>
    }

    updateTextArea = (e) =>{
        this.setState({[e.target.name]: e.target.value}, ()=>{
            console.log('updated', this.state.declineReason)
        })
    }

    sendDecline = () => {
        this.setState({decline: false, sending: true, success: false}, ()=> {
            this.forceUpdate()
            axios.post('http://localhost:8080/api/centreAdmins/declineRequest', {
                email: this.props.request.email,
                declinedDescription: this.state.declineReason
            }).then( response => {
                this.setState({success: true, sending: false}, ()=>{
                    this.props.update()
                 })
            }).catch(error => {
                this.setState({sending: false, conflict: true}, ()=>this.props.update())
            })
        })
    }


    sendAccept = () => {
        this.setState({decline: false, sending: true, success: false}, () => {
            axios.post('http://localhost:8080/api/centreAdmins/acceptRequest', {
                        email: this.props.request.email
             }).then( response => {
                       this.setState({
                           success: true,
                           sending: false
                       }, ()=> this.props.update()) 
             }).catch(error => {
                 this.setState({sending: false, conflict: true}, ()=> {this.props.update()})
             })
        })
    }

    render() {
        return(
            <Fragment>
            <Modal
                    isOpen={this.props.modal}
                    toggle={this.props.toggle}
                    closeTimeoutMS={2000} >
                    <ModalHeader toggle={this.props.toggle}>
                        Request overview
                    </ModalHeader>
                            <ModalBody>

                                <div class="row d-flex justify-content-center">
                                        <div class='col-md-11'>
                                            {this.displayUserRequestTable()}
                                            <br/>
                                        </div>
                                </div>

                                <br/>

                                <div class="row d-flex justify-content-center">
                                        <div class='col-md-11'>

                                        {/* decline clicked:  */}
                                        {/* opet retardirana render logika */}

                                        {this.state.sending ? <div> <p class="text-info">Sending... </p> </div> : <Fragment> 
                                            {this.state.decline && <Fragment> 
                                                <textarea name="declineReason" placeholder="Reasons for declining..." onChange = {this.updateTextArea}/>
                                                <br/>
                                                <Button variant="outline-secondary" onClick = {()=>this.setState({decline: false})}>Back</Button> &nbsp;&nbsp;&nbsp;
                                                <Button variant="outline-primary"  onClick = {this.sendDecline}>Send</Button>
                                                </Fragment>}

                                            {/* decline not clicked:  ako nisi u konfliktu i ako nisi uspesan, prikazi decline i accept */}
                                            {(!this.state.decline && !this.state.success && !this.state.conflict) && <Fragment> <Button variant="outline-danger" onClick={()=>{this.setState({decline: true})}}>Decline</Button>&nbsp;&nbsp;
                                            <Button variant="outline-success" onClick={this.sendAccept}>Accept</Button> </Fragment>}
                                            </Fragment>}

                                        {this.state.success && <div class="valid-feedback d-block"> Success. </div>}
                                        {this.state.conflict && <div class="invalid-feedback d-block"> Something went wrong. Please try again </div>}

                                        </div>
                                </div>

                            </ModalBody>
                    <ModalFooter>
                        
                 <ModalButton class="secondary" onClick={this.props.toggle}> Close</ModalButton>
                    </ModalFooter>
                </Modal>

            </Fragment>
        )
    }
}

export default RequestModal