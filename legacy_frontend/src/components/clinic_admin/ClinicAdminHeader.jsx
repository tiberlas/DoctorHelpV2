import React, { Component } from 'react'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import {Link} from 'react-router-dom'
import {ClinicAdminContext} from '../../context/ClinicAdminContextProvider';
import {DropdownItem} from  'react-bootstrap'
import {DropdownToggle, DropdownMenu, NavbarToggler, Collapse, UncontrolledDropdown, NavItem} from 'reactstrap'
import { LinkContainer } from 'react-router-bootstrap';
import cross from '../../images/reed_cross.gif';

class ClinicAdminHeader extends Component {
    static contextType = ClinicAdminContext
 
    state = {
        dropdownAdd: false
    }

    toogle = () => {
        this.setState({dropdownAdd:!this.state.dropdownAdd});
    }

    render() { 
        return (
            <Navbar bg="dark" expand="sm">
            <Navbar.Brand >
              <Nav> 
                  <Nav.Link>
                    <Link exact to = '/home' class="nav-link">
                      <strong class="text-success" style={{fontFamily: "serif", fontSize: "40px", textAlign: "center"}}> drHelp </strong>
                      <img src={cross} height="45px" width="45px" style={{alignContent: "up"}}/>
                      <img src={cross} height="45px" width="45px"/>
                    </Link>
                  </Nav.Link>
                </Nav>
            </Navbar.Brand>
            <NavbarToggler aria-controls="basic-navbar-nav"/>
            <Collapse id="basic-navbar-nav" isOpen={this.state.dropdownAdd} navbar className="collapse">
            <Nav className="mr-auto">

            <NavbarToggler onClick={this.toggle}/>
            <Collapse isOpen={this.state.dropdownAdd} navbar className="collapse">
                <Nav className="mr-auto" navbar pullRight>
                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle nav caret>
                    Clinic
                    </DropdownToggle>
                    <DropdownMenu className='dropdown-menu'>
                    <LinkContainer exact to = '/clinic-administrator/clinic'>
                    <DropdownItem >Profile</DropdownItem>
                    </LinkContainer>
                    
                    <LinkContainer exact to='/clinic-administrator/clinic/change'>
                    <DropdownItem >Change</DropdownItem>
                    </LinkContainer>

                    <LinkContainer exact to='/clinic-administrator/clinic/income'>
                    <DropdownItem >Income</DropdownItem>
                    </LinkContainer>

                    <LinkContainer exact to='/clinic-administrator/heald-appointemtns'>
                    <DropdownItem >Held&nbsp;Appointments</DropdownItem>
                    </LinkContainer>

                    </DropdownMenu>
                </UncontrolledDropdown>
                </Nav>
            </Collapse>

            <Nav.Link>
                <Link exact to = '/clinic-administrator/rooms' class="nav-link">Room</Link>
            </Nav.Link>

            <Nav.Link>
                <Link exact to = '/clinic-administrator/procedure-types' class="nav-link">Procedure&nbsp;type</Link>
            </Nav.Link>

            <Nav.Link>
                <Link exact to = '/clinic-administrator/predefined-appointments' class="nav-link">Predefined&nbsp;appointments</Link>
            </Nav.Link>

            <Nav.Link>
                <Link exact to = '/clinic-administrator/medical-staff' class="nav-link">Medical&nbsp;staffs</Link>
            </Nav.Link>

            <NavbarToggler id="requests_listing" onClick={this.toggle}/>
            <Collapse isOpen={this.state.dropdownAdd} navbar className="collapse">
                <Nav className="mr-auto" navbar pullRight>
                <UncontrolledDropdown nav inNavbar>
                    <DropdownToggle nav caret>
                    Requests
                    </DropdownToggle>
                    <DropdownMenu className='dropdown-menu'>
                    <LinkContainer exact to = '/clinic-administrator/requests/appointments'>
                    <DropdownItem id="appointment_id" >Appointments</DropdownItem>
                    </LinkContainer>
                    
                    <LinkContainer exact to='/clinic-administrator/requests/operations'>
                    <DropdownItem >Operations</DropdownItem>
                    </LinkContainer>

                    <LinkContainer exact to='/clinic-administrator/requests/leaves'>
                    <DropdownItem >Leaves</DropdownItem>
                    </LinkContainer>

                    </DropdownMenu>
                </UncontrolledDropdown>
                </Nav>
            </Collapse>

            </Nav>
            <Nav className="justify-content-end" >

                <Nav.Link>
                        <Link exact to = '/clinic-administrator/profile' class="nav-link">
                          {this.context.admin.firstName}&nbsp;{this.context.admin.lastName}
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
 
export default ClinicAdminHeader;