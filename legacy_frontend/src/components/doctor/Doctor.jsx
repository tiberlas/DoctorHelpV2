import React, { Component } from 'react';
import DoctorHeader from './DoctorHeader.jsx'
import DoctorProfile from './DoctorProfile.jsx'
import { UserContext } from '../../context/UserContextProvider'
import DoctorContextProvider from '../../context/DoctorContextProvider';
import {Route, Redirect} from "react-router-dom";
import {Switch} from "react-router-dom";
import DoctorChangeProfile from './DoctorChangeProfile.jsx';
import DoctorChangePassword from './DoctorChangePassword.jsx';
import axios from 'axios';
import ViewPatientProfile from '../patient/ViewPatientProfile';
import DoctorCalendar from './DoctorCalendar'
import HandlePatientList from './HandlePatientList.jsx';
import DoctorRequestedOperations from './DoctorRequestedOperations.jsx';
import DoctorLeaveRequest from '../leave_request/doctor/DoctorLeaveRequest.js';

class Doctor extends Component {
    state = { 
        id: 0,
        email: "",
        firstName: "",
        lastName: "",
        address: "",
        city: "",
        state: "",
        phoneNumber: "",
        birthday: "",
        clinicId: 1,
        operation: false
     }

    static contextType = UserContext

    componentDidMount() {
        this.handleDoctor();

        axios.get('/api/doctors/schedules/operation/requested/count')
            .then(response => {
                if(response.data == 'OPERATIONS') {
                    this.setState({operation: true})
                }
            })
    }

    handleDoctor = () => {
        axios.get("/api/doctors/profile")
            .then(response =>  {
                this.setState({
                    id: response.data.id,
                    email: response.data.email,
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    address: response.data.address,
                    city: response.data.city,
                    state: response.data.state,
                    phoneNumber: response.data.phoneNumber,
                    birthday: response.data.birthday,
                    clinicId: response.data.clinicId
                })
            })
    }

    render() { 
        var doctor = {id: this.state.id, firstName: this.state.firstName, lastName: this.state.lastName, address: this.state.address, state: this.state.state, city: this.state.city, phoneNumber: this.state.phoneNumber, email: this.state.email, birthday: this.state.birthday, clinicId: this.state.clinicId, role: 'doctor'} 
        return ( 
            <div>
                <DoctorContextProvider doctor={doctor} >
                    <DoctorHeader operation={this.state.operation} logout={() => this.props.logout ()}/>

                    <div>
                        <Switch>
                            <Route exact path="/doctor/"> <DoctorProfile /></Route>
                            <Route exact path="/doctor/profile"> <DoctorProfile /></Route>
                            <Route exact path="/doctor/profile/change"> <DoctorChangeProfile handleUpdate={this.handleDoctor}/></Route>
                            <Route exact path="/doctor/profile/change/password"> <DoctorChangePassword /></Route>
                            <Route exact path = "/doctor/schedule"><DoctorCalendar regime='schedule'/></Route>
                            <Route exact path="/doctor/leave-request"> <DoctorLeaveRequest/> </Route>
                            <Route path="/profile/"> <ViewPatientProfile medical_staff = {doctor}/></Route>
                            <Route exact path = "/doctor/patients"><HandlePatientList /></Route>
                            <Route exact path = "/doctor/requested/operations"><DoctorRequestedOperations /> </Route>

                        </Switch>
                    </div>
                </DoctorContextProvider>
            </div>
         );
    }
}
 
export default Doctor;