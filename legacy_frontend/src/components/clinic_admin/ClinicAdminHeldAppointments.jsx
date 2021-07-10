import React, { Component } from 'react';
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import FormControl from 'react-bootstrap/FormControl';
import axios from 'axios';
import ViewGraph from './ViewGraph';

class ClinicAdminHeldAppointments extends Component {
    state = {
        name: '',
        graphGranularity: 'DAILY',
        date: '',
        graphPoints: []
    }

    static contextType = ClinicAdminContext;

    componentDidMount() {
        axios
			.get(
				"/api/clinics/id=" +
				this.context.admin.clinicId,
			)
			.then((response) => {
				this.setState({
					name: response.data.name,
				});
			});
    }

    handleOptionChange = (event) => {
        this.setState({graphGranularity: event.target.value}, () => {
            if(this.state.date != null && this.state.date != '') {
                this.handleGetGraph();
            }
        });
    }

    handleChangeDate = (event) => {
        this.setState({date: event.target.value}, () => {
            if(this.state.date != null && this.state.date != '') {
                this.handleGetGraph();
            }
        })
    }

    handleGetGraph = () => {
        axios.post('/api/clinics/held_appointments', {
            detailLvl: this.state.graphGranularity,
            referentDate: this.state.date
        }).then(response => {
            this.setState({
                graphPoints: response.data
            })
        })
    }

    render() {

        return (
            <div class="row d-flex justify-content-center">
				<div class="col-md-7">
                <br />
					<h3>Clinic {this.state.name}</h3>
                    <h4>Graph of done appointemnts</h4>
                    <br/>
                    <hr class='m4' />

                    <h4>Graph's accurassy in: </h4>
                    <ul>
                        <li>
                            <input
                                type="radio"
                                id="customRadioDaily"
                                name="customRadio"
                                value="DAILY"
                                defaultChecked
                                onChange={this.handleOptionChange}
                            />
                            <label
                                class="text-white"
                                for="customRadioDaily"
                            >
                                daily
                            </label>
                            <div class="check"></div>
                        </li>
                        <li>
                            <input
                                type="radio"
                                id="customRadioWeekly"
                                name="customRadio"
                                value="WEEKLY"
                                onChange={this.handleOptionChange}
                            />
                            <label
                                class="text-white"
                                for="customRadioWeekly"
                            >
                                weekly
                            </label>
                            <div class="check"></div>
                        </li>
                        <li>
                            <input
                                type="radio"
                                id="customRadioMothly"
                                name="customRadio"
                                value="MONTHLY"
                                onChange={this.handleOptionChange}
                            />
                            <label
                                class="text-white"
                                for="customRadioMothly"
                            >
                                monthly
                            </label>
                            <div class="check"></div>
                        </li>
                    </ul>

                    <br />
                    <div class="col-md-7">
                        <div class="input-group">
                                <label for='date'>
                                    Select&nbsp;
                                    {
                                        this.state.graphGranularity == 'DAILY'? "day to view":
                                        this.state.graphGranularity == 'MONTHLY'? "month to view":
                                        "week to view"
                                    }&nbsp;
                                </label>
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#x1F4C5;</span>
                            </div>
                            <FormControl type="date" id='date' locale="en-us" placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeDate} />
                        </div>
                    </div>
                    <br />
                    <div>
                        <ViewGraph graphPoints={ this.state.graphPoints } />
                    </div>

                </div>
            </div>
        );
    }
}
 
export default ClinicAdminHeldAppointments;