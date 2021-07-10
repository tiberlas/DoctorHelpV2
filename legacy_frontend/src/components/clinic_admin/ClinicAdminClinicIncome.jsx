import React, { Component } from 'react';
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import FormControl from 'react-bootstrap/FormControl';
import axios from 'axios';

class CLinicAdminClinicIncome extends Component {
    state = {
        name: '',
        startDate: '',
        endDate: '',
        income: 0,
        disabledSubmit: true,
        errorDatesNotInOred: false
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

    handleDateValidation = () => {
        if(this.state.startDate != null && this.state.startDate != ''
            && this.state.endDate != null && this.state.endDate != '') {
                if(this.state.endDate < this.state.startDate) {
                    this.setState({disabledSubmit: true, errorDatesNotInOred: true})
                } else {
                    this.setState({disabledSubmit: false, errorDatesNotInOred: false})
                }
        } else {
            this.setState({disabledSubmit: true, errorDatesNotInOred: false})
        }
    }

    handleChangeStartDate = (date) => {
        this.setState({startDate: date.target.value}, () => {this.handleDateValidation()});
    }

    handleChangeEndDate = (date) => {
        this.setState({endDate: date.target.value}, () => {this.handleDateValidation()});
    }

    handleSubmit = (event) => {
        event.preventDefault();

        axios.post('/api/clinics/income', {
            beginDate: this.state.startDate,
            endDate: this.state.endDate
        }).then(response => {
            if(response.data != null) {
                this.setState({income: response.data})
            } else {
                this.setState({income: 0})
            }
        })
    }

    render() { 
        return (
            <div class="row d-flex justify-content-center">
				<div class="col-md-7">
                    <br />
					<h3>Clinic {this.state.name}</h3>
                    <h4>Clinic income in a given time periond</h4>
                    <br/>
                    <hr class='m4' />

                    <div class="col-md-6">
                    <form onSubmit={this.handleSubmit} >
                    <div>
                        <div class="input-group">
                                <label for='date'>Start&nbsp;date&nbsp;</label>
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#x1F4C5;</span>
                            </div>
                            <FormControl type="date" id='date' locale="en-us" placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeStartDate} className={`form-control ${this.state.errorDatesNotInOred ? 'is-invalid' : 'is-valid'}`}/>
                        </div>
                    </div>

                    <div>
                        <div class="input-group">
                                <label for='date'>End&nbsp;date&nbsp;&nbsp;&nbsp;</label>
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#x1F4C5;</span>
                            </div>
                            <FormControl type="date" id='date' locale="en-us" placeholder="Date in format: dd/mm/yyyy" onChange={this.handleChangeEndDate} className={`form-control ${this.state.errorDatesNotInOred ? 'is-invalid' : 'is-valid'}`}/>
                        </div>
                    </div>
                    {this.state.errorDatesNotInOred &&
                        <p class="text text-danger">End date must be after start date.</p>
                    }

                    <br />
                    <div>
                        <div class="input-group">
                            <label for='date'>Income&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
                            <div class="input-group-preppend">
                                <span class="input-group-text">&#x20bf;</span>
                            </div>
                                <input type="number" value={this.state.income} disabled/>
                        </div>
                    </div>
                    <br />
                    <input type="submit" class="btn btn-success"  value="show" disabled={this.state.disabledSubmit}/>

                    </form>
                    </div>
                </div>
            </div>
        );
    }
}
 
export default CLinicAdminClinicIncome;