import React, { Component } from 'react';
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import {Link} from 'react-router-dom'
import {NurseContext} from '../../context/NurseContextProvider';
 
class NurseHeader extends Component {
    static contextType = NurseContext;

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
                            <Link exact to='/nurse/schedule' class="nav-link">Schedule</Link>
                    </Nav.Link>

                    <Nav.Link>
                            <Link exact to='/nurse/patient-list' class="nav-link">Patient&nbsp;list</Link>
                    </Nav.Link>

                    <Nav.Link>
                            <Link exact to='/nurse/perscription-list' class="nav-link">Pending&nbsp;perscriptions</Link>
                    </Nav.Link>

                  

                    <Nav.Link>
                            <Link exact to='/nurse/leave-request' class="nav-link">Leave&nbsp;request</Link>
                    </Nav.Link>
                </Nav>




                <Nav className="justify-content-end" style={{ width: "100%" }}>
                    <Nav.Link>
                    <Link exact to = '/nurse/profile' class="nav-link">
                          {this.context.nurse.firstName}&nbsp;{this.context.nurse.lastName}
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
 
export default NurseHeader;