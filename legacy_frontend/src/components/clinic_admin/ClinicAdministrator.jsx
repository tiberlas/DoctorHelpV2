import React, { Component } from 'react';
import ClinicAdminHeader from './ClinicAdminHeader';
import HandlingRooms from './HandlingRooms';
import {Route, Redirect} from "react-router-dom";
import {Switch} from "react-router-dom";
import ClinicAdminProfile from './ClinicAdminProfile';
import { UserContext } from '../../context/UserContextProvider'
import ClinicAdminChangeProfile from './ClinicAdminChangeProfile';
import ClinicAdminContextProvider from '../../context/ClinicAdminContextProvider';
import ClinicAdminMedicalStaff from './ClinicAdminMedicalStaff';
import ClinicAdminChangePassword from './ClinicAdminChangePassword';
import axios from 'axios';
import Clinic from '../clinic/Clinic';
import ClinicChangeProfile from './ClinicChangeProfile';
import NewRoom from '../rooms/NewRoom.jsx';
import HandleingProcedureTypes from './HandleingProcedureTypes';
import NewProcedureType from '../procedureType/NewProcedureType';
import HandleingPredefinedAppointments from './HandleingPredefinedAppointments';
import NewPredefinedAppointment from '../predefined_appointments/NewPredefinedAppointment';
import CreateMedicalStaff from './CreateMedicalStaff';
import RoomSchedule from '../rooms/RoomSchedule';
import ClinicAdminAppointmentRequests from './ClinicAdminAppointmentRequests';
import ClinicAdminOperationRequest from './ClinicAdminOperationRequest';
import RequestedAppointment from '../requesting_appointment/RequestedAppointment';
import RequestedOpration from '../requesting_operations/RequestedOperation.jsx';
import ClinicAdminHeldAppointments from './ClinicAdminHeldAppointments';
import ClinicAdminClinicIncome from './ClinicAdminClinicIncome';
import LeaveRequests from '../leave_request/clinic_admin/LeaveRequests';

class ClinicAdministrator extends Component {
    state = {
            id: 1, 
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
        //console.log(this.context.user);
        this.handleClinicAdmin();
    }

    handleClinicAdmin = () => {
        axios.get("/api/clinicAdmins/profile")
            .then(response =>  {
                this.setState({
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
        var admin = {id: this.state.id, firstName: this.state.firstName, lastName: this.state.lastName, address: this.state.address, state: this.state.state, city: this.state.city, phoneNumber: this.state.phoneNumber, email: this.state.email, birthday: this.state.birthday, clinicId: this.state.clinicId} 
        return (
             <div>
                <ClinicAdminContextProvider admin={admin}>
                <ClinicAdminHeader logout={() => this.props.logout ()}></ClinicAdminHeader>

                <div>
                <Switch>
                    <Route exact path="/clinic-administrator/clinic" ><Clinic clinicId={this.state.clinicId}/> </Route>
                    <Route exact path="/clinic-administrator/clinic/change" ><ClinicChangeProfile clinicId={this.state.clinicId}/> </Route>
                    <Route exact path="/clinic-administrator/clinic/income" ><ClinicAdminClinicIncome /> </Route>
                    <Route exact path="/clinic-administrator/heald-appointemtns" ><ClinicAdminHeldAppointments /> </Route>
                    <Route exact path="/clinic-administrator/profile" ><ClinicAdminProfile /> </Route>
                    <Route exact path="/clinic-administrator/" ><ClinicAdminProfile /> </Route>
                    <Route exact path="/clinic-administrator/profile/change" ><ClinicAdminChangeProfile  handleUpdate={this.handleClinicAdmin}/> </Route>
                    <Route exact path="/clinic-administrator/rooms" ><HandlingRooms /> </Route>
                    <Route exact path='/clinic-administrator/rooms/add'> <NewRoom /> </Route>
                    <Route exact path="/clinic-administrator/procedure-types" ><HandleingProcedureTypes /> </Route>
                    <Route exact path='/clinic-administrator/procedure-types/add'> <NewProcedureType /> </Route>
                    <Route exact path='/clinic-administrator/predefined-appointments'> <HandleingPredefinedAppointments /> </Route>
                    <Route exact path='/clinic-administrator/predefined-appointments/add'> <NewPredefinedAppointment /> </Route>
                    <Route exact path='/clinic-administrator/medical-staff'> <ClinicAdminMedicalStaff /> </Route>
                    <Route exact path='/clinic-administrator/medical-staff/add'> <CreateMedicalStaff /> </Route>
                    <Route exact path='/clinic-administrator/requests/appointments'> <ClinicAdminAppointmentRequests/> </Route>
                    <Route exact path='/clinic-administrator/requests/operations'> <ClinicAdminOperationRequest /> </Route>
                    <Route exact path='/clinic-administrator/requests/leaves'> <LeaveRequests /> </Route>
                    <Route exact path='/clinic-administrator/profile/change/password'> <ClinicAdminChangePassword /> </Route>
                    <Route path='/request/appointment/'> <RequestedAppointment /> </Route>
                    <Route path='/request/operation/'> <RequestedOpration /> </Route>
                    <Route path='/schedule/'> <RoomSchedule /> </Route>
                </Switch>
                </div>
                </ClinicAdminContextProvider>
            </div> );
    }
}
 
export default ClinicAdministrator