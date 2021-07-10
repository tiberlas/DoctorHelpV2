import React, { Component } from 'react';
import {NavLink} from 'react-router-dom';
import {NurseContext} from '../../context/NurseContextProvider';
import ViewProfile from '../ViewProfile';

class NurseProfile extends Component {
    static contextType = NurseContext;

    render() { 
        return ( 
            <div>
               
                <div class="row d-flex justify-content-center">
					<div class='col-md-7'>
                    <ViewProfile profile={this.context.nurse}/>
                        <div>
                            <NavLink to = '/nurse/profile/change'>
                                change profile
                            </NavLink>
                        </div>
                        <div>
                            <NavLink to = '/nurse/profile/change/password'>
                                change password
                            </NavLink>
                        </div>
                    </div>
                </div>
            </div>
         );
    }
}
 
export default NurseProfile;