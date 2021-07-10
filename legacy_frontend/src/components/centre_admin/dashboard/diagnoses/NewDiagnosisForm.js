import React from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import axios from 'axios'

class NewDiagnosisForm extends React.Component {

    constructor() {
        super()
        this.state = {
            
            diagnosisName: "",
            diagnosisDescription: "",
            error: true,
            errorDiagnosisName: true,
            errorDiagnosisResponse: false,
            errorDescription: true,
            success: false,
            loading: false
        }
      
    }

    validate = () => {
        this.setState({error: false, errorDiagnosisName: false, errorDiagnosisResponse: false, errorDescription: false, success: false}, ()=>{
            this.props.disabledOff(false)
        })
        if(!this.state.diagnosisName.trim() || this.state.diagnosisName.length < 3) {
            this.setState({error: true, errorDiagnosisName: true}, ()=>{this.props.disabledOn()})
        }

        if(!this.state.diagnosisDescription.trim() || this.state.diagnosisDescription.length < 3) {
            this.setState({error: true, errorDescription: true}, ()=>{this.props.disabledOn()})
        }

    }

    handleChange = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        }, ()=> {this.validate()})
    }

    handleSubmit = () => {
        if(this.state.error) {
            return 
        }

        this.setState({loading: true}, () => {
            this.submitDiagnosis()
        })
        
    }

    submitDiagnosis = () => {
        axios.post('/api/diagnoses/new', { 

            name: this.state.diagnosisName,
            description: this.state.diagnosisDescription
        })
            .then(res => {
                this.setState({
                    success: true,
                    errorDiagnosisResponse: false,
                    error: false,
                    loading: false
                }, ()=>{this.props.disabledOff(true)})
            }).catch(error => {
                this.setState({
                    errorDiagnosisResponse: true,
                    success: false,
                    error: true,
                    loading: false
                }, ()=>{this.props.disabledOn()})
            })
    }

    render() {
        return (
            <div> 
            <div class="row d-flex justify-content-center">
                <div class='col-md-11'>
                {/* <h1>>New diagnosis </h1> */}
                <Form onSubmit = {this.handleSubmit}>
                <div className={`form-group ${(this.state.errorDiagnosisName || this.state.errorDiagnosisResponse)? 'has-danger': ''}`}>
                <Form.Group controlId="formDiagnosisName">
                    <Form.Control type="text" name = "diagnosisName" placeholder="Enter diagnosis name" onChange = {this.handleChange} className={`form-control ${(this.state.errorDiagnosisName || this.state.errorDiagnosisResponse) ? 'is-invalid': 'is-valid'}`}/>
                    {this.state.errorDiagnosisResponse && <div class="invalid-feedback"> Diagnosis already exists. </div>}
                </Form.Group>
                </div>

                <div className={`form-group ${(this.state.errorDescription)? 'has-danger': ''}`}>
                <Form.Group controlId="formDiagnosisDescription">
                    <Form.Control type="text" name = "diagnosisDescription" placeholder="Description" onChange = {this.handleChange} className={`form-control ${(this.state.errorDescription) ? 'is-invalid': 'is-valid'}`}/>
                    {this.state.success && 
                             <div class="valid-feedback"> Good success, added new diagnosis! </div>
                            }
                     {this.state.loading && <div> <p class="text-info">Loading... </p> </div>} 
                </Form.Group>
                </div>

                {/* <input type='submit' value='Create' className={`btn btn-success ${this.state.error ? 'disabled': ''}`}/> */}
                </Form>
               </div>
            </div>
            </div>
        )
    }
}



export default NewDiagnosisForm