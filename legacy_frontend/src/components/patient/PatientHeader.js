import React, { Component } from 'react';
import Nav from 'react-bootstrap/Nav';
import { PatientContext } from '../../context/PatientContextProvider';
import { Navbar } from 'react-bootstrap';
import {Link} from 'react-router-dom';
import {DropdownItem} from  'react-bootstrap'
import {DropdownToggle, DropdownMenu, NavbarToggler, Collapse, UncontrolledDropdown, NavItem} from 'reactstrap'
import { LinkContainer } from 'react-router-bootstrap';
import NavDropdown from 'react-bootstrap/NavDropdown';


class PatientHeader extends Component {
    state = { 
        showAppointmentsDropdown : false, 
        isOpenManage: false
     }
 
    static contextType = PatientContext;

    collapse (arg) {
        alert ("Collapsing")
    }

    handleMouseEnter () {
        // alert ("Entering")
        this.setState ({
            showAppointmentsDropdown : false
        })
    }

    handleOpenManage = () => {
        this.setState({ isOpenManage: true })
      }
    
      handleCloseManage = () => {
         this.setState({ isOpenManage: false })
      }


    render() { 
        return ( 
            <Navbar bg="dark" expand="sm" id="navbarColor01">
                <Navbar.Brand >
                    <Nav> 
                        <Nav.Link>
                            <Link exact to = '/home' class="nav-link">
                            <strong> drHelp++ </strong>
                            </Link>
                        </Nav.Link>
                    </Nav>
                </Navbar.Brand>
            <NavbarToggler aria-controls="basic-navbar-nav"/>
            <Collapse id="basic-navbar-nav" isOpen={this.state.dropdownAdd} navbar className="collapse">
                <Nav className="mr-auto">
                    <Nav.Link>
                        <Link exact to='/patient/clinicList' class="nav-link"><span id="clinics_nav"> Clinics </span></Link>
                    </Nav.Link>

                    <Collapse isOpen={true} navbar className="collapse">
                        <Nav>

                            <div id="appointments_nav"> 
                            <NavDropdown
                                onMouseEnter = { this.handleOpenManage }
                                onMouseLeave = { this.handleCloseManage }
                                show={ this.state.isOpenManage }
                                noCaret
                                id="language-switcher-container"
                                title="Appointments"
                                
                            >
                                <LinkContainer exact to="/patient/appointmentList">
                                    <DropdownItem >Pending apppointments</DropdownItem>
                                </LinkContainer>
                                <LinkContainer exact to="/patient/history">
                                    <DropdownItem> History</DropdownItem>
                                </LinkContainer>
                                <LinkContainer exact to="/patient/predefined">
                                    <DropdownItem id="book_predefined_appointment">Book predefined appointment</DropdownItem>
                                </LinkContainer>                
                            </NavDropdown>
                            </div>
                        </Nav>
                    </Collapse>
                    <Nav.Link>
                        <Link exact to='/patient/health-record' class="nav-link">Health record</Link>
                    </Nav.Link>
                </Nav>
                <Nav className="justify-content-end" >
                    <Nav.Link>
                        <Link exact to = '/patient/profile' class="nav-link">
                            {this.context.patient.firstName}&nbsp;{this.context.patient.lastName}
                        </Link>
                    </Nav.Link>
                    <Nav.Link>
                        <Link exact to='/login' onClick={this.props.logout} class="nav-link">Logout</Link>
                    </Nav.Link>
                </Nav>
            </Collapse>
        </Navbar>
            
        );
    }
}
 
export default PatientHeader;