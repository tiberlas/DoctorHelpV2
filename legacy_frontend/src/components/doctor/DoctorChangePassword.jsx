import React, { Component } from 'react';
import {DoctorContext} from '../../context/DoctorContextProvider';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import ChangePassword from '../ChangePassword';

class DoctorChangePassword extends Component {
    static contextType = DoctorContext

    state = {
        go_profile: false,
        errorBack: false
    }

    handleSubmit = (data) => {
        this.setState({errorBack: false})

        axios.put('/api/doctors/change/password', {
                    oldPassword: data.oldPassword,
                    newPassword: data.newPassword
        }).then((responce) => {
            this.setState({go_profile: true})
        }).catch((error)=> {
            this.setState({errorBack: true})
        });
    }

    render() {
        if(this.state.go_profile === true) {
            if(this.props.first === true) {
                window.location.href='/login'
            }
            return(<Redirect to='/doctor/'></Redirect> );
        }

        return ( 
            <ChangePassword handleSubmit={(data) => this.handleSubmit(data)} errorBack={this.state.errorBack}/>
         );
    }
}
 
export default DoctorChangePassword;

