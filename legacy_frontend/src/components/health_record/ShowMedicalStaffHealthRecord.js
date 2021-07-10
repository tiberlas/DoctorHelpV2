import React, {Fragment} from 'react'
import axios from 'axios'
import HealthRecord from './HealthRecord'

class ShowMedicalStaffHealthRecord extends React.Component {

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
        let url = '/api/healthRecord/get/patient='+this.props.patient.insuranceNumber
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
            <h1> Record overview </h1>
            <HealthRecord health_record={this.state.health_record} />
            </Fragment>
        )

    }

}

export default ShowMedicalStaffHealthRecord