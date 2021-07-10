import React, { Component } from 'react';
import {NavLink} from 'react-router-dom';
import {DoctorContext} from '../../context/DoctorContextProvider';
import ViewProfile from '../ViewProfile';

class DoctroProfile extends Component {
    static contextType = DoctorContext;

    render() { 
        return ( 
            <div>
                
                <div class="row d-flex justify-content-center">
					<div class='col-md-7'>
                    <ViewProfile profile={this.context.doctor}/>
                        <div>
                            <NavLink to = '/doctor/profile/change'>
                                change profile
                            </NavLink>
                        </div>
                        <div>
                            <NavLink to = '/doctor/profile/change/password'>
                                change password
                            </NavLink>
                        </div>
                    </div>
                </div>
            </div>
         );
    }
}
 
export default DoctroProfile;