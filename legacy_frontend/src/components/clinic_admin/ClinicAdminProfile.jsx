import React, { Component } from 'react';
import {NavLink} from 'react-router-dom'
import {ClinicAdminContext} from '../../context/ClinicAdminContextProvider';
import ViewProfile from '../ViewProfile';


class ClinicAdminProfile extends Component {

    static contextType = ClinicAdminContext;

    render() { 
        return ( 
            <div>
              
                <div class="row d-flex justify-content-center">
					<div class='col-md-7'>
                    <ViewProfile profile={this.context.admin}/>
                        <div>
                            <NavLink to = '/clinic-administrator/profile/change'>
                                change profile
                            </NavLink>
                        </div>
                        <div>
                            <NavLink to = '/clinic-administrator/profile/change/password'>
                                change password
                            </NavLink>
                        </div>
                    </div>
                </div>
            </div>


         );
    }
}
 
export default ClinicAdminProfile;