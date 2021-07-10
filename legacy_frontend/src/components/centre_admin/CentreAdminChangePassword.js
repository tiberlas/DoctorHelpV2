import React, { Component } from 'react';
import {CentreAdminContext} from '../../context/CentreAdminContextProvider';
import { Redirect } from 'react-router-dom';
import axios from 'axios';
import ChangePassword from '../ChangePassword';

class CentreAdminChangePassword extends Component {
    static contextType = CentreAdminContext

    state = {
        go_profile: false,
        errorBack: false
    }

    handleSubmit = (data) => {
        this.setState({errorBack: false})

        axios.put('/api/centreAdmins/change/password', {
                    oldPassword: data.oldPassword,
                    newPassword: data.newPassword
        }).then((responce) => {
            this.setState({go_profile: true})
        }).catch((error)=> {
            this.setState({errorBack: true})
        });  
    }

    render() {
        if(this.state.go_profile == true) {
            if(this.props.first === true) {
                window.location.href='/login'
            } else {
                return(<Redirect to='/centreAdmin/profile'></Redirect> );
            }
        }
        
        return ( 
            <ChangePassword handleSubmit={(data) => this.handleSubmit(data)} errorBack={this.state.errorBack}/>
         );
    }
}
 
export default CentreAdminChangePassword;

