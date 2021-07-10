import React, { Component, Fragment } from 'react';

class ViewProfile extends Component {

    render() { 
        return ( 
                <Fragment>
                <div >

                    <h1 >{this.props.profile.firstName}&nbsp;{this.props.profile.lastName}</h1>

                </div>
                
                <div >
                    <strong><i class="fa fa-envelope-open"></i> Email:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.email}</label>
                </div>
               
                <div>
                    <strong><i class="fas fa-map-marker-alt"></i> Address:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.address}</label>
                </div>
               
                <div >
                    <strong><i class="fas fa-city"></i> City:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.city}</label>
                </div>
                
                <div >
                    <strong><i class="fas fa-globe-africa"></i> State:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.state}</label>
                </div>
                
                <div >
                    <strong><i class="fas fa-mobile-alt"></i> Phone Number:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.phoneNumber}</label>
                </div>
                
                <div >
                    <strong><i class="fas fa-birthday-cake"></i> Birthday:</strong>&nbsp;&nbsp;&nbsp;
                    <label>{this.props.profile.birthday}</label>
                </div>
                </Fragment>
         );
    }
}
 
export default ViewProfile;