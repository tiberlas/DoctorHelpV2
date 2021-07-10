import React, {Component} from 'react';
import axios from 'axios';
import StarRatingComponent from 'react-star-rating-component';
import { Button } from '@material-ui/core';
import { UserContext } from '../../context/UserContextProvider'


class DoctorProfilePreview extends Component {

	static contextType = UserContext;

	state = {
		firstName : "", 
		lastName : "", 
		clinic : "", 
		specialization : "", 
		rating : "", 
		id : "", 
		myRating : 0, 
		haveInteracted : false
	}

	componentDidMount () {
		let parts = window.location.href.split ('/');
		let tempId = parts[parts.length - 1];
		axios.get ("/api/doctors/preview/" + tempId + "/" + this.context.user.id)
		.then (response => {
			this.setState ({
				firstName : response.data.firstName, 
				lastName : response.data.lastName, 
				clinic : response.data.clinic, 
				specialization : response.data.specialization, 
				rating : response.data.rating, 
				id : tempId, 
				myRating : response.data.myRating,
				haveInteracted : response.data.haveInteracted
			})
		});
	}

	handleUpdate () {
		let parts = window.location.href.split ('/');
		let tempId = parts[parts.length - 1];
		axios.get ("/api/doctors/preview/" + tempId + "/" + this.context.user.id)
		.then (response => {
			this.setState ({
				firstName : response.data.firstName, 
				lastName : response.data.lastName, 
				clinic : response.data.clinic, 
				specialization : response.data.specialization, 
				rating : response.data.rating, 
				id : tempId, 
				myRating : response.data.myRating,
				haveInteracted : response.data.haveInteracted
			})
		});
	}

	handleClick (nextValue) {
		// alert ("Star click: " + nextValue)
		this.setState ({
			myRating : nextValue
		})
		axios.post ("/api/doctors/review/" + this.context.user.id + "/" + this.state.id + "/" + nextValue)
		.then (data => {
			this.handleUpdate();
		});
	}

	handleClear () {
		this.setState ({
			myRating : 0
		})
		axios.post ("/api/doctors/review/" + this.context.user.id + "/" + this.state.id + "/" + 0)
		.then (data => {
			this.handleUpdate();
		});
	}

	render () {

		const {myRating} = this.state.myRating;

		return (
			<div class="row d-flex justify-content-center">
				<div class='col-md-7'>
					<br/>
					<br/>
					<h1>{this.state.firstName}&nbsp;{this.state.lastName}</h1>
					<div >
                    		<label class="badge badge-success text-right">Clinic:</label>&nbsp;&nbsp;&nbsp;
                    		<label >{this.state.clinic}</label>
                	</div>
					<div >
                    		<label class="badge badge-success text-right">Specialization:</label>&nbsp;&nbsp;&nbsp;
                    		<label >{this.state.specialization}</label>
                	</div>
					<div >
                    		<label class="badge badge-success text-right">Rating:</label>&nbsp;&nbsp;&nbsp;
                    		<label >{this.state.rating}</label>
                	</div>
					<div hidden={!this.state.haveInteracted}>
						<StarRatingComponent name="imja" starCount={5} value={this.state.myRating} onStarClick={this.handleClick.bind(this)}/>
					</div>
					<div hidden={!this.state.haveInteracted}>
						<Button class="badge badge-success text-right" onClick = {() => this.handleClear()}>
							Clear rating
						</Button>
					</div>
				</div>
			</div>
		)

	}


}


export default DoctorProfilePreview;