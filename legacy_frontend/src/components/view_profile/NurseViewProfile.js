import React, {Fragment} from 'react'
import ViewProfile from '../ViewProfile';
import Button from 'react-bootstrap/Button'
import ShowMedicalStaffHealthRecord from '../health_record/ShowMedicalStaffHealthRecord'
import './button-group.css'
import NurseCalendar from '../nurse/NurseCalendar';

class NurseViewProfile extends React.Component {

    state = {
        nurse: {},
        displayHealthRecord: false,
        displayMedicalHistory: false,
        display: false //if true -> history else health record
    }

    componentWillReceiveProps = (props) => {
        this.setState({nurse: props.nurse})
    }

    handleDisplay = () => {
        if(this.state.displayMedicalHistory && !this.state.displayHealthRecord) {
            this.setState({display: true}, ()=>{this.forceUpdate()})
        } else if(this.state.displayHealthRecord && !this.state.displayMedicalHistory) {
            this.setState({display: false}, ()=>{this.forceUpdate()})
        } else {
            this.setState({display: false}, ()=>{this.forceUpdate()})
        }
    }


    render() {
        return(
            <Fragment>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-4'>
                    <br/>
                    <br/>
                    <ViewProfile profile={this.props.patient}/>
                    <div class="btn-group"> 
                    {(this.props.patient.showHealthRecord //ako smes da prikazes health record, i ako dugme vec nije kliknuto, prikazi health-record dugme
                        && !this.state.displayHealthRecord  && !this.state.displayMedicalHistory) 
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
                           <NurseCalendar medical_staff = {this.state.doctor} regime='history'/> :
                           this.state.displayHealthRecord && <ShowMedicalStaffHealthRecord patient = {this.props.patient}/>
                        }
                    </div>
                    
                </div>
            </Fragment>
        )
    }
}

export default NurseViewProfile