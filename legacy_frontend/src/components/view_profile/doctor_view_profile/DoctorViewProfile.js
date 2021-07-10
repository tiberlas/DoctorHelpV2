import React, {Fragment} from 'react'
import DoctorCalendar from '../../doctor/DoctorCalendar'
import ViewProfile from '../../ViewProfile';
import Button from 'react-bootstrap/Button'
import {Route, Link, Redirect} from "react-router-dom";
import ShowMedicalStaffHealthRecord from '../../health_record/ShowMedicalStaffHealthRecord'
import '../button-group.css'
class DoctorViewProfile extends React.Component {
    
    state = {
        doctor: {},
        displayHealthRecord: false,
        displayMedicalHistory: false,
        regime: "profile",
        display: true //if true -> profile else history
    }

    componentWillReceiveProps = (props) => {
        this.setState({doctor: props.doctor}) //mora se refaktorisati da radi sa kontekstom...
    }

    handleDisplay = () => {
        if(this.state.displayMedicalHistory && !this.state.displayHealthRecord) {
            this.setState({display: true, regime: 'history'}, ()=>{this.forceUpdate()})
        } else if(this.state.displayHealthRecord && !this.state.displayMedicalHistory) {
            this.setState({display: false, regime: 'profile'}, ()=>{this.forceUpdate()})
        } else {
            this.setState({display: true, regime: 'profile'}, ()=>{this.forceUpdate()})
        }
    }

    render() {
        return (
            <Fragment>
                 <div class="row d-flex justify-content-center">
                    <div class='col-md-4'>
                        <br/>
                        <br/>
                        <ViewProfile profile={this.props.patient}/>
                        <div class="btn-group"> 
                        {(this.props.patient.showHealthRecord //ako smes da prikazes health record, i ako dugme vec nije kliknuto, prikazi health-record dugme
                                && !this.state.displayHealthRecord && !this.state.displayMedicalHistory) 
                                && <Button className="btn btn-success" onClick={()=>{this.setState({displayHealthRecord: true, displayMedicalHistory: false}, ()=>{this.handleDisplay()})}}> Health record</Button>}
                        
                        {this.state.displayHealthRecord 
                        && <div>
                            <br/>
                            <Button className="btn btn-secondary" onClick={()=>{this.setState({displayMedicalHistory: false, displayHealthRecord: false}, ()=> {
                                this.handleDisplay()
                            })}}>Back</Button>
                            <br/>
                            <Button className="btn btn-success" onClick={()=>{this.setState({displayMedicalHistory: true, displayHealthRecord: false}, ()=>{this.handleDisplay()})}}>Medical history</Button>
                           </div>}
                        {this.state.displayMedicalHistory && 
                        <div> 
                            <Button className="btn btn-secondary" onClick={()=>{this.setState({displayMedicalHistory: false, displayHealthRecord: true}, ()=>{this.handleDisplay()})}}>Back</Button> 
                        </div>}
                        </div>
                    </div>
                    <div class="col-md-6">
                        <br/>
                        <br/> 
                        {this.state.display ?
                           <DoctorCalendar medical_staff = {this.state.doctor} regime={this.state.regime}/> :
                           <ShowMedicalStaffHealthRecord patient = {this.props.patient}/>
                        }
                    </div>
                </div>
            </Fragment> 
        )
    }
}

export default DoctorViewProfile