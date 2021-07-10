import React, { Fragment } from 'react'
import Nav from 'react-bootstrap/Nav'
import Navbar from 'react-bootstrap/Navbar'
import {Link} from 'react-router-dom'

class BasicHeader extends React.Component {

    render() {
        return(
            <Fragment>
                  <Navbar bg="light" expand="lg" id="navbarColor01">
                    <Navbar.Brand >
                    <Nav> 
                            <strong style={{cursor: "pointer"}}> drHelp++ </strong>
                            
                        </Nav>
                    </Navbar.Brand>
                    </Navbar>
            </Fragment>
        )
    }
}

export default BasicHeader