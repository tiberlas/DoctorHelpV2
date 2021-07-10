import React, { Component } from 'react';
import {ClinicAdminContext} from '../../context/ClinicAdminContextProvider';
import { Redirect } from 'react-router-dom'
import axios from 'axios';
import ChangeProfile from '../ChangeProfile';

class ClinicAdminChangeProfile extends Component {
    
    static contextType = ClinicAdminContext
    state = {
        go_profile: false,
        errorBack: false,
        id: this.context.admin.id,
        firstName: this.context.admin.firstName,
        lastName: this.context.admin.lastName,
        address: this.context.admin.address,
        city: this.context.admin.city,
        state: this.context.admin.state,
        phoneNumber: this.context.admin.phoneNumber,
        birthday: this.context.admin.birthday
    }


    handleSubmit = (user) => {
        this.setState({go_profile: false})
        this.setState({errorBack: false})

        axios.put('/api/clinicAdmins/change', {
                    id: this.state.id,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    address: user.address,
                    city: user.city,
                    state: user.state,
                    phoneNumber: user.phoneNumber
        }).then( (response) => {
            this.props.handleUpdate()
            this.setState({go_profile: true})
        }).catch((error) => {
            this.setState({errorBack: true})
        });
    }

    render() { 
        if(this.state.go_profile == true)
            return (<Redirect to='/clinic-administrator/'></Redirect>);
        return (  
            <ChangeProfile user={this.state} handleSubmit={(user) => this.handleSubmit(user)} errorBack={this.state.errorBack}/>
        );
    }
}
 
export default ClinicAdminChangeProfile;