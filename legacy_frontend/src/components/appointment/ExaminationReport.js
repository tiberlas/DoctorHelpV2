import React, {Fragment} from 'react'
import axios from 'axios'
import Select from 'react-select'
import Button from 'react-bootstrap/Button'
const fontStyles = {
    option: provided => ({
    ...provided,
    color: 'black'
    }),
    control: provided => ({
    ...provided,
    color: 'black'
    }),
    singleValue: provided => ({
    ...provided,
    color: 'black'
    })
}

class ExaminationReport extends React.Component {

    state = {
        diagnosisList: {},
        medicationList: {},
        diagnosisOptions: [],
        medicationOptions: [],
        showNote: false
        
    }

    componentDidMount() {
        axios.get('/api/diagnoses/all').then( response => {
            this.setState({
                diagnosisList: response.data
            })
            let items = []; 
            var size = Object.keys(this.state.diagnosisList).length;
    
            items.push({
                label: 'No diagnosis',
                value: 'No diagnosis'
            })
            for (let i = 0; i < size; i++) {
                 let option = {
                     label: this.state.diagnosisList[i].name,
                     value: this.state.diagnosisList[i].name
                 }             
                 items.push(option);
            }

            this.setState({
                diagnosisOptions: items
            })
        })

        axios.get('/api/medication/all').then(response => {
            this.setState({medicationList: response.data})

            let items = []; 
            var size = Object.keys(this.state.medicationList).length;

            for (let i = 0; i < size; i++) {
                 let option = {
                     label: this.state.medicationList[i].name,
                     value: this.state.medicationList[i].name
                 }             
                 items.push(option);
            }

            this.setState({
                medicationOptions: items
            })
        })
    }


    render() {
        return (
            <div> 
                <form>
                <div class="form-group row">
                    <div class="col-sm-10">
                    <label for="diagnosisSelect"><i class="fas fa-stethoscope"></i> Diagnosis</label>
                    <Select 
                        id="diagnosisSelect" 
                        styles={fontStyles} 
                        className="basic-single" 
                        classNamePrefix="select" 
                        name="diagnosis" 
                        options={this.state.diagnosisOptions} 
                        defaultValue={this.state.diagnosisOptions[0]} 
                        onChange = {this.props.handleDiagnosisChange}
                    />
                    <br/>
                    <label for="medicationSelect"> <i class="fas fa-capsules"></i> Medication </label>
                    <Select
                        id="medicationSelect"
                        isMulti
                        name="medication"
                        options={this.state.medicationOptions}
                        className="basic-multi-select"
                        classNamePrefix="select"
                        onChange={this.props.handleMedicationChange}
                        styles={fontStyles}
                    />

                <br/>
                <br/>
                <br/>

                {this.state.showNote === false && <Button className="btn btn-info " onClick={()=>{this.setState({showNote: true})}}>Add note</Button>}
                            {this.state.showNote === true 
                            &&  <Fragment>
                                <label for='doctorNotes'> <i class="fas fa-sticky-note"></i> Additional notes</label>
                                <br/>
                            <textarea id="doctorNotes" placeholder="Write any appointment notes here..." onChange={this.props.handleNotesChange} style={{resize: "none"}}/>
                            </Fragment>
                            }
                   
                    </div>
                </div>
                </form>
            </div>
        )
    }
}

export default ExaminationReport