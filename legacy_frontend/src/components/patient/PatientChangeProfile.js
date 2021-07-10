import React, {Component} from 'react';
import { PatientContext } from '../../context/PatientContextProvider';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import ChangeProfile from '../ChangeProfile';



class PatientChangeProfile extends Component {

	static contextType = PatientContext

	state = {
		to_profile: false, 
		errorBack: false,
		id: this.context.patient.id, 
		firstName: this.context.patient.firstName, 
		lastName: this.context.patient.lastName, 
		address: this.context.patient.address, 
		city: this.context.patient.city, 
		state: this.context.patient.state, 
		phoneNumber: this.context.patient.phoneNumber, 
		birthday: this.context.patient.birthday, 
	}

	handleSubmit = async (user) => {
		this.setState({to_profile: false})
        this.setState({errorBack: false})

		axios.put ('/api/patients/change', {
			
				id: this.context.patient.id, 
				firstName: user.firstName,
                lastName: user.lastName,
                address: user.address,
                city: user.city,
                state: user.state,
                phoneNumber: user.phoneNumber
			
		}).then( (response) => {
            this.props.updateData()
            this.setState({to_profile: true})
        }).catch((error) => {
            this.setState({errorBack: true})
        });; 
	}

	handleChange = (event) => {
		let nam = event.target.name;
		let val = event.target.value;
		this.setState({[nam]: val});
	}

	render () {
		this.bul = false;

		if (this.state.to_profile === true) {
			return (
				<Redirect to ='/patient/profile' />
			);
		}
		else {
			return (
				<ChangeProfile user={this.state} handleSubmit={(user) => this.handleSubmit(user)} errorBack={this.state.errorBack}/>
			);
		}
	}

}

export default PatientChangeProfile;