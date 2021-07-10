import React, { Component } from 'react';
import Modal from 'react-bootstrap/Modal';
import axios from 'axios';
import TimePicker from 'react-time-picker/dist/TimePicker';
import FormControl from 'react-bootstrap/FormControl';

const fontStyles = {
    option: provided => ({
    ...provided,
    color: 'black'
    }),
    control: provided => ({
    ...provided,
    color: 'black'
    }),
    singleValue: provided => ({
    ...provided,
    color: 'black'
    })
}

class ScheduleAppointment extends Component {
    state = {
        procedure: "",
        patient: "",
        room: '',
        doctorList: [],
        nurse: '',
        date: '',
        time: '',
        dateAndTime: '',
        procedureId: 0,
        doctorSelected: '',
        scheduleRecomendedDate: '',
        doctorRecomendedDate: '',
        errorDate: false,
        errorTime: false,
        errorDateAndTime: false,
        errorDoctor: false,
        success: false,
        fatalError: false
    }
    
    handleGetDoctors = () => {
        axios.get('/api/doctors/all/specialization='+this.state.procedureId)
            .then(response => {
                this.setState({doctorList: response.data})
            })
    }

    handelDateAndTimeConversion = () => {
        let parts = this.state.dateAndTime.split(" ");
        let dates = parts[0].split("/");
        let svDate = dates[2] +"-"+ dates[0] +"-"+ dates[1];
        this.setState({date: svDate}, () => {this.handleChanngeDateAndTime()})

        if(parts[2] == 'PM') {
            let hoursAndMinutes = parts[1].split(":")
            let hours = parseInt(hoursAndMinutes[0]);
            let minutes = parseInt(hoursAndMinutes[1]);
            hours += 12;

            let timeString = hours + ":"+minutes
            this.setState({time: timeString}, () => {this.handleChanngeDateAndTime()})
        } else {
            this.setState({time: parts[1]}, () => {this.handleChanngeDateAndTime()});
        }
    }
    
    componentDidUpdate(prevProps, prevState) {
        if (prevProps.roomId !== this.props.roomId) {
            axios.get('/api/rooms/one/room='+this.props.roomId)
                .then(response => {
                    this.setState({room: response.data.name+' #'+response.data.number+''})
                })

            axios.get('/api/appointments/requests/id='+this.props.appointmentId)
                .then(response => {
                    this.setState({procedure: response.data.type+' '+response.data.duration+'h',
                                    patient: response.data.patient,
                                    doctorSelected: response.data.doctor,
                                    procedureId: response.data.typeId,
                                    dateAndTime: response.data.date
                                }, ()=> {
                                    this.handleGetDoctors()
                                    this.handelDateAndTimeConversion()
                                })
                })
        }
    }

    createDoctorItems = () => {
        let items = []; 
        var size = Object.keys(this.state.doctorList).length;
        for (let i = 0; i < size; i++) {
            if(this.state.doctorList[i].email == this.state.doctorSelected) {
                items.push(<option key={i} name = "doctor" selected="selected" value={this.state.doctorList[i].email} >{this.state.doctorList[i].email}</option>);
            } else {
                items.push(<option key={i} name = "doctor" value={this.state.doctorList[i].email} >{this.state.doctorList[i].email}</option>);
            }
        }
        return items;
    }

    handlerChangeDoctor = (event) => {
        let val = event.target.value
        this.setState({doctorSelected: val, errorDoctor: false})
    }

    handleChanngeDateAndTime = () => {
        this.setState({dateAndTime: this.state.date +' '+ this.state.time, errorDate: false})
    }

    handleChangeTime = (time) => {
        this.setState({time: time, errorTime: false}, () => {this.handleChanngeDateAndTime() })
    }

    handleChangeDate = (event) => {
        this.setState({date: event.target.value}, () => {this.handleChanngeDateAndTime() });
    }

    handleBless = (event) => {
        event.preventDefault();

        this.setState({errorDateAndTime: false, errorDoctor: false}, () => {
            axios.post('/api/appointments/bless', {
                patientEmail: this.state.patient,
                doctorEmail: this.state.doctorSelected,
                roomId: this.props.roomId,
                procedureId: this.state.procedureId,
                dateAndTime: this.state.dateAndTime,
                appointmentRequestedId: this.props.appointmentId
            }).then(response => {
                //aleluja
                this.setState({success: true})
            }).catch(error => {
                if(error.response.status == 406) {
                    let status = error.response.data.split("#");
                    if(status[0] == 'DOCTOR') {
                        this.setState({errorDoctor: true, doctorRecomendedDate: status[1]})
                    } else {
                        this.setState({errorDateAndTime: true, errorDate: true, errorTime: true, scheduleRecomendedDate: status[1]})
                    }
                } else {
                    //fatalan error
                    this.setState({fatalError: true})
                }
            })
        })
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
                    <Modal.Title id="contained-modal-title-vcenter">Assign a room</Modal.Title>
                </Modal.Header>
                <form onSubmit={this.handleBless}>
                    <Modal.Body>
                        <div class="form-group">
                                <label class="control-label" for="disabledInput"><i class="fas fa-procedures"></i> Procedure: &emsp; {this.state.procedure} </label>
                            
                        </div>
                        <div class="form-group">
                                <label class="control-label" for="disabledInput"><i class="fas fa-user-injured"></i> Patient: &emsp; {this.state.patient} </label>
                        </div>
                        <div class="form-group">
                                <label class="control-label" for="disabledInput"><i class="fas fa-door-open"></i>  Room&emsp;{this.state.room}</label>
                        </div>
                        <hr class="my-4" />

                        <div className={`form-group ${this.state.errorDoctor? 'has-danger': ''}`}>
                            <label for="doctor">Doctor</label>
                            <select multiple="" className={`form-control ${this.state.errorDoctor? 'is-invalid': 'is-valid'}`} id="doctor" name='doctor' onChange={this.handlerChangeDoctor} disabled={this.state.success}>
                                {this.createDoctorItems()}
                            </select>
                            { (this.state.errorDoctor) && <div class="invalid-feedback"> Work schedule is occupied. Try assigning {this.state.doctorRecomendedDate} </div>}
                        </div>

                        <div>
                            <label for='date'>Date</label>
                            <FormControl type="date" id='date' placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeDate} value={this.state.date} className={`form-control ${this.state.errorDate? 'is-invalid': 'is-valid'}`} disabled={this.state.success}/>
                        </div>
                        <div>
                            <label for='time'>Time</label>
                            <TimePicker name='duration' id='time' onChange={this.handleChangeTime} locale="us" value={this.state.time} className={`form-control ${this.state.errorTime? 'is-invalid': 'is-valid'}`} disabled={this.state.success}/>
                        </div>
                        {this.state.errorDateAndTime && 
                            <div class="text-danger"> Work schedule or there is an assigned appointment. Try assigning {this.state.scheduleRecomendedDate} </div>
                        }

                        {this.state.success &&
                            <p class="text-success">Successfully blessed appointment!</p>
                        }
                        {this.state.fatalError &&
                            <p class="text-danger">Something went wrong, please refresh the page and try again.</p>
                        }

                    </Modal.Body>
                    <Modal.Footer>
                        <button id="close_dialog" type="button" class="btn btn-secondary" data-dismiss="modal" onClick={() => {this.props.onHide(this.state.success)}}>Close</button>
                        <input id="bless_appointment" type="submit" class="btn btn-success" value="Bless" disabled={this.state.success}/>
                    </Modal.Footer>
                </form>
            </Modal>
        );
    }
}
 
export default ScheduleAppointment;