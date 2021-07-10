import React, { Component } from 'react';

class ChangeProfile extends Component {
    state = { 
        firstName: this.props.user.firstName,
        lastName: this.props.user.lastName,
        state: this.props.user.state,
        city: this.props.user.city,
        address: this.props.user.address,
        phoneNumber: this.props.user.phoneNumber,
        errorName: false,
        errorLast: false,
        errorState: false,
        errorCity: false,
        errorAddress: false,
        errorPhone: false,
        error: false
    }

    handlerChange = (event) => {
        let nam = event.target.name
        let val = event.target.value
        this.setState({[nam]: val}, () => { this.handleValidation()})
    }

    handleValidation = () => {
        this.setState({error: false,errorName: false, errorLast: false, errorState: false, errorCity: false, errorAddress: false, errorPhone: false})

        if(!this.state.firstName.trim() || this.state.firstName.length < 3) {
            this.setState({errorName: true, error: true})
        }

        if(!this.state.lastName.trim() || this.state.lastName.length < 3) {
            this.setState({errorLast: true, error: true})
        }

        if(!this.state.state.trim() || this.state.state.length < 3) {
            this.setState({errorState: true, error: true})
        }

        if(!this.state.city.trim() || this.state.city.length < 3) {
            this.setState({errorCity: true, error: true})
        }

        if(!this.state.address.trim() || this.state.address.length < 3) {
            this.setState({errorAddress: true, error: true})
        }

        if(!this.state.phoneNumber.trim() || this.state.phoneNumber.length < 3) {
            this.setState({errorPhone: true, error: true})
        }
    }


    handleSubmit = (event) => {
        event.preventDefault();
        if(this.state.error === true) {
            return;
        }
        var user = {
            firstName: this.state.firstName,
            lastName: this.state.lastName,
            state: this.state.state,
            city: this.state.city,
            address: this.state.address,
            phoneNumber: this.state.phoneNumber
        }
        this.props.handleSubmit(user);
    }

    render() { 
        return (
            <div class='row d-flex justify-content-center'>
            <div class='col-md-3'> 
            <form onSubmit={this.handleSubmit}>
                <div className={`form-group ${this.state.errorName? 'has-danger': ''}`}>
                <label class="form-control-label" for="firstName">Enter your first name:</label>
                    <input type='text'name='firstName' id='firstName' className={`form-control ${this.state.errorName? 'is-invalid': ''}`} value={this.state.firstName} onChange={this.handlerChange} />
                </div>
                <div className={`form-group ${this.state.errorLast? 'has-danger': ''}`}>
                    <label class="form-control-label" for="lastName">Enter your last name:</label>
                    <input type='text'name='lastName' id='lastName' className={`form-control ${this.state.errorLast? 'is-invalid': ''}`} value={this.state.lastName} onChange={this.handlerChange} />
                </div>
                <div className={`form-group ${this.state.errorState? 'has-danger': ''}`}>
                    <label class="form-control-label" for="state">Enter your state:</label>
                    <input type='text'name='state' id='state' className={`form-control ${this.state.errorState? 'is-invalid': ''}`} value={this.state.state} onChange={this.handlerChange} />
                </div>
                <div className={`form-group ${this.state.errorCity? 'has-danger': ''}`}>
                    <label class="form-control-label" for="city">Enter your city:</label>
                    <input type='text'name='city' id='city' className={`form-control ${this.state.errorCity? 'is-invalid': ''}`} value={this.state.city} onChange={this.handlerChange} />
                </div>
                <div className={`form-group ${this.state.errorAddress? 'has-danger': ''}`}>
                    <label class="form-control-label" for="address">Enter your address:</label>
                    <input type='text'name='address' id='address' className={`form-control ${this.state.errorAddress? 'is-invalid': ''}`} value={this.state.address} onChange={this.handlerChange} />
                </div>
                <div className={`form-group ${this.state.errorPhone? 'has-danger': ''}`}>
                    <label class="form-control-label" for="phoneNumber">Enter your phone number:</label>
                    <input type='number' name='phoneNumber' id='phoneNumber' className={`form-control ${this.state.errorPhone? 'is-invalid': ''}`} value={this.state.phoneNumber} onChange={this.handlerChange} />
                </div>
                <div class='row'>
                    <div class='col'>
                        <input type='submit' value='submit' className={`btn btn-success ${this.state.error? 'disabled': ''}`}/>
                    </div>
                    <div class='col'>
                    {this.props.errorBack && 
                        <div class="alert alert-dismissible alert-danger">
                            <strong>Oh snap!</strong>Change a few things up and try submitting again.
                        </div>
                    }
                    </div>
                </div>
            
            </form>
            </div>
            </div> 
        );
    }
}
 
export default ChangeProfile;
