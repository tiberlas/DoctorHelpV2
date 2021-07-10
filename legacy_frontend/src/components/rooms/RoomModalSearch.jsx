import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import TimePicker from "react-time-picker";
import axios from "axios";

class RoomModalSearch extends Component {
	state = {
		name: "",
		number: "",
		procedureTypeId: "",
		date: "",
		time: null,
		procedureList: {},
		errorNumber: false,
		errorTime: false,
		errorDate: false,
		hasType: false,
	};

	componentDidMount() {
		axios
			.get("/api/procedure+types/all")
			.then((response) => {
				this.setState({ procedureList: response.data });
			});
		if (this.props.hasType == true) {
			this.setState({ hasType: true });
		}
	}

	handleValidation = () => {
		if (this.state.number === "") {
			this.setState({ errorNumber: false });
		} else if (this.state.number < 1) {
			this.setState({ errorNumber: true });
		} else {
			this.setState({ errorNumber: false });
		}

		if (this.state.date !== "" && this.state.time == null) {
			this.setState({ errorTime: true });
		} else if (this.state.date == "" && this.state.time !== null) {
			this.setState({ errorDate: true });
		} else {
			this.setState({ errorDate: false, errorTime: false });
		}
	};

	handlerChange = (event) => {
		let nam = event.target.name;
		let val = event.target.value;
		this.setState({ [nam]: val }, () => {
			this.handleValidation();
		});
	};

	handleChangeTime = (time) => {
		this.setState({ time: time }, () => {
			this.handleValidation();
		});
	};

	handlerChangeProcedureType = (event) => {
		let val = event.target.value;
		if (val === "") {
			this.setState({ procedureTypeId: "" });
		} else {
			this.setState({ procedureTypeId: parseInt(val) });
		}
	};

	createProcedureItems() {
		let items = [];
		var size = Object.keys(this.state.procedureList).length;
		items.push(
			<option
				key={size + 1}
				name="procedureTypeId"
				value=""
				selected={this.state.procedureTypeId === "" ? "selected" : ""}
			>
				{" "}
				----{" "}
			</option>,
		);
		for (let i = 0; i < size; i++) {
			items.push(
				<option
					key={i}
					name="procedureTypeId"
					selected={
						this.state.procedureTypeId ===
							this.state.procedureList[i].id
							? "selected"
							: ""
					}
					value={this.state.procedureList[i].id}
				>
					{this.state.procedureList[i].name}:{" "}
					{this.state.procedureList[i].price}
				</option>,
			);
		}
		return items;
	}

	handleSearch = (event) => {
		event.preventDefault();
		this.props.handleSearch(
			this.state.name,
			this.state.number,
			this.state.procedureTypeId,
			this.state.date,
			this.state.time,
		);
	};

	render() {
		return (
			<Modal
				size="md"
				aria-labelledby="contained-modal-title-vcenter"
				centered
				show={this.props.show}
			>
				<Modal.Header closeButton onClick={this.props.onHide}>
					<Modal.Title id="contained-modal-title-vcenter">
						Search for room
					</Modal.Title>
				</Modal.Header>
				<form onSubmit={this.handleSearch}>
					<Modal.Body>
						<div class="form-group">
							<label class="form-control-label" for="name">
								Room's name:
							</label>
							<input
								type="text"
								name="name"
								id="name"
								class="form-control"
								value={this.state.name}
								onChange={this.handlerChange}
							/>
						</div>

						<div
							className={`form-group ${
								this.state.errorNumber ? "has-danger" : ""
								}`}
						>
							<label class="form-control-label" for="number">
								Room's number:
							</label>
							<input
								type="number"
								min="1"
								name="number"
								id="number"
								className={`form-control ${
									this.state.errorNumber
										? "is-invalid"
										: "is-valid"
									}`}
								value={this.state.number}
								onChange={this.handlerChange}
							/>
							{this.state.errorNumber && (
								<div class="invalid-feedback">
									{" "}
									Must enter a positive value.{" "}
								</div>
							)}
						</div>

						{this.state.hasType != true && (
							<div class="form-group">
								<label for="procedureTypeId">
									Procedure name:
								</label>
								<select
									multiple=""
									class="form-control"
									id="procedureTypeId"
									name="procedureTypeId"
									onChange={this.handlerChangeProcedureType}
								>
									{this.createProcedureItems()}
								</select>
							</div>
						)}

						<div
							className={`form-group ${
								this.state.errorDate ? "has-danger" : ""
								}`}
						>
							<label class="form-control-label" for="date">
								Date for scheduling:
							</label>
							<input
								type="date"
								name="date"
								id="date"
								className={`form-control ${
									this.state.errorDate
										? "is-invalid"
										: "is-valid"
									}`}
								value={this.state.date}
								onChange={this.handlerChange}
							/>
							{this.state.errorDate && (
								<div class="invalid-feedback">
									{" "}
									Must enter date if time is defined.{" "}
								</div>
							)}
						</div>

						<div
							className={`form-group ${
								this.state.errorTime ? "has-danger" : ""
								}`}
						>
							<label class="form-control-label" for="time">
								Schedule time:
							</label>
							<TimePicker
								name="time"
								id="time"
								onChange={this.handleChangeTime}
								locale="us"
								value={this.state.time}
								className={`form-control ${
									this.state.errorTime
										? "is-invalid"
										: "is-valid"
									}`}
							/>
							{this.state.errorTime && (
								<div class="invalid-feedback">
									{" "}
									Must enter time if date is defined.{" "}
								</div>
							)}
						</div>
					</Modal.Body>
					<Modal.Footer>
						<div class="form-group row">
							<div class="col-md text-left">
								<input
									type="submit"
									value="Search"
									id="submit_select"
									class="btn btn-success"
									data-dismiss="modal"
									disabled={
										this.state.errorNumber ||
										this.state.errorDate ||
										this.state.errorTime
									}
								></input>
							</div>
							<div class="col-md text-right">
								<button
									id="submit_search"
									type="button"
									class="btn btn-secondary"
									data-dismiss="modal"
									onClick={this.props.onHide}
								>
									Close
								</button>
							</div>
						</div>
					</Modal.Footer>
				</form>
			</Modal>
		);
	}
}

export default RoomModalSearch;
