import React, { Component } from 'react';
import {ClinicAdminContext} from '../../context/ClinicAdminContextProvider';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import ChangePassword from '../ChangePassword';

class PatientChangePassword extends Component {
    static contextType = ClinicAdminContext

    state = {
        go_profile: false,
        errorBack: false
    }

    handleSubmit = (data) => {
        this.setState({errorBack: false})

        axios.put('/api/patients/change/password', {
                    oldPassword: data.oldPassword,
                    newPassword: data.newPassword
        }).then((responce) => {
            this.setState({go_profile: true})
        }).catch((error)=> {
            this.setState({errorBack: true})
        });
    }

    render() {
        if(this.state.go_profile === true)
            return(<Redirect to='/patient/'></Redirect> );
        
        return ( 
            <ChangePassword handleSubmit={(data) => this.handleSubmit(data)} errorBack={this.state.errorBack}/>
         );
    }
}
 
export default PatientChangePassword;

