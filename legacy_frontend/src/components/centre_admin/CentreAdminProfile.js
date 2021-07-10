import React, { Component } from 'react';
import {NavLink} from 'react-router-dom'
import ViewProfile from '../ViewProfile';
import {CentreAdminContext} from '../../context/CentreAdminContextProvider';

class CentreAdminProfile extends Component {

    static contextType = CentreAdminContext

    render() { 
        return ( 
        <div>
           
                <div class="row d-flex justify-content-center">
					<div class='col-md-7'>
                    <ViewProfile profile={this.context.admin}/>
                        <div>
                            <NavLink to = '/centreAdministrator/profile/change'>
                                change profile
                            </NavLink>
                        </div>
                        <div>
                            <NavLink to = '/centreAdministrator/profile/change/password'>
                                change password
                            </NavLink>
                        </div>
                    </div>
                </div>
            </div>


         );
    }
}
 
export default CentreAdminProfile;