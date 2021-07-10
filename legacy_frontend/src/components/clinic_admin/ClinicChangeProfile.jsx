import React, { Component } from 'react';
import { Redirect } from 'react-router-dom';
import axios from 'axios';

class ClinicChangeProfile extends Component {
    state = { 
        name: "",
        address: "",
        state: "",
        city: "",
        description: "",
        errorName: false,
        errorDescription: false,
        errorAddress: false,
        errorCity: false,
        errorState: false,
        gotoProfile: false,
        errorBack: false,
    }

    componentDidMount() {
        this.handelUpdate()
    }

    handelUpdate = () => {
       axios.get('/api/clinics/id='+this.props.clinicId)
       .then(response => {
           this.setState({
               name: response.data.name,
               address: response.data.address,
               city: response.data.city,
               state: response.data.state,
               description: response.data.description
           })
       })
    }

    //stavlja + umesto space; bitno je da adresa nema space u sebi
    stringParser = (path) => {
        var words = path.split(' ')
        var ret = words[0]
        var i
        for(i=1; i<words.length; i++) {
            ret += '+'+words[i]
        }

        return ret
    }

    handleSubmit = (event) => {
        event.preventDefault();
        this.setState({gotoProfile: false, errorBack: false})

        axios.put('/api/clinics/change', {
                    id: this.props.clinicId,
                    name: this.state.name,
                    description: this.state.description,
                    address: this.state.address,
                    city: this.state.city,
                    state: this.state.state
        }).then( (response) => {
             //provera da li je adresa validna
        fetch('https://api.opencagedata.com/geocode/v1/json?q='+this.stringParser(this.state.address)+'%2c+'+this.stringParser(this.state.city)+'%2c+'+this.stringParser(this.state.state)+'&key=c94e6fbd30c540dba84374d9fc772e18&pretty=1')
            .then(response => response.json())
            .then(data => {
                console.log('provera validnosti', data)
                if(data.status !== 200 && data.status.code == undefined) {
                    alert('THE ADDRESS IS NOT VALID./nPLEASE TRY AGAIN')
                    this.setState({errorBack: true, errorAddress: true})
                } else {
                    this.setState({gotoProfile: true})
                }
            })
        }).catch((error) => {
            alert('SOMETHING WENT WRONG./nPLEASE TRY AGAIN')
            this.setState({errorBack: true})
        });
    }

    handlerChange = (event) => {
        this.setState({errorName: false, errorAddress: false, errorDescription: false, errorCity: false, errorState: false})

        let nam = event.target.name;
        let val = event.target.value;
        let isValid = this.validateInput(val)

        this.setState({[nam]: val});
        if(isValid === false) {
            if(nam === 'name') {
                this.setState({errorName: true})
            } else if(nam === 'address') {
                this.setState({errorAddress: true})
            } else if(nam === 'city') {
                this.setState({errorCity: true})
            } else if(nam === 'state') {
                this.setState({errorState: true})
            } else {
                this.setState({errorDescription: true})
            }
        }
    }

    validateInput(input) {
        if(isNaN(input) == true && input !== '') {
            return true;
        } 

        return false;
    }

    render() {
        if(this.state.gotoProfile === true) {
            return (<Redirect to='/clinic-administrator/clinic'></Redirect>);
        } 
        return ( 
            <div class='row d-flex justify-content-center'>
            <div class='col-md-5'> 
            <form onSubmit={this.handleSubmit}>
                <div className={`form-group ${this.state.errorName? 'has-danger': ''}`}>
                    <label class="form-control-label" for="name">Enter clinic's name:</label>
                    <input type='text'name='name' id='name' className={`form-control ${this.state.errorName? 'is-invalid': ''}`} value={this.state.name} onChange={this.handlerChange} />
                    <div class="invalid-feedback">input must not be blank</div>
                </div>
                <div className={`form-group ${this.state.errorAddress? 'has-danger': ''}`}>
                    <label class="form-control-label" for="address">Enter clinic's address:</label>
                    <input type='text'name='address' id='address' className={`form-control ${this.state.errorAddress? 'is-invalid': ''}`} value={this.state.address} onChange={this.handlerChange} />
                    <div class="invalid-feedback">input must not be blank</div>
                </div>
                <div className={`form-group ${this.state.errorCity? 'has-danger': ''}`}>
                    <label class="form-control-label" for="city">Enter clinic's city:</label>
                    <input type='text'name='city' id='city' className={`form-control ${this.state.errorCity? 'is-invalid': ''}`} value={this.state.city} onChange={this.handlerChange} />
                    <div class="invalid-feedback">input must not be blank</div>
                </div>
                <div className={`form-group ${this.state.errorState? 'has-danger': ''}`}>
                    <label class="form-control-label" for="state">Enter clinic's state:</label>
                    <input type='text'name='state' id='state' className={`form-control ${this.state.errorState? 'is-invalid': ''}`} value={this.state.state} onChange={this.handlerChange} />
                    <div class="invalid-feedback">input must not be blank</div>
                </div>
                <div className={`form-group ${this.state.errorDescription? 'has-danger': ''}`}>
                    <label class="form-control-label" for="description">Enter clinic's description:</label>
                    <textarea name='description' id='description' className={`form-control ${this.state.errorDescription? 'is-invalid': ''}`} value={this.state.description} onChange={this.handlerChange} />
                    <div class="invalid-feedback">input must not be blank</div>
                </div>
                <div>
                    <input type='submit' value='submit' class='btn btn-success' disabled={this.state.errorName || this.state.errorAddress || this.state.errorDescription || this.state.errorCity || this.state.errorState}/>
                </div>
            </form>
            </div>
            </div>
         );
    }
}
 
export default ClinicChangeProfile;