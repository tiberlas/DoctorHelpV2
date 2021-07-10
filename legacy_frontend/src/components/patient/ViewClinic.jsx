import React, { Component } from 'react';
import Clinic from '../clinic/Clinic.jsx'
import StarRatingComponent from 'react-star-rating-component';
import { Button } from '@material-ui/core';
import Axios from 'axios';
import { UserContext } from '../../context/UserContextProvider'

class ViewClinic extends Component {

    state = {
        rating : "/", 
        myRating : 0, 
        haveInteracted : false
    }

    static contextType = UserContext;

    componentDidMount () {
        Axios.get ("/api/clinics/review/" + this.context.user.id + "/" + window.location.href.split('/')[4])
        .then (response => {
            this.setState ({
                haveInteracted : response.data.haveInteracted, 
                myRating : response.data.myRating
            })
        })
    }

    render() { 
        let id = window.location.href.split('/')[4]
        return ( 
            <div>
                <Clinic clinicId={id}/> 
            </div>
         );
    }
}
 
export default ViewClinic;