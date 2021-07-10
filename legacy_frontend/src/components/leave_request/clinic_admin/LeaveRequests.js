import React, {Fragment} from 'react'
import axios from 'axios'
import LeaveRequestItem from './LeaveRequestItem'
import LeaveRequestStaffModal from './LeaveRequestStaffModal'

class LeaveRequests extends React.Component {

    state = {
        leaveRequests: [],
        showRequestModal: false,
        selectedRequest: {}
    }


     componentDidMount = () => {
        // this._isMounted = true

        axios.get('/api/leave-requests/get-admin',)
        .then(res =>  {
            this.setState({leaveRequests: res.data})
        })
        .catch(err => 
            console.log(err)
        )
    }



    toggle = () =>{
        this.setState({showRequestModal: !this.state.showRequestModal})
    }

    setRequestSelected = (request) => {
        this.setState({selectedRequest: request, showRequestModal: true}, () => {
        })
    }

    update = () => {
        setTimeout(()=>{
             axios.get('/api/leave-requests/get-admin',)
        .then(res =>  {
            this.setState({leaveRequests: res.data, showRequestModal: false})
        })
        .catch(err => 
            console.log(err)
        )
        }, 1200)
    }


    render() {
        let size = 1 //fali size
        return (<Fragment>
               <div> 
                <br/>
                <br/>
                    <div class="row d-flex justify-content-center">
                        <div class='col-md-3'>
                            <h3> Leave requests. </h3>
                        </div>  
                    </div>

                <div class="row d-flex justify-content-center">
                <div class='col-md-11'>
                 <br/>

                 {size == 0 ? <div> <br/> <br/> <h4> No new requests at the moment. </h4> </div> : <Fragment> 
                <table class="table table-hover">
                        <thead> 
                            <tr >
                                <th class="text-success" scope="row">
                                <i class="fas fa-user-circle"></i> Full name
                                </th>
                                <th class="text-success" scope="row">
                                <i class="fas fa-user-md"></i> Role
                                </th>
                                <th class="text-success" scope="row">
                                <i class="fas fa-sign-out-alt"></i> Leave type
                                </th>
                                <th class="text-success" scope="row">
                                <i class="fas fa-calendar-minus"></i> From
                                </th>
                                <th class="text-success" scope="row">
                                <i class="fas fa-calendar-plus"></i>  Until
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.leaveRequests.map( request => (
                               <LeaveRequestItem key={request.staffId + request.staffRole} userRequest = {request} setRequestSelected={this.setRequestSelected}/>
                            ))}
                        </tbody>
                    </table> </Fragment>   }

                   {this.state.showRequestModal && <LeaveRequestStaffModal modal={this.state.showRequestModal}
                                 toggle={this.toggle}
                                 request={this.state.selectedRequest}
                            update = {this.update}/> }
                    
                    </div> 
                </div>
                </div>
        </Fragment>)
    }
}
export default LeaveRequests