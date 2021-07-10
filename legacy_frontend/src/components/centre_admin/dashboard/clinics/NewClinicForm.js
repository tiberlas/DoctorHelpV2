import React, { Fragment } from 'react'
import Form from 'react-bootstrap/Form'
import Button from 'react-bootstrap/Button'
import axios from 'axios'
import { Redirect } from 'react-router-dom'


class NewClinicForm extends React.Component {

    constructor() {
        super()
        this.state = {
            clinicName: "",
            clinicAddress: "",
            clinicDescription: "", 
            error: true,
            errorName: true,
            errorAddress: true,
            errorDescription: true,
            errorClinicName: false,
            errorCity: true,
            errorState: true,
            clinicCity: "",
            clinicState: "",
            success: false,
            loading: false
        }
       
       
    }

    isCharNumber(c){
        return c >= '0' && c <= '9';
    }

    validate = () => {
        this.setState({error: false, errorName: false, errorAddress: false, errorDescription: false, errorClinicName: false, success:false, errorCity: false, errorState: false}, ()=>{
            this.props.disabledOff(false)
        })
        if(!this.state.clinicName.trim() || this.state.clinicName.length < 3) {
            this.setState({error: true, errorName: true}, ()=>this.props.disabledOn())
        }

        if(!this.state.clinicDescription.trim() || this.state.clinicDescription.length < 3) {
            this.setState({error: true, errorDescription: true}, ()=>this.props.disabledOn())
        }

        if(!this.state.clinicAddress.trim() || this.state.clinicAddress.length < 3 || !this.isCharNumber(this.state.clinicAddress[0])) {
            
            this.setState({error: true, errorAddress: true}, ()=>this.props.disabledOn())
        }

        if(!this.state.clinicCity.trim() || this.state.clinicCity.length < 3) {
            this.setState({error: true, errorCity: true}, ()=>this.props.disabledOn())
        }

        if(!this.state.clinicState.trim() || this.state.clinicState.length < 3) {
            this.setState({error: true, errorState: true}, ()=>this.props.disabledOn())
        }
    }


    handleChange = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        }, () => {this.validate()})
    }

    handleSubmit = () => {
        // event.preventDefault()

        if(this.state.error)
            return

        this.setState({loading: true}, () => {
            this.submitClinic()
        })

     
    }

    submitClinic = () => {
        axios.post('/api/clinics/newClinic', { 

            name: this.state.clinicName,
            address: this.state.clinicAddress,
            city: this.state.clinicCity,
            state: this.state.clinicState,
            description: this.state.clinicDescription
        })
            .then(res => {
                // alert("Successfully added new clinic.");
                this.setState({success:true, error: false, errorClinicName: false, loading: false}, ()=>{
                    this.props.disabledOff(true)
                })

            }).catch(error => { 
                this.setState({errorClinicName: true, success: false, error: true, loading: false}, ()=>this.props.disabledOn())
            })
    }

    render() {

        return (
            
                <div class="row d-flex justify-content-center">
                    <div class='col-md-11'>

                    <Form onSubmit = {this.handleSubmit}>
                    <div className={`form-group ${(this.state.errorName || this.state.errorClinicName)? 'has-danger': ''}`}>
                    <Form.Group controlId="formClinicName">         
                        <Form.Control type="text" name = "clinicName" placeholder="Clinic name" onChange = {this.handleChange} className={`form-control ${(this.state.errorName || this.state.errorClinicName) ? 'is-invalid': 'is-valid'}`}/>
                        {this.state.errorClinicName ? <div class="invalid-feedback">Clinic with that name already exists. </div>
                        : <Form.Text className="text-muted">
                        Clinic has to have a sensible name.
                        </Form.Text> }
                     
                    </Form.Group>
                    </div>

                    <div className={`form-group ${this.state.errorAddress? 'has-danger': ''}`}>
                    <Form.Group controlId="formClinicAddress">
                        <Form.Control type="text" name = "clinicAddress" placeholder="Address" onChange = {this.handleChange} className={`form-control ${(this.state.errorAddress)? 'is-invalid': 'is-valid'}`}/>
                        {this.state.errorAddress &&  <div class="invalid-feedback"> Invalid address format </div>}
                    </Form.Group>
                    </div>

                    <div className={`form-group ${this.state.errorCity? 'has-danger': ''}`}>
                    <Form.Group controlId="formClinicCity">
                        <Form.Control type="text" name = "clinicCity" placeholder="City" onChange = {this.handleChange} className={`form-control ${(this.state.errorCity)? 'is-invalid': 'is-valid'}`}/>
                    </Form.Group>
                    </div>

                    <div className={`form-group ${this.state.errorState? 'has-danger': ''}`}>
                    <Form.Group controlId="formClinicState">
                        <Form.Control type="text" name = "clinicState" placeholder="State" onChange = {this.handleChange} className={`form-control ${(this.state.errorState)? 'is-invalid': 'is-valid'}`}/>
                    </Form.Group>
                    </div>

                    <div className={`form-group ${this.state.errorDescription? 'has-danger': ''}`}>
                    <Form.Group controlId="formClinicDescription">
                        <Form.Control type="text" name = "clinicDescription" placeholder="Description" onChange = {this.handleChange} className={`form-control ${(this.state.errorDescription) ? 'is-invalid': 'is-valid'}`} />
                        {this.state.success && <Fragment> 
                             <div class="valid-feedback"> Great success, added new clinic! </div>
                             {/* <Redirect to='/clinic/add'/> */}
                             </Fragment>}
                        {this.state.loading && <div> <p class="text-info">Loading... </p> </div>} 
                    </Form.Group>
                    </div>


                    </Form>
                    </div>
                   
                    </div>
            
        )
    }
}


export default NewClinicForm