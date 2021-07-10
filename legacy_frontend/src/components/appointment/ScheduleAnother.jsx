import React, { Component } from 'react';
import axios from 'axios';
import TimePicker from 'react-time-picker/dist/TimePicker';
import FormControl from 'react-bootstrap/FormControl';
import Select from 'react-select';

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

class ScheduleAnother extends Component {
    state = {
        currentAppointment: this.props.currentAppointment, //samo id od trenutnog appointmenta
        firstFreeDate: '01.09.2020 90:30',
        recomended: '',
        date: '',
        time: '',
        selectedOption: 'appointment',
        procedures: {},
        procedureTypeId: '',
        disableOperatin: false,
        disableSubmit: true,
        successedSchedule: false, //disables all inputs and submit button
        errorDate: true,
        errorTime: true,
        errorInUse: false,
        errorType: true,
        errorDateInPast: false
    }

    componentDidMount() {
        this.getFirstFreeAppoiintment()

        axios.get("/api/procedure+types/operation/all")
            .then(response => {
                this.setState({ procedures: response.data }, () => {
                    if (this.state.procedures.length == 0) {
                        this.setState({ disableOperatin: true })
                    }
                })
            }).catch(error => {
                this.setState({ disableOperatin: true })
            })
    }

    getFirstFreeAppoiintment = () => {
        axios.get("/api/doctors/schedules/first_free")
            .then(response => {
                this.setState({ firstFreeDate: response.data })
            })
    }

    createTypeItems = () => {
        let items = [];
        var size = Object.keys(this.state.procedures).length;
        items.push(<option key={size} name='procedureTypeId' value='' selected="selected"> ---- </option>);
        for (let i = 0; i < size; i++) {
            items.push(<option key={i} name="procedureTypeId" value={this.state.procedures[i].id} >{this.state.procedures[i].name}: {this.state.procedures[i].price}</option>);
        }
        return items;
    }

    handleCheckIfDateInFuturre = (dateAndTime) => {
        let tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        let selectedDate = Date.parse(dateAndTime);

        console.log('for', selectedDate)
        console.log('tomorrow', tomorrow)

        if (selectedDate < tomorrow) {
            return false;
        } else {
            return true;
        }
    }

    handleValidationTimeAndDate = () => {
        this.setState({ errorDate: false, errorTime: false, disableSubmit: true, errorDateInPast: false }, () => {
            if (this.state.date === null || this.state.date === "") {
                this.setState({ errorDate: true })
            } else if (this.state.time === null || this.state.time === "") {
                this.setState({ errorTime: true })
            } else {
                //provera izabranog datuma
                let dateAndTime = this.state.date + " " + this.state.time;
                if (!this.handleCheckIfDateInFuturre(dateAndTime)) {
                    this.setState({ errorDateInPast: true, disableSubmit: true })
                }

                if (this.state.selectedOption === 'appointment') {
                    axios.post("/api/doctors/schedules/check", {
                        dateAndTimeString: dateAndTime
                    }).then(response => {
                        if (response.status === 201) {
                            //trazeni termin je zauzet i predlozen je drugi
                            this.setState({ errorInUse: true, errorDate: true, errorTime: true, recomended: response.data })
                        } else {
                            this.setState({ errorInUse: false, errorDate: false, errorTime: false }, () => {
                                //izabani datum je valida i submit je dozvoljen
                                this.setState({ disableSubmit: false })
                            })
                        }
                    }).catch(error => {
                        this.setState({ errorInUse: true, errorDate: true, errorTime: true })
                    })
                } else {
                    this.setState({ disableSubmit: false })
                }
            }
        })
    }

    handleChange = (event) => {
        this.setState({
            selectedOption: event.target.value, firstFreeDate: 'calculating', date: '', time: '', errorDate: true, errorTime: true, errorType: true, disableSubmit: true
        }, () => {
            if (this.state.selectedOption === 'appointment') {
                this.getFirstFreeAppoiintment()
            }
        });
    }

    handleChangeTime = (time) => {
        this.setState({ time: time }, () => { this.handleValidationTimeAndDate() })
    }

    handleChangeDate = (event) => {
        //jquery :(
        let date = document.getElementById('date').value;
        this.setState({ date: date }, () => { this.handleValidationTimeAndDate() })
    }

    handlerTypeChange = (event) => {
        let val = event.target.value

        this.setState({ procedureTypeId: val }, () => {
            if (this.state.procedureTypeId === '' || this.state.procedureTypeId === null || this.state.procedureTypeId === undefined) {
                this.setState({ errorType: true })
            } else {
                this.setState({ errorType: false });
            }
        })
    }

    handleSubmit = (event) => {
        event.preventDefault();
        if (this.state.selectedOption === 'appointment') {

            axios.post('/api/appointments/request/doctor', {
                oldAppointmentID: parseInt(this.state.currentAppointment),
                dateAndTime: this.state.date + " " + this.state.time,
            }).then(respense => {
                this.setState({ successedSchedule: true, disableSubmit: true });
            }).catch(error => {
                alert("Please refresh the page and try agan")
            })

        } else {

            axios.post('/api/operations/request/doctor', {
                appointmentId: parseInt(this.state.currentAppointment),
                dateAndTimeString: this.state.date + " " + this.state.time,
                procedureTypeId: this.state.procedureTypeId
            }).then(respense => {
                this.setState({ successedSchedule: true })
            }).catch(error => {
                alert("Please refresh the page and try agan")
            })
        }
    }

    render() {
        return (
            <form onSubmit={this.handleSubmit}>
                <div class="form-group row">
                    <div class="col-sm-10">

                        <p>Schedule type:</p>

                        <span class="form-grup">
                            <div class="custom-control custom-radio">
                                <input type="radio" id="customRadio1" name="radio" class="custom-control-input" onChange={this.handleChange} value="appointment" checked={this.state.selectedOption === 'appointment'} disabled={this.state.successedSchedule} />
                                <label class="custom-control-label text-white" for="customRadio1">Appointment</label>
                            </div>
                            <div class="custom-control custom-radio">
                                <input type="radio" id="customRadio2" name="radio" class="custom-control-input" onChange={this.handleChange} value="operation" checked={this.state.selectedOption === 'operation'} disabled={this.state.successedSchedule} />
                                <label class="custom-control-label text-white" for="customRadio2">Operation</label>
                            </div>
                        </span>
                        <br />

                        {this.state.selectedOption === 'operation' &&
                            <div>
                                <div className={`form-group ${this.state.errorType ? 'has-danger' : ''}`}>
                                    <label for="type">Select operation</label>
                                    <div class="input-group">
                                        <div class="input-group-preppend">
                                            <span class="input-group-text">&#9815;</span>
                                        </div>
                                    <select multiple="" className={`form-control ${this.state.errorType ? 'is-invalid' : 'is-valid'}`} id="type" name='typeId' onChange={this.handlerTypeChange} disabled={this.state.successedSchedule}>
                                        {this.createTypeItems()}
                                    </select>
                                    </div>
                                </div>
                            </div>
                        }

                        <div>
                            {this.state.selectedOption === 'appointment' &&
                                <div>
                                    <label>First available date</label>
                                    <p>{this.state.firstFreeDate}</p>
                                </div>
                            }

                            <div>
                                <label for='date'>Select date</label>
                                <div class="input-group">
                                        <div class="input-group-preppend">
                                            <span class="input-group-text">&#x1F4C5;</span>
                                        </div>
                                <FormControl type="date" id='date' placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeDate} className={`form-control ${this.state.errorDate ? 'is-invalid' : 'is-valid'}`} disabled={this.state.successedSchedule} />
                                </div>
                            </div>
                            <div>
                                <label for='time'>Select time</label>
                                <div class="input-group">
                                        <div class="input-group-preppend">
                                            <span class="input-group-text">&#x231A;</span>
                                        </div>
                                <TimePicker name='duration' id='time' onChange={this.handleChangeTime} locale="en-us" value={this.state.time} className={`form-control ${this.state.errorTime ? 'is-invalid' : 'is-valid'}`} disabled={this.state.successedSchedule} />
                                </div>
                            </div>
                            {this.state.errorDateInPast && <p class='text-danger'>Date and time cannot be in the past.</p>}
                            {this.state.errorInUse && <p class="text-danger">Selected date/time is already appointed. Try selecting another date or time!</p>}
                            {this.state.errorInUse && this.state.recomended != "" && <p class="text-success">Recomended schedule is {this.state.recomended}</p>}

                        </div>

                        <br />
                        <input type="submit" class="btn btn-success" value="Request" disabled={this.state.disableSubmit || this.state.successedSchedule} />
                        {this.state.successedSchedule &&
                            <p class='text-success'>
                                Successfully sent a request.
                            </p>
                        }
                    </div>
                </div>
            </form>
        );
    }
}

export default ScheduleAnother;