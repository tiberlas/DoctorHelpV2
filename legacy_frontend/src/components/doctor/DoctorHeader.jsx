import React, { Component } from 'react';
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import {Link} from 'react-router-dom'
import {DoctorContext} from '../../context/DoctorContextProvider';
import axios from 'axios';

class DoctorHeader extends Component {
    static contextType = DoctorContext;

    render() { 
        return ( 
            <Navbar bg="dark" expand="sm">
           <Navbar.Brand >
              <Nav> 
                  <Nav.Link>
                    <Link exact to = '/home' class="nav-link">
                      <strong> drHelp++ </strong>
                    </Link>
                  </Nav.Link>
                </Nav>
            </Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="mr-auto">

                  <Nav.Link>
                          <Link exact to='/doctor/schedule' class="nav-link">Schedule</Link>
                  </Nav.Link>

                  <Nav.Link>
                          <Link exact to='/doctor/patients' class="nav-link">Patient&nbsp;list</Link>
                  </Nav.Link>

                  {this.props.operation &&

                    <Nav.Link>
                      <Link exact to='/doctor/requested/operations' class="nav-link">Requested&nbsp;operations</Link>
                    </Nav.Link>
                  }

                  <Nav.Link>
                            <Link exact to='/doctor/leave-request' class="nav-link">Leave&nbsp;request</Link>
                    </Nav.Link>

                </Nav>
                <Nav className="justify-content-end" style={{ width: "100%" }}>

                    <Nav.Link>
                     <Link exact to = '/doctor/profile' class="nav-link">
                          {this.context.doctor.firstName}&nbsp;{this.context.doctor.lastName}
                          </Link>
                    </Nav.Link>
                    <Nav.Link>
                            <Link exact to='/login' onClick={this.props.logout} class="nav-link">Logout</Link>
                        </Nav.Link>
                    </Nav>
            </Navbar.Collapse>
            </Navbar>);
    }
}
 
export default DoctorHeader;