import React, {Fragment} from 'react'
import NurseRequestCalendar from './NurseRequestCalendar'
import LeaveRequestHistory from '../LeaveRequestHistory'
import {NurseContext} from '../../../context/NurseContextProvider'
import axios from 'axios'

class NurseLeaveRequest extends React.Component {

  static contextType = NurseContext

    state = {
        requests: []
    }


    componentDidMount() {
            axios.get('/api/leave-requests/get-all/nurse='+this.context.nurse.id)
            .then(response=>{
                this.setState({
                    requests: response.data
                })
            })
    }

    update = () => {
        axios.get('/api/leave-requests/get-all/nurse='+this.context.nurse.id)
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
                        <NurseRequestCalendar update = {this.update} />
                    </div>
                  </div>
            </Fragment>
        )
    }
}


export default NurseLeaveRequest