import React, {Component, Fragment} from 'react'
import axios from 'axios';
import UserRequestItem from './UserRequestItem';
import RequestModal from './RequestModal';
class PatientRequests extends Component {

    _isMounted = false

    constructor() {
        super()
        this.state = {
                patientInfo: [],
                showRequestModal: false,
                selectedRequest: {}
        }

    }

    toggle = () =>{
        this.setState({showRequestModal: !this.state.showRequestModal})
    }

    setRequestSelected = (request) => {
        this.setState({selectedRequest: request, showRequestModal: true}, () => {
        })
    }

    componentDidMount = () => {
        this._isMounted = true

        axios.get('/api/centreAdmins/requests',)
        .then(res =>  {
            this.setState({patientInfo: res.data})
        })
        .catch(err => 
            console.log(err)
        )
    }


    update = () => {
        setTimeout(() => {axios.get('/api/centreAdmins/requests',)
        .then(res =>  {
            this.setState({patientInfo: res.data, showRequestModal: false}, ()=>{this.forceUpdate()})
        })
        .catch(err => 
            console.log(err)
        )}, 1000)
    }

    render() {

        var size = Object.keys(this.state.patientInfo).length
        console.log('patient info', this.state.patientInfo)
        if(this._isMounted)
            return(
                <div> 
                <br/>
                <br/>
                    <div class="row d-flex justify-content-center">
                        <div class='col-md-3'>
                            <h3> Patient requests. </h3>
                        </div>  
                    </div>

                <div class="row d-flex justify-content-center">
                <div class='col-md-11'>
                 <br/>

                 {size == 0 ? <div> <br/> <br/> <h4> No new requests at the moment. </h4> </div> : <Fragment> 
                <table class="table table-hover">
                        <thead> 
                            <tr>
                                <th class="text-success" scope="row">
                                <i class="fa fa-envelope-open"></i> Email
                                </th>
                                <th class="text-success" scope="row">
                                    First name
                                </th>
                                <th class="text-success" scope="row">
                                    Last name
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            {this.state.patientInfo.map( request => (
                               <UserRequestItem key={request.email} userRequest = {request} setRequestSelected={this.setRequestSelected}/>
                            ))}
                        </tbody>
                    </table> </Fragment>   }

                   {this.state.showRequestModal && <RequestModal modal={this.state.showRequestModal}
                                 toggle={this.toggle}
                                 request={this.state.selectedRequest}
                            update = {this.update}/> }
                    
                    </div> 
                </div>
                </div>
                         
            )
        else {
            return (
                <div> Loading... </div>
            )
        }
    }
}


export default PatientRequests