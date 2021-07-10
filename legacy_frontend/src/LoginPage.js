import React from 'react';
import Button from 'react-bootstrap/Button'
import Form from 'react-bootstrap/Form';
import FormControl from 'react-bootstrap/FormControl';
import TempHome from './components/TempHome.js';
import {UserContext} from './context/UserContextProvider';
import {Route} from 'react-router-dom';
import {Switch} from 'react-router-dom';
import RegistrationPage from './components/RegistrationPage.js';
import { Link } from 'react-router-dom';
import FirstTimePasswordChange from './components/FirstTimePasswordChange'
import ModalHeader from 'react-bootstrap/ModalHeader';
import { Modal, ModalBody, ModalFooter } from 'react-bootstrap';

const baseBackendUrl = "http://localhost:8080";
class LoginPage extends React.Component {


	state = {  }

    static contextType = UserContext
	
	constructor(props) {
		super(props)
		this.state = {
			loggedIn: false,
			userRole: 'guest', 
			error: false, 
			shortPassword : false, 
			shortUsername : false
		}

	}

	handleSubmit = (event) => {
		event.preventDefault ();
		if (this.state.userRole !== 'guest') {
			return (
				<div>
					<TempHome userRole={this.props.userRole} userId = "1"/>			
				</div>
			)
		}
		let email = document.getElementById('tb_email');
		let password = document.getElementById('tb_password');
		
		if (email.value.length >= 3) {
			if  (password.value.length >= 3) {
				fetch (baseBackendUrl+'/api/login', {
					method: 'post',
					headers: {'Content-Type' : 'application/json'},
					body: JSON.stringify ({
						email: email.value, 
						password: password.value
					})
				})
				.then(response => response.json())
				.then (response =>  {


					if (response.status === 401) {
						//alert ("An account with that email and password doesn't exist or isn't activated. ");
						this.setState ({
							error: true
						});
						return;
					}

					localStorage.setItem('token', JSON.stringify(response.jwtToken));

					var token = JSON.parse(localStorage.getItem('token'));
					console.log(`Authorization=Bearer ${token}`)

					if(response.mustChangePassword === true) {
						let role = response.userRole
						this.props.setPasswordChange(response.userRole)
					}
					
					if (response.userRole === "PATIENT") {
						this.props.setLoginPatient ();
						this.context.updateValue (response.id, response.userRole);

						//this.context.updateValue ("role", response.userRole);
					}
					else if (response.userRole === "DOCTOR") {
						this.props.setLoginDoctor ()
						this.context.updateValue (response.id, response.userRole);
						//this.context.updateValue ("role", response.userRole);
					}
					else if (response.userRole === "NURSE") {
						this.props.setLoginNurse ()
						this.context.updateValue (response.id, response.userRole);
						//this.context.updateValue ("role", response.userRole);
					}
					else if (response.userRole === "CLINICAL_ADMINISTRATOR") {
						this.props.setLoginClinicAdmin ()
						this.context.updateValue (response.id, response.userRole);
						
					
						//this.context.updateValue ("role", response.userRole);
					}
					else if (response.userRole === "CENTRE_ADMINISTRATOR") {
						this.props.setLoginCentreAdmin ()
						this.context.updateValue ( response.id, response.userRole);
						//this.context.updateValue ("role", response.userRole);
					}
				});
			}
			else {
				// alert ('Password too short');
				this.setState (() => ({
					loggedIn: false, 
				}))
				this.switchPasswordMessage();
			}
		}
		else  {
			// alert ('Username too short');
			this.setState (() => ({
					loggedIn: false
			}))
			this.switchUsernameMessage ();
		}	
	}

	handleLogIn = () => {
		this.setState ({
			loggedIn: true
		})
	}

	switchPasswordMessage () {
		if (this.state.shortPassword) {
			this.setState ({
				shortPassword : false
			})
		}
		else {
			this.setState ({
				shortPassword : true
			})
		}
	}

	switchUsernameMessage () {
		if (this.state.shortUsername) {
			this.setState ({
				shortUsername : false
			})
		}
		else {
			this.setState ({
				shortUsername : true
			})
		}
	}
	
	render () {

		return (
			<div>

				<br/>

				<Modal show={this.state.shortPassword} onHide={() => this.switchPasswordMessage()}>
					<ModalHeader closeButton>
						Warning!
					</ModalHeader>
					<ModalBody>
						Password must be longer
					</ModalBody>
					<ModalFooter>
						<Button onClick={() => this.switchPasswordMessage()}>
							Ok
						</Button>
					</ModalFooter>
				</Modal>

				<Modal show={this.state.shortUsername} onHide={() => this.switchUsernameMessage()}>
					<ModalHeader closeButton>
						Warning!
					</ModalHeader>
					<ModalBody>
						Username must be longer
					</ModalBody>
					<ModalFooter>
						<Button onClick={() => this.switchUsernameMessage()}>
							Ok
						</Button>
					</ModalFooter>
				</Modal>


				<Switch>
					<Route path = "/login">
						<div class='row d-flex justify-content-center' >

						<div class='col-sm-5'>
							<br/>
							<br/>
							<br/>
							<h2 class='text-success'>Sign in, get help</h2>
							<br/>
							<form onSubmit={this.handleSubmit}>
							<div class="form-group ">
								<label for="exampleInputEmail1">Email address</label>
								<FormControl type="email" id="tb_email"/>
							</div>
							<div class={`form-group ${this.state.error? 'has-danger': ''}`}>
								<label for="exampleInputEmail1">Password</label>
								<FormControl type="password" id='tb_password' className={`form-control ${this.state.error? 'is-invalid': ''}`}/>
								<div class="invalid-feedback">The user-password pair you entered matches no existing user. </div>
							</div>
							<div class="form-group row">
								<div class='col-md text-left'>
									<input type="submit" value="Sign in" id = "tb_login" class="btn btn-outline-success" />
								</div>
								<div class='col-md text-right'>
									<Link to="/register">
										<a href>request an account</a>
									</Link>
								</div>
							</div>
							</form>

							</div>
						</div>
					</Route>
					<Route path = "/register">
						<RegistrationPage/>
					</Route>
				</Switch>
			</div>
		)
	}

}


export default LoginPage