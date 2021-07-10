import React from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import axios from 'axios'

class NewMedicationForm extends React.Component {

    constructor() {
        super()
        this.state = {
            medicationName: "",
            medicationDescription: "",
            error: true,
            errorMedicationName: true,
            errorMedicationResponse: false,
            errorDescription: true,
            success: false,
            loading: false
        }
      
    }


    validate = () => {
        this.setState({error: false, errorMedicationResponse: false, errorDescription: false, errorMedicationName: false, success: false}, 
           () => this.props.disabledOff(false))
        if(!this.state.medicationName.trim() || this.state.medicationName.length < 3) {
            this.setState({error: true, errorMedicationName: true}, ()=>{this.props.disabledOn()})
        }

        if(!this.state.medicationDescription.trim() || this.state.medicationDescription.length < 3) {
            this.setState({error: true, errorDescription: true}, ()=>{this.props.disabledOn()})
        }

    }

    handleChange = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        }, () => {this.validate()})
    }

    handleSubmit = () => {

        if(this.state.error)
            return

        this.setState({loading: true}, () => {
            this.submitMedication()
        })
       
    }

    submitMedication = () => {
        axios.post('/api/medication/new', { 

            name: this.state.medicationName,
            description: this.state.medicationDescription
        })
            .then(res => {
                // alert("Successfully added new medication.");
                this.setState({success: true, error: false, errorMedicationResponse: false, loading: false}, ()=>{this.props.disabledOff(true)})
            }).catch(error => {
                this.setState({
                    errorMedicationResponse: true,
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
                   
                    <Form onSubmit = {this.handleSubmit}>
                    <div className={`form-group ${(this.state.errorMedicationName || this.state.errorMedicationResponse)? 'has-danger': ''}`}>
                    <Form.Group controlId="formMedicationName">
                        <Form.Control type="text" name = "medicationName" placeholder="Enter medication name" onChange = {this.handleChange} className={`form-control ${(this.state.errorMedicationName || this.state.errorMedicationResponse) ? 'is-invalid': 'is-valid'}`}/>
                        {(this.state.errorMedicationResponse) && <div class="invalid-feedback"> Medication already exists. </div>}
                    </Form.Group>
                    </div>

                
                    <div className={`form-group ${(this.state.errorDescription)? 'has-danger': ''}`}>
                    <Form.Group controlId="formMedicationDescription">
                        <Form.Control type="text" name = "medicationDescription" placeholder="Description" onChange = {this.handleChange} className={`form-control ${(this.state.errorDescription) ? 'is-invalid': 'is-valid'}`}/>
                        {this.state.success && 
                             <div class="valid-feedback"> Ultra success, added new medication! </div>}
                             
                            {this.state.loading && <div> <p class="text-info">Loading... </p> </div>} 
                    </Form.Group>
                    </div>


                    </Form>
                </div>
             </div>
            </div>
        )
    }
}



export default NewMedicationForm