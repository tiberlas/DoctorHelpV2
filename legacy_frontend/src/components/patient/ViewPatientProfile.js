import React, {Component} from 'react';
import axios from 'axios'
import DoctorViewProfile from '../view_profile/doctor_view_profile/DoctorViewProfile'
import NurseViewProfile from '../view_profile/NurseViewProfile'


class ViewPatientProfile extends Component {

    state = {
        patient: {},
        loading: true
    }

    componentDidMount() {
        let id = window.location.href.split('profile/')[1] //get the forwarded insurance id from url
        console.log("id is " + id)
        let url = '/api/patients/profile/' + id //append it to get url
        
        setTimeout(() => { axios.get(url).then(response => {
           this.setState({
               patient: response.data
           })
        }).then(
            this.setState({loading: false})
        )}, 1200)
    }

	render () {
		return (
            this.state.loading ? <h1> <i class="fas fa-circle-notch fa-spin"></i> Loading... </h1> :
			<div>
                {this.props.medical_staff.role === 'doctor' && <DoctorViewProfile //ako si doca, imas opcije za doktora
                                                                patient={this.state.patient}
                                                                doctor = {this.props.medical_staff}
                                                                />}
                {this.props.medical_staff.role === 'nurse' && <NurseViewProfile
                                                                patient={this.state.patient}
                                                                nurse = {this.props.medical_staff}/>}               
			</div>
		);
	}

}

export default ViewPatientProfile;