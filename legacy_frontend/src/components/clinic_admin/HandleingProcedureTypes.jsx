import React, { Component } from "react";
import ProcedureTypeItem from "../procedureType/ProcedureTypeItem";
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import axios from "axios";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Button from "react-bootstrap/Button";
import '../../customRadioButton.css';
import NewProcedureType from "../procedureType/NewProcedureType";


const sortTypes = {
	name_up: {
		fn: (a, b) => b.name.localeCompare(a.name),
	},
	name_down: {
		fn: (a, b) => a.name.localeCompare(b.name),
	},
	duration_up: {
		fn: (a, b) => b.duration.localeCompare(a.duration),
	},
	duration_down: {
		fn: (a, b) => a.duration.localeCompare(b.duration),
	},
	price_up: {
		fn: (a, b) => a.price - b.price,
	},
	price_down: {
		fn: (a, b) => b.price - a.price,
	},
	default: {
		fn: (a, b) => a,
	},
};

class HandleingProcedureTypes extends Component {
	state = {
		procedures: [],
		shownProcedures: [],
		name: "",
		refresh: false,
		filterString: "",
		//isFilterOperationActive: false,
		currentSort: "default",
		checkFilter: "NOT_OPERATION",
		filterOperationDTO: "NOT_DEFINED",
		showAddType: false
	};

	static contextType = ClinicAdminContext;

	componentDidMount() {
		this.handleAllProcedureType()

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
	}

	handleAllProcedureType = () => {
		axios
			.get("/api/procedure+types/all")
			.then((response) => {
				this.setState({
					procedures: response.data,
					shownProcedures: response.data,
					refresh: false,
				});
			});
	}

	onSortChange = (name) => {
		const { currentSort } = this.state;
		let nextSort;

		if (name === "name") {
			if (currentSort === "name_down") nextSort = "name_up";
			else if (currentSort === "name_up") nextSort = "default";
			else nextSort = "name_down";
		} else if (name === "duration") {
			if (currentSort === "duration_down") nextSort = "duration_up";
			else if (currentSort === "duration_up") nextSort = "default";
			else nextSort = "duration_down";
		} else {
			if (currentSort === "price_down") nextSort = "price_up";
			else if (currentSort === "price_up") nextSort = "default";
			else nextSort = "price_down";
		}

		this.setState(
			{
				currentSort: nextSort,
			},
			() => {
				this.renderArrowName();
				this.renderArrowDuration();
				this.renderArrowPrice();
			},
		);
	};

	renderArrowName = () => {
		if (this.state.currentSort === "name_up") {
			return "\u2191";
		} else if (this.state.currentSort === "name_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	renderArrowDuration = () => {
		if (this.state.currentSort === "duration_up") {
			return "\u2191";
		} else if (this.state.currentSort === "duration_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	renderArrowPrice = () => {
		if (this.state.currentSort === "price_up") {
			return "\u2191";
		} else if (this.state.currentSort === "price_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	handleUpdate = (key) => {
		const items = this.state.procedures.filter((item) => item.id !== key);
		this.setState({ procedures: items, refresh: true });
	};

	handleChange = (event) => {
		this.setState({ [event.target.name]: event.target.value });
	};

	handleOptionChange = (changeEvent) => {
		this.setState(
			{
				filterOperationDTO: changeEvent.target.value,
			}, () => {
				this.updateOptionChange()
			}
		);
	};

	updateOptionChange = () => {
		if (this.state.filterOperationDTO == 'OPERATION') {
			let items = this.state.procedures.filter(item => item.operation == true)
			this.setState({ shownProcedures: items })
		} else if (this.state.filterOperationDTO == 'NOT_OPERATION') {
			let items = this.state.procedures.filter(item => item.operation == false)
			this.setState({ shownProcedures: items })
		} else {
			this.setState({ shownProcedures: this.state.procedures })
		}
	}

	handleFilter = () => {
		axios
			.post("/api/procedure+types/filter", {
				string: this.state.filterString,
				operation: 'NOT_DEFINED',
			})
			.then((response) => {
				this.setState({ procedures: response.data }, () => {
					this.updateOptionChange();
				});
			})
			.catch((error) => {
				console.log("error in filter of procedure types");
			});
	};

	handleAddType = () => {
		this.setState({ showAddType: !this.state.showAddType })
	}

	handleCreatetType = () => {
		this.handleAddType();
		this.handleAllProcedureType()
	}

	render() {
		let i = 0;
		return (
			<div class="row d-flex justify-content-center">
				<div class="col-md-7">
					<br />
					<h3>Clinic {this.state.name}</h3>
					<div class='row'>
						<div class='col'>
							<h4>List of procedure types</h4>
						</div>
						<div class='col'>
							<button class='btn btn-success rounded-circle float-right mr-5' onClick={this.handleAddType}>+</button>
						</div>
					</div>
					<br />
					<NewProcedureType
						show={this.state.showAddType}
						onHide={this.handleAddType}
						onSubmit={this.handleCreatetType}
					/>
					<Table class="table table-hover ">
						<TableHead>
							<TableRow>
								<TableCell colSpan='4'>
									<ul>
										<li>
											<input
												type="radio"
												id="customRadio3"
												name="customRadio"
												value="NOT_DEFINED"
												defaultChecked
												onChange={this.handleOptionChange}
											/>
											<label
												class="text-white"
												for="customRadio3"
											>
												all
											</label>
											<div class="check"></div>
										</li>
										<li>
											<input
												type="radio"
												id="customRadio1"
												name="customRadio"
												value="NOT_OPERATION"
												onChange={this.handleOptionChange}
											/>
											<label
												class="text-white"
												for="customRadio1"
											>
												appointment
											</label>
											<div class="check"></div>
										</li>
										<li>
											<input
												type="radio"
												id="customRadio2"
												name="customRadio"
												value="OPERATION"
												onChange={this.handleOptionChange}
											/>
											<label
												class="text-white"
												for="customRadio2"
											>
												operation
											</label>
											<div class="check"></div>
										</li>
									</ul>
								</TableCell>
								<TableCell>
									<input
										type="text"
										placeholder="Filter..."
										name="filterString"
										onChange={this.handleChange}
									/>
								</TableCell>
								<TableCell>
									<Button
										variant="btn btn-success"
										onClick={this.handleFilter}
									>
										Search
									</Button>
								</TableCell>
							</TableRow>
							<TableRow class="table-active">
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("name")}
								>
									name{this.renderArrowName()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() =>
										this.onSortChange("duration")
									}
								>
									duration{this.renderArrowDuration()}
								</TableCell>
								<TableCell class="text-success">
									type
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("price")}
								>
									price{this.renderArrowPrice()}
								</TableCell>
								<TableCell></TableCell>
								<TableCell></TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{this.state.shownProcedures
								.sort(sortTypes[this.state.currentSort].fn)
								.map((c) => (
									<TableRow
										className={++i % 2 ? `table-dark` : ``}
									>
										<ProcedureTypeItem
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
			</div >
		);
	}
}

export default HandleingProcedureTypes;
