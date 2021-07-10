import React, {Component, createContext} from 'react'

export const PatientContext = createContext ()

class PatientContextProvider extends Component {

	state = {
		patient: {
			id: this.props.patient.id, 
			email: this.props.patient.email, 
			firstName: this.props.patient.firstName, 
			lastName: this.props.patient.lastName, 
			address: this.props.patient.address, 
			city: this.props.patient.city, 
			state: this.props.patient.state, 
			phoneNumber: this.props.patient.phoneNumber, 
			birthday: this.props.patient.birthday, 
			insuranceNumber: this.props.patient.insuranceNumber
		}
	}

	componentDidUpdate (prevProps, prevState) {
		if (prevProps.patient !== this.props.patient) {
			this.setState ({
				patient: {
					id: this.props.patient.id, 
					email: this.props.patient.email, 
					firstName: this.props.patient.firstName, 
					lastName: this.props.patient.lastName, 
					address: this.props.patient.address, 
					city: this.props.patient.city, 
					state: this.props.patient.state, 
					phoneNumber: this.props.patient.phoneNumber, 
					birthday: this.props.patient.birthday, 
					insuranceNumber: this.props.patient.insuranceNumber
				}
			})
		}
	}

	render () {
		return (
			<PatientContext.Provider value = {{...this.state}}>
				{this.props.children}
			</PatientContext.Provider>
		);
	}

}

export default PatientContextProvider;