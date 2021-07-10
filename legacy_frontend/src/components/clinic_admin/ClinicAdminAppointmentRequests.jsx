import React, { Component, Fragment } from "react";
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import axios from "axios";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import RequestedAppointmentItem from "../requesting_appointment/RequestedAppointmentItem.jsx";
import { Redirect } from "react-router-dom";

class ClinicAdminAppointmentRequests extends Component {
	state = {
		appointments: [],
		name: "",
		id: 0,
		redirectState: false
	};

	static contextType = ClinicAdminContext;

	componentDidMount() {
		this.handleGetRequests();
		this.handleClinicName();
	}

	handleGetRequests = () => {
		axios
			.get("/api/appointments/requests/all")
			.then((response) => {
				this.setState({ appointments: response.data });
			});
	};

	handleClinicName = () => {
		axios
			.get(
				"/api/clinics/id=" +
					this.context.admin.clinicId,
			)
			.then((response) => {
				this.setState({
					name: response.data.name,
				});
			});
	};

	handleRedirectPage = (id) => {
		this.setState({redirectState: true, id: id})
	}

	render() {
		let i = 0;
		return (
			<Fragment>
			<div class="row d-flex justify-content-center">
				<div class="col-md-4">
			{this.state.redirectState &&
				<Redirect extact to={`/request/appointment/${this.state.id}`} />
			}
					<br />
					<h3><i class="fas fa-hospital-alt"></i> {this.state.name}</h3>
					</div>
					</div>
					
					<br />

					<div class="row d-flex justify-content-center">
				<div class="col-md-11"> 
					<Table class="table table-hover ">
						<TableHead>
							<TableRow>
								<TableCell class="text-success cursor-pointer">
								<i class="fas fa-clock"></i>  Date
								</TableCell>
								<TableCell class="text-success cursor-pointer">
								<i class="fas fa-procedures"></i> Procedure
								</TableCell>
								<TableCell class="text-success cursor-pointer">
								<i class="fas fa-user-md"></i> Doctor
								</TableCell>
								<TableCell class="text-success cursor-pointer">
								<i class="fas fa-user-nurse"></i> 	Nurse
								</TableCell>
								<TableCell class="text-success cursor-pointer">
								<i class="fas fa-user-injured"></i>  Patient
								</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{this.state.appointments.map((c) => (
								<TableRow 
								 style={{cursor: 'pointer'}}
									className={++i % 2 ? `table-dark` : ``}
									id={'table_row_'+c.id}
									onClick={(id) => this.handleRedirectPage(c.id)}
								>
									<RequestedAppointmentItem
										key={c.id}
										id={c.id}
										value={c}
										handleUpdate={this.handleUpdate}
									/>
								</TableRow>
							))}
						</TableBody>
					</Table>
				</div>
				</div>
				</Fragment>
		);
	}
}

export default ClinicAdminAppointmentRequests;
