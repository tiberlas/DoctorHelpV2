
import React, {Fragment} from 'react'
import DoctorRequestCalendar from './DoctorRequestCalendar'
import LeaveRequestHistory from '../LeaveRequestHistory'
import {DoctorContext} from '../../../context/DoctorContextProvider'
import axios from 'axios'

class DoctorLeaveRequest extends React.Component {
    static contextType = DoctorContext

    state = {
        requests: []
    }


    componentDidMount() {
            axios.get('/api/leave-requests/get-all/doctor='+this.context.doctor.id)
            .then(response=>{
                this.setState({
                    requests: response.data
                })
            })
    }

    update = () => {
        axios.get('/api/leave-requests/get-all/doctor='+this.context.doctor.id)
        .then(response=>{
            this.setState({
                requests: response.data
            })
        })
    }

    render() {
        return(
            <Fragment> 
                  <div class="row d-flex justify-content-center">
                    <div class='col-md-5'>
                        <br/>
                        <LeaveRequestHistory requests={this.state.requests}/>
                    </div>

                    <div class='col-md-6'>
                        <br/>
                        <DoctorRequestCalendar update = {this.update} />
                    </div>
                  </div>
            </Fragment>
        )
    }
}

export default DoctorLeaveRequest