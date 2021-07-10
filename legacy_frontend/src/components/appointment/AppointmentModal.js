import React, {Fragment} from 'react'
import Tab from 'react-bootstrap/Tab'
import Nav from 'react-bootstrap/Nav'
import {Row, Col} from 'react-bootstrap'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import OverviewTable from './OverviewTable'
import AppointmentHealthRecord from '../health_record/AppointmentHealthRecord'
import ExaminationReport from './ExaminationReport'
import axios from 'axios'
import ScheduleAnother from './ScheduleAnother'

class AppointmentModal extends React.Component {
    
    state = {
        selectedDiagnosis: "",
        selectedMedication: [],
        note: "",
        confirmFinish: false,
        startAppointment: new Date() //rewrite the appointment date with the date when the doctor clicked on it
    }

    handleDiagnosisChange = (option) => {
        this.setState({selectedDiagnosis: option.label})
    }

    handleMedicationChange = (options) => {
        if(options === null) {
            this.setState({selectedMedication: []})
            return
        }
        let medication = []
        for(let i=0; i<options.length; i++) {
            medication.push(options[i].label)
        }
        this.setState({selectedMedication: medication})
    }

    handleNotesChange = (e) => {
        this.setState({note: e.target.value})
    }

    componentWillReceiveProps(props) {
        this.setState({ confirmFinish: props.showConfirmAppointment})
    }


    handleFinish = () => {
        let url = '/api/appointments/done=' + this.props.event.id
        axios.put(url, {
            diagnosis: this.state.selectedDiagnosis,
            medicationList: this.state.selectedMedication,
            note: this.state.note,
            dateStart: this.state.startAppointment
        }).then(response => {
            this.props.update()
            this.props.toggleAppointment()
        }) 
    }
  
    render() {
        return (
            <Fragment> 
                <Modal
                isOpen={this.props.modal}
                toggle={this.props.toggle}
                className={this.props.className + " modal-lg"} 
                >
                    <ModalHeader className = "text-align:center">
                    Appointment
                    </ModalHeader>

                    <Tab.Container id="left-tabs-example" defaultActiveKey="first">
                        <Row>
                            <Col sm={3}>
                            <Nav variant="pills" className="flex-column">
                                <Nav.Item>
                                <Nav.Link eventKey="first">Overview</Nav.Link>
                                </Nav.Item>
                                <Nav.Item>
                                <Nav.Link eventKey="second">Health record</Nav.Link>
                                </Nav.Item>

                                <Nav.Item>
                                <Nav.Link eventKey="third">Examination report</Nav.Link>
                                </Nav.Item>

                                <Nav.Item>
                                <Nav.Link eventKey="fourth">Schedule another</Nav.Link>
                                </Nav.Item>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                                <br/>
                            </Nav>
                            </Col>
                            <Col sm={9}>
                            <Tab.Content>
                                <Tab.Pane eventKey="first">
                                    <ModalBody>
                                        <OverviewTable data = {this.props.event}/>
                                    </ModalBody>
                                </Tab.Pane>
                                <Tab.Pane eventKey="second">
                                <ModalBody>
                                   <AppointmentHealthRecord data = {this.props.event} />
                                 </ModalBody>
                                </Tab.Pane>
                                <Tab.Pane eventKey="third">
                                <ModalBody>
                                   <ExaminationReport 
                                    data={this.props.event}
                                    handleDiagnosisChange={this.handleDiagnosisChange}
                                    handleMedicationChange={this.handleMedicationChange}
                                    handleNotesChange={this.handleNotesChange} />
                                 </ModalBody>
                                </Tab.Pane>
                                <Tab.Pane eventKey="fourth" >
                                    <ModalBody>
                                        <ScheduleAnother currentAppointment = {this.props.event.id}/>
                                    </ModalBody>
                                </Tab.Pane>
                            </Tab.Content>
                            </Col>
                        </Row>
                    </Tab.Container>


                 
                    <ModalFooter>
                    {!this.state.confirmFinish && <Button color="secondary" onClick={() => {this.setState({confirmFinish: true})}}> Finish </Button>}
                    {this.state.confirmFinish &&  <Fragment>
                        <p> You are about to finish the appointment.
                        Should you proceed? </p><br/>
                        <Button color = "secondary" onClick = {() => {this.setState({confirmFinish: false})}}> Back</Button> 
                        <Button color = "primary" onClick = {this.handleFinish}>Yes</Button> 
                    </Fragment>}
                   
                    </ModalFooter>
                </Modal>
            </Fragment>

        )
    }
}

export default AppointmentModal