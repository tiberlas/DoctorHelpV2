import React, {Fragment} from 'react'
import axios from 'axios'
import HealthRecord from './HealthRecord'
import {PatientContext} from '../../context/PatientContextProvider'
import ViewProfile from '../ViewProfile'

class ShowPatientHealthRecord extends React.Component {

    static contextType = PatientContext

    state = {
        health_record: {
            firstName: "",
            lastName: "",
            height: "",
            weight: "", 
            diopter: "",
            bloodType: "",
            birthday: "",
            allergyList: []
        }
    }

    componentWillMount() {
        let url = '/api/healthRecord/get/patient='+this.context.patient.insuranceNumber
        axios.get(url).then(response => {
            this.setState( prevState => ({
                health_record: {
                    ...prevState.health_record,
                    firstName: response.data.firstName,
                    lastName: response.data.lastName,
                    height: response.data.height,
                    weight: response.data.weight,
                    diopter: response.data.diopter,
                    bloodType: response.data.bloodType,
                    birthday: new Date(response.data.birthday),
                    allergyList: response.data.allergyList
                }
            }))
        })
    }

    render() {
        return (
                <Fragment>
                <div class="row d-flex justify-content-center">
					<div class='col-md-4'>
					<ViewProfile profile={this.context.patient}/>
                    </div>
                    <div class='col-md-6'>
                    <h1> Record overview </h1>
                    <HealthRecord health_record={this.state.health_record} />
                    </div>
                </div>
                </Fragment>
        )
    }
}

export default ShowPatientHealthRecord