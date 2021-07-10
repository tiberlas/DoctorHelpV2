import React, {Component} from 'react';
import CentreAdminChangePassword from './centre_admin/CentreAdminChangePassword';
import ClinicAdminChangePassword from './clinic_admin/ClinicAdminChangePassword';
import DoctorChangePassword from './doctor/DoctorChangePassword';
import NurseChangePassword from './nurse/NurseChangePassword';


class FirstTimePasswordChange extends Component {

    render() {
        if(this.props.role === 'CENTRE_ADMINISTRATOR' || this.props.role === 'centreAdmin') {
            return(
                <div>
                    <CentreAdminChangePassword first={true}/>
                </div>
            )
        } else if(this.props.role === 'CLINICAL_ADMINISTRATOR' || this.props.role === 'clinicAdmin') {
            return (<div> <ClinicAdminChangePassword first = {true}/> </div>)
        } else if(this.props.role === 'DOCTOR' || this.props.role === 'doctor') {
            return (<div> <DoctorChangePassword first = {true}/> </div>)
        } else if(this.props.role === 'NURSE' || this.props.role === 'nurse') {
            return (<div> <NurseChangePassword first = {true}/> </div>)
        }
            return(
                <div>
                    <h1>>Work in progress </h1>
                    {/* <CentreAdminChangePassword /> */}
                </div>
            )
        
    }
}

export default FirstTimePasswordChange