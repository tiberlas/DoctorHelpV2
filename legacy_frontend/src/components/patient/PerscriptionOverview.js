import React, {Component} from 'react';
import { TableHead, TableRow, TableBody } from '@material-ui/core';
import Table from '@material-ui/core/Table';
import TableCell from '@material-ui/core/TableCell';
import axios from 'axios';


class PerscriptionOverview extends Component {

	state = {
		diagnosis: "No diagnosis",
		description: "No description given", 
		advice: "Nothing in particular. ", 
		medicationList: []
	}



	componentDidMount () {
		let url = window.location.href.split ('perscription/')[1];
		
		axios.get("/api/patients/examinationReportId=" + url + "/perscription")
		.then (response => {
			this.setState ({
				diagnosis: response.data.diagnosis, 
				description: response.data.description,
				medicationList: response.data.medicationList
			});
			if (response.data.advice !== "") {
				this.setState ({
					advice: response.data.advice
				});
			}
		})
	}

	render () {
		return (
			<div>
				<div class='row d-flex justify-content-center'>
					<div class='col-md-11'>
						<Table>
							<TableRow>
								<TableCell>
									<h3>{this.state.diagnosis}</h3>
									<h5>Perscription description:</h5>
									<p>{this.state.description}</p>
									<h5>Advice:</h5>
									<p>{this.state.advice}</p>
								</TableCell>
								<TableCell>
									<Table>
										<TableHead>
											<TableRow>
												<TableCell align='center'><p class='text-success'>Medication Name</p></TableCell>
												<TableCell align='center'><p class='text-success'>Medication Description</p></TableCell>
											</TableRow>
										</TableHead>
										<TableBody>
											{this.state.medicationList.map (row => (
												<TableRow key={row.medicationName}>
													<TableCell align='center'><p class='text-white'>{row.medicationName}</p></TableCell>
													<TableCell align='center'><p class='text-white'>{row.medicationDescription}</p></TableCell>
												</TableRow>
											))}
										</TableBody>
									</Table>
								</TableCell>
							</TableRow>
						</Table>
					</div>
				</div>
			</div>
		)
	}
 
}


export default PerscriptionOverview;