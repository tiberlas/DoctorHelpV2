import React, {Component} from 'react';
import './App.css';
import TempHome from './components/TempHome.js'
import LoginPage from './LoginPage.js'

import {BrowserRouter, Redirect, Route} from "react-router-dom";
import {Switch} from 'react-router-dom';

import 'bootstrap/dist/css/bootstrap.css';
import 'bootswatch/dist/darkly/bootstrap.css';
//import 'font-awesome/css/font-awesome.min.css'; //za ikonice
import UserContextProvider from './context/UserContextProvider';
import interceptor from './Interseptor.js';
import FirstTimePasswordChange from './components/FirstTimePasswordChange'
import ActivatePatient from './components/activate/ActivatePatient'



class App extends Component {
  
  constructor(props) {
    super(props)
    this.state = {
      loggedIn: false,
      userRole: 'guest', 
      userId: 1,
      currentUrl: window.location.href.split('=')[0],
      passwordChange: false
    }
  }


  componentDidMount() {
    const script = document.createElement("script"); //neophodno za font-awesome ikonice

    script.src = "https://kit.fontawesome.com/2060984414.js";
    script.crossOrigin = "anonymous"

    document.body.appendChild(script);
  }

  setDoctor () {
    this.setState ({
      loggedIn: true,
      userRole: 'doctor',
    })
  }

  setNurse () {
    this.setState ({
      loggedIn: true,
      userRole: 'nurse',
    })
  }

  setCentreAdmin () {
    this.setState ({
      loggedIn: true,
      userRole: 'centreAdmin',
    })
  }

  setClinicAdmin () {
    this.setState ({
      loggedIn: true,
      userRole: 'clinicAdmin',
    })
  }

  setPatient () {
    this.setState ({
      loggedIn: true,
      userRole: 'patient',
    })
  }

  logout () {
    this.setState ({
      loggedIn: false,
      userRole: 'guest',
    })
    localStorage.setItem('token', null);
  }

  setPasswordChange(role) {
    this.setState({
      passwordChange: true,
      userRole: role
    })
  }

  render() {


    if(this.state.currentUrl === 'http://localhost:3000/activate') { //if you're activating the account, dont render the login page
        let urlEmail = window.location.href.split('=')[1] //email from the url
        return(<ActivatePatient email={urlEmail}/>)
    }

      return (
        <div>
          {/* <script src="https://kit.fontawesome.com/2060984414.js" crossorigin="anonymous"></script> */}
          {/* <link 
    rel="stylesheet"
    href="https://use.fontawesome.com/releases/v5.3.1/css/all.css"
    integrity="sha384-mzrmE5qonljUremFsqc01SB46JvROS7bZs3IO2EmfFsd15uHvIt+Y8vEf7N7fWAU" crossorigin="anonymous"/> */}
          <BrowserRouter >
            <Switch>
                { this.state.passwordChange?
                 <div> <FirstTimePasswordChange role = {this.state.userRole}/>  </div>:

                <UserContextProvider id={this.state.userId} role = {this.state.userRole}>
                 {!this.state.loggedIn && 
                    <LoginPage 
                    loggedIn={this.state.loggedIn}
                    userRole={this.state.userRole}
                    setLoginDoctor={() => this.setDoctor ()}
                    setLoginNurse={() => this.setNurse ()}
                    setLoginCentreAdmin={() => this.setCentreAdmin ()}
                    setLoginClinicAdmin={() => this.setClinicAdmin ()}
                    setLoginPatient={() => this.setPatient ()}
                    setPasswordChange = {(role) => this.setPasswordChange(role)}
                    />
                    // <PatientProfile></PatientProfile>
                  }
                  {(this.state.loggedIn && !this.state.passwordChange) &&
                  < TempHome role = {this.state.userRole} logout={() => this.logout ()}/>	}
                  <Route render={() => <Redirect to={{pathname: "/login"}} />} />

                  </UserContextProvider>}		
            </Switch>
          </BrowserRouter>
        </div>

      );
    

}
}

export default App;
