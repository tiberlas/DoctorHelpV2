import React, { Component } from 'react';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import {Link} from 'react-router-dom';
import { UserContext } from './../context/UserContextProvider';

class Header extends Component {
    static contextType = UserContext

    render() { 
        return ( 
            <Navbar bg="light" expand="lg" id="navbarColor03">
                <Navbar.Brand >{this.context.user.firstName}&nbsp;{this.context.user.lastName}</Navbar.Brand>
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    {this.context.user.role === 'CLINICAL_ADMINISTRATOR' &&
                    <Nav className="mr-auto">
                        <Nav.Link class="nav-item">
                            <Link exact to = '/clinic+administrator/profile' class="nav-link">profile</Link>
                        </Nav.Link>
                        <Nav.Link class="nav-item">
                            <Link exact to = '/clinic+administrator/clinic' class="nav-link">clinic's profile</Link>
                        </Nav.Link>
                        <Nav.Link class="nav-item">
                            <Link exact to = '/clinic+administrator/clinic/change' class="nav-link">change clinic's profile</Link>
                        </Nav.Link>
                        <Nav.Link class="nav-item">
                            <Link exact to = '/clinic+administrator/rooms' class="nav-link">rooms</Link>
                        </Nav.Link>
                        <Nav.Link class="nav-item"> 
                            <Link exact to='/clinic+administrator/medical+staff' class="nav-link">medical staff</Link>
                        </Nav.Link>
                    </Nav> }
                    {this.context.user.role === 'DOCTOR' &&
                    <Nav className="mr-auto">
                        <Nav.Link class="nav-item">
                            <Link exact to = '/doctor/profile' class="nav-link">profile</Link>
                        </Nav.Link>
                    </Nav>}
                </Navbar.Collapse>
            </Navbar>
         );
    }
}
 
export default Header;
