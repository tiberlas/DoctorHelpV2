import React from 'react';
import FormControl from 'react-bootstrap/FormControl';
import { Link, Redirect } from 'react-router-dom';
import { Modal, ModalBody, ModalFooter } from 'react-bootstrap';

const baseBackUrl = "http://localhost:8080";
class RegistrationPage extends React.Component {

	constructor () {
    	super()
    	this.state = { 
			iHaveWaited: false, 
			same_password: true, 
			name_length: true, 
			last_name_length: true, 
			insurance_valid: true, 
			registration_valid: false, 
			original_mail: false,
			unique_insurence: false,
			addres_length: true, 
			town_length: true, 
			country_lenght: true, 
			phone_length: true, 
			valid_date: true, 
			toLogin : false
		}
  	}


	handleRegister = async (event) => {
		this.setState ({
			same_password: true, 
			name_length: true, 
			last_name_length: true, 
			insurance_valid: true, 
			registration_valid: false,
			original_mail: false,
			unique_insurence: false,
			addres_length: true, 
			town_length: true, 
			country_lenght: true, 
			phone_length: true, 
			valid_date: true, 
		})
		event.preventDefault();

		let pass1 = document.getElementById ('tb_pass1').value;
		let pass2 = document.getElementById ('tb_pass2').value;
		if (pass1 !== pass2) {
			this.setState ({
				same_password: false
			})
			return;
		}

							
		let email = document.getElementById ('tb_email').value;
		let pass = document.getElementById ('tb_pass1').value;
		let name = document.getElementById ('tb_name').value;
		let last_name = document.getElementById ('tb_last_name').value;
		let address = document.getElementById ('tb_address').value;
		let town = document.getElementById ('tb_town').value;
		let country = document.getElementById ('tb_country').value;
		let phone = document.getElementById ('tb_phone').value;
		let insurance = document.getElementById ('tb_insurance').value;
		let birthday = document.getElementById('tb_birthday').value;
		let temp_error = false;

		if (insurance.includes("-") || insurance.includes("+") || (insurance < 1) || (insurance > 999999999999999999)) {
			this.setState ({
				insurance_valid: false
			})
			return;
		}
		if ((name.length < 2) || (name.length > 30)) {
			this.setState ({
				name_length: false
			})
			return;
		}
		if ((last_name.length < 2) || (last_name.length > 30)) {
			this.setState ({
				last_name_length: false
			})
			return;
		}
		if ((address.length < 2) || (address.length > 30)) {
			this.setState ({
				addres_length: false
			})
			return;
		}
		if ((town.length < 2) || (town.length > 30)) {
			this.setState ({
				town_length: false
			})
			return;
		}
		if ((country.length < 2) || (country.length > 30)) {
			this.setState ({
				country_lenght: false
			})
			return;
		}
		if ((phone.length < 5) || (phone.length > 13)) {
			this.setState ({
				phone_length: false
			})
			return;
		}

		let parts = birthday.split('-');
		let year = parts[0];
		let mon = parts[1];
		let day = parts[2];
		var now = new Date();
		if ((year < 1900) || (year > now.getFullYear())) {
			this.setState ({
				valid_date: false
			})
			return;
		}

		fetch (baseBackUrl+'/api/register', {
			method: 'post', 
			headers: {'Content-Type' : 'application/json'}, 
			body: JSON.stringify ({
				email: email, 
				password: pass, 
				firstName: name, 
				lastName: last_name, 
				address: address, 
				city: town, 
				state: country, 
				phoneNumber: phone, 
				insuranceNumber: insurance, 
				birthday: birthday
			})
		})
		//.then (data => data.json ()) 
		.then (data => {
			if (data.status !== 200) {
				if (data.status === 409) {
					//alert ("Do mejla je")
					this.setState ({
						registration_valid: false,
						original_mail: true, 
						unique_insurence: false
				    })
				}
				else {
					//alert ("Do osiguranja je")
					this.setState ({
						registration_valid: false,
						original_mail: false, 
						unique_insurence: true
				    })
				}
				
			} else {
				this.setState ({
					registration_valid: true,
					original_mail: false, 
					unique_insurence: false 
				})	
				
				setTimeout (() => {
					this.setState ({
						toLogin : true
					})
				}, 2000);
			}
		});	
		
	}



	render () {
		return (



			<div class='row d-flex justify-content-center'>
				<div class='col-md-3'> 

					<Modal show={this.state.toLogin}>

					</Modal>



					{
						this.state.toLogin &&
						<Redirect exact to="/login"></Redirect>
					}
					
					<form onSubmit={this.handleRegister}>
						<br/>
						<br/>
						<br/>
						<h2 class='text-success'>Register</h2>
						<br/>
						<div>
							<FormControl required type="email" placeholder="Email" id="tb_email" className={`form-control ${this.state.original_mail? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">An account with that email address already exists. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="password" placeholder="Password" id="tb_pass1"/>
						</div>
						<br/>
						<div>
							<FormControl required type="password" placeholder="Repeat password" id="tb_pass2" className={`form-control ${!this.state.same_password? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Both instances of the password must be same. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="text" placeholder="First name" id="tb_name" className={`form-control ${!this.state.name_length? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Name length must be between 2 and 30 characters. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="text" placeholder="Last name" id="tb_last_name" className={`form-control ${!this.state.last_name_length? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Last name length must be between 2 and 30 characters. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="text" placeholder="Address" id="tb_address" className={`form-control ${!this.state.addres_length? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Address length must be between 2 and 30 characters. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="text" placeholder="Town" id="tb_town" className={`form-control ${!this.state.town_length? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Town name length must be between 2 and 30 characters. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="text" placeholder="Country" id="tb_country" className={`form-control ${!this.state.country_lenght? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Country name length must be between 2 and 30 characters. </div>
						</div>
						<br/>
						<div>
						    <FormControl required type="number" placeholder="Phone number" id="tb_phone" className={`form-control ${!this.state.phone_length? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">Phone number length must be between 5 and 13 digits. </div>
						</div>
						<br/>
						<div>
							<FormControl required type="number" placeholder="Insurance number" id="tb_insurance" className={`form-control ${(!this.state.insurance_valid || this.state.unique_insurence)? 'is-invalid': ''}`}/>
							<div class="invalid-feedback">{(this.state.unique_insurence) ? ("An account with that insurence number already exists.") : ("Insurance number must be positive with no more than 18 digits.")}</div>
						</div>
						<br/>
						<div>
							<FormControl required type="date" placeholder="Date of birth, in format: dd/mm/yyyy" id="tb_birthday" className={`form-control ${this.state.registration_valid? 'is-valid': ''}`}/>
							<div class="valid-feedback">Registration request successfully sent. </div>
							<div class="invalid-feedback">Date must be betweend today and 1.1.1990. </div>
							<br></br>
						</div>
						<div class="form-group row" >
							<div class='col-md text-left'>
								<input type="submit" value="Submit" class='btn btn-success'></input>
							</div>
							<div class='col-md text-right'>
								<Link to="/login">
									<a href>Login</a>
								</Link>
							</div>
						</div>
					</form>
				</div>
			</div>
		)
	}

} export default RegistrationPage


