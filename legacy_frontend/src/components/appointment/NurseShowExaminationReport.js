import React, { Fragment } from 'react'
import Button from 'react-bootstrap/Button'

const completedStyle = {
    fontStyle: "italic",
    color: "#cdcdcd",
    textDecoration: "line-through"
}

class NurseShowExaminationReport extends React.Component {

    state = {
        isSigned: this.props.report.signed
    }

    signOff = () => {
        this.setState({isSigned: true})
        this.props.signOff()
    }

    generateMedicationList = () => {
        let items = []
        for(let i = 0; i < this.props.report.medicationList.length; i++) {
        items.push(<span class="text-muted" key = {i}> - {this.props.report.medicationList[i].medicationName}</span>)
        items.push(<br/>)
        }
        return items
    }

    render() {

        if(this.props.event.status !== 'DONE')
            return null //problem sa renderovanjem modala nakon klika na DONE modal gde u APPROVED ili AVAILABLE modalu takodje ispisuje ove podatke (a ne bi smeo)
            //... ovo je resenje

        return(
            <Fragment>
                <hr/>
                <h4> Examination report: </h4>
                <br/>
                <i class="fas fa-stethoscope"></i>  Diagnosis: {this.props.report.diagnosis} <br/>
                <i class="fas fa-capsules"></i> Prescriptions:  <br/>
                <p style = {this.state.isSigned ? completedStyle : null}> 
                {this.generateMedicationList()} </p>
                <i class="fas fa-sticky-note"></i>  Additional doctor notes: {this.props.report.advice}
                <br/>
                {!this.state.isSigned && <Button className="btn btn-success" onClick = {this.signOff}>Sign off</Button>}

                <hr/>
            </Fragment>
        )
    }
}

export default NurseShowExaminationReport