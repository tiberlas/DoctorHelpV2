
import React from 'react'
import Footer from './footer.jsx';
import ClinicAdministrator from './clinic_admin/ClinicAdministrator';
import {UserContext} from '../context/UserContextProvider'
import CenterAdministrator from './centre_admin/CentreAdministrator.js';
import Doctor from './doctor/Doctor.jsx'
import Patient from './patient/Patient'
import LoginPage from '../LoginPage.js';
import Nurse from './nurse/Nurse.jsx';
import Header from './Header.jsx';

class TempHome extends React.Component {

    static contextType = UserContext

    handleLogout () {
        
    }

    render() {
        return(
            <div>
                {this.props.role === 'centreAdmin' && <CenterAdministrator logout={() => this.props.logout ()}/>}
                {this.props.role === 'clinicAdmin' && <ClinicAdministrator logout={() => this.props.logout ()}/>}
                {this.props.role === 'doctor' && <Doctor logout={() => this.props.logout ()}/>}
                {this.props.role === 'patient' && <Patient logout={() => this.props.logout ()}/>}
                {this.props.role === 'nurse' && <Nurse logout={() => this.props.logout ()}/>}
                <br/>
                <br/>
                <br/>
               {/* <Footer /> */}
            </div>

        )

    };
}

export default TempHome