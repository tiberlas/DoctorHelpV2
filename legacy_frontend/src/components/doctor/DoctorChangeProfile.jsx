import React, { Component } from 'react';
import {DoctorContext} from '../../context/DoctorContextProvider';
import { Redirect } from 'react-router-dom'
import axios from 'axios';
import ChangeProfile from '../ChangeProfile';

class DoctorChangeProfile extends Component {
    static contextType = DoctorContext;
    state = { 
        goto_profile: false,
        email: this.context.doctor.email,
        firstName: this.context.doctor.firstName,
        lastName: this.context.doctor.lastName,
        address: this.context.doctor.address,
        city: this.context.doctor.city,
        state: this.context.doctor.state,
        phoneNumber: this.context.doctor.phoneNumber,
        birthday: this.context.doctor.birthday,
        errorBack: false
     }

    handleSubmit = (user) => {

        axios.put('/api/doctors/change', {
                    id: 100,
                    firstName: user.firstName,
                    lastName: user.lastName,
                    address: user.address,
                    city: user.city,
                    state: user.state,
                    phoneNumber: user.phoneNumber
        }).then(
            this.props.handleUpdate,
            this.setState({goto_profile: true})
      ).catch(error =>{
          alert('ERROR')
            this.setState({errorBack: true})
      });
    }


    render() { 
        if(this.state.goto_profile==true) {
            return (
                <Redirect to='/doctor/' ></Redirect>
            );
        }
        return ( 
            <ChangeProfile user={this.state} handleSubmit={(user) => this.handleSubmit(user)} errorBack={this.state.errorBack}/>
          );
    }
}
 
export default DoctorChangeProfile;