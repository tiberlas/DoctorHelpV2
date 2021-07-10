import React, { Component } from 'react';
import NurseHeader from './NurseHeader.jsx'
import NurseProfile from './NurseProfile.jsx'
import { UserContext } from '../../context/UserContextProvider'
import NurseContextProvider from '../../context/NurseContextProvider';
import {Route, Redirect} from "react-router-dom";
import {Switch} from "react-router-dom";
import NurseChangeProfile from './NurseChangeProfile.jsx';
import NurseChangePassword from './NurseChangePassword.jsx';
import axios from 'axios';
import PatientList from './PatientList'
import ViewPatientProfile from '../patient/ViewPatientProfile'
import PerscriptionList from './PerscriptionList.js';
import NurseCalendar from './NurseCalendar.js';
import NurseLeaveRequest from '../leave_request/nurse/NurseLeaveRequest.js';

class Nurse extends Component {
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
        clinicId: 1
     }

    static contextType = UserContext

    componentDidMount() {
        this.handleNurse();
    }

    handleNurse = () => {
        axios.get("/api/nurses/profile")
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
        var nurse = {id: this.state.id, firstName: this.state.firstName, lastName: this.state.lastName, address: this.state.address, state: this.state.state, city: this.state.city, phoneNumber: this.state.phoneNumber, email: this.state.email, birthday: this.state.birthday, clinicId: this.state.clinicId, role: 'nurse'} 
        return ( 
            <div>
                <NurseContextProvider nurse={nurse} >
                    <NurseHeader logout={() => this.props.logout ()}/>

                    <div>
                        <Switch>
                            <Route exact path="nurse/"> <NurseProfile /></Route>
                            <Route exact path="/nurse/profile"> <NurseProfile /></Route>
                            <Route exact path="/nurse/profile/change"> <NurseChangeProfile handleUpdate={this.handleNurse}/></Route>
                            <Route exact path="/nurse/profile/change/password"> <NurseChangePassword /></Route>
                            <Route exact path="/nurse/patient-list"> <PatientList/> </Route>
                            <Route exact path="/nurse/perscription-list"> <PerscriptionList/> </Route>
                            <Route exact path="/nurse/schedule"> <NurseCalendar regime = 'schedule'/> </Route>
                            <Route exact path="/nurse/leave-request"> <NurseLeaveRequest/> </Route>
                            <Route path = "/profile/"> <ViewPatientProfile medical_staff = {nurse}/> </Route> {/*prosledi "context" nurse-a jer nzm kako da drzim dva konteksta u isto vreme u jednoj klasi*/}
                        </Switch>
                    </div>
                </NurseContextProvider>
            </div>
         );
    }
}
 
export default Nurse;