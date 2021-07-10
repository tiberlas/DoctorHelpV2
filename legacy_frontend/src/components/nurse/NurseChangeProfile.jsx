import React, { Component } from 'react';
import {NurseContext} from '../../context/NurseContextProvider';
import { Redirect } from 'react-router-dom'
import axios from 'axios';
import ChangeProfile from '../ChangeProfile';

class NurseChangeProfile extends Component {
    static contextType = NurseContext;
    state = { 
        goto_profile: false,
        email: this.context.nurse.email,
        firstName: this.context.nurse.firstName,
        lastName: this.context.nurse.lastName,
        address: this.context.nurse.address,
        city: this.context.nurse.city,
        state: this.context.nurse.state,
        phoneNumber: this.context.nurse.phoneNumber,
        birthday: this.context.nurse.birthday,
        errorBack: false
     }

    handleSubmit = (user) => {

        axios.put('/api/nurses/change', {
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

    handlerChange = (event) => {
        let nam = event.target.name;
        let val = event.target.value;
        this.setState({[nam]: val});
  }

    render() { 
        if(this.state.goto_profile==true) {
            return (
                <Redirect to='/nurse/' ></Redirect>
            );
        }
        return ( 
            <ChangeProfile user={this.state} handleSubmit={(user) => this.handleSubmit(user)} errorBack={this.state.errorBack}/>
         );
    }
}
 
export default NurseChangeProfile;