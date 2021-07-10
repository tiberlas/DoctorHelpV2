import React, {Component} from 'react';
import axios from 'axios'
import { PatientContext } from '../../context/PatientContextProvider';


class HealthRecord extends Component {

	state = {
		firstName: this.context.patient.firstName, 
		lastName: this.context.patient.lastName, 
		weight: "UNKNOWN", 
		height: "UNKNOWN", 
		birthday: "UNKNOWN", 
		diopter: "UNKNOWN", 
		bloodType: "UNKNOWN", 
		allergyList: "/"
	}

	static contextType = PatientContext;

	componentDidMount () {
		let path = "/api/patients/health_record";
		axios.get (path)
        .then (response => {
			if (response.status === 404) {
				alert ("You have no health record");

			}
            this.setState ({
				weight: response.data.weight, 
				height: response.data.height, 
				birthday: response.data.birthday, 
				diopter: response.data.diopter, 
				bloodType: response.data.bloodType, 
				allergyList: response.data.allergyList
            });
		})
		.catch (function (error) {
			
		});
	}

	render () {
		return (
			<div class="row d-flex justify-content-center">
                <div class='col-md-3'>
					<div >
						<label class="badge badge-success text-right">First Name:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.firstName}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Last Name:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.lastName}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Weight:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.weight} kg</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Height:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.height}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Birthday:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.birthday}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Diopter:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.diopter}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Blood Type:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.bloodType}</label>
					</div>
					<div >
						<label class="badge badge-success text-right">Alergies:</label>&nbsp;&nbsp;&nbsp;
						<label >{this.state.allergyList}</label>
					</div>
                </div>
                
            </div>
		);
	}

}


export default HealthRecord;