import React,{Fragment} from 'react'
import Button from 'react-bootstrap/Button'
import axios from 'axios'

const completedStyle = {
    fontStyle: "italic",
    color: "#cdcdcd",
    textDecoration: "line-through"
}

class DoctorShowExaminationReport extends React.Component {

    state = {
        showUpdate: false,
        note: "",
        loading: true
    }

    generateMedicationArray = () => {
        let items = []
        for(let i = 0; i < this.props.report.medicationArray.length; i++) {
        items.push(<span class="text-muted" key = {i}> - {this.props.report.medicationArray[i].medicationName}</span>)
        items.push(<br/>)
        }
        return items
    }

    componentDidMount() {
        this.setState({loading: false})
    }

    handleUpdate = () => {
        this.setState({showUpdate: false}, ()=> {
            axios.put('/api/appointments/update/appointment='+this.props.event.id,
                {
                    note: this.state.note
                }
            ).then(this.props.updateReport(this.state.note))
        })
    }

    handleChange = (e) => {
        this.setState({[e.target.name]: e.target.value})
    }

    render() {

        if(this.props.event.status !== 'DONE')
            return null //problem sa renderovanjem modala nakon klika na DONE modal gde u APPROVED ili AVAILABLE modalu takodje ispisuje ove podatke (a ne bi smeo)

        if(this.state.loading) {
            return(<div> Loading... </div>)
        }

        return(
            <Fragment>
                <hr/>
                <h4> Examination report: </h4>
                <br/>
                <i class="fas fa-stethoscope"></i> Diagnosis: {this.props.report.diagnosis} <br/>
                <i class="fas fa-capsules"></i> Medication:  <br/>
                <p style = {this.props.report.nurseSigned ? completedStyle : null}> 
                {this.generateMedicationArray()} </p>

                {this.props.report.nurseSigned ? <div class="text-muted" style={{fontStyle: 'italic'}}><br/>Signed off by {this.props.report.nurse}</div>
                :  <div class="text-muted"style={{fontStyle: 'italic'}}><br/>Not yet signed off</div>}

                <div class="text-muted" style={{fontStyle: 'italic'}}>Examined by {this.props.event.doctor}</div>
                <br/>
                {!this.state.showUpdate && <Fragment> <i class="far fa-sticky-note"></i> Doctor notes: {this.props.report.note} </Fragment>}
                <br/>

                {this.state.showUpdate 
                ? <Fragment> 
                    <i class="far fa-sticky-note"></i> Doctor notes: <br/>
                    <textarea name="note" defaultValue={this.props.report.note} onChange={this.handleChange}/>
                    <br/>
                    <button type="button" class = "btn btn-secondary" onClick={()=>{this.setState({showUpdate: false})}}>Cancel</button> &nbsp;&nbsp;
                    <Button className="btn btn-success" onClick={this.handleUpdate}>Confirm</Button>
                   </Fragment> 
                   : <Fragment> 
                       {this.props.report.myExamination && <Button className="btn btn-success" onClick={()=>{this.setState({showUpdate: true})}}>Update</Button>} 
                    </Fragment>}
                <hr/>
            </Fragment>
        )
    }
}

export default DoctorShowExaminationReport