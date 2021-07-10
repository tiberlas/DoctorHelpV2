import React, { Component } from "react";
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import RoomItem from "../rooms/RoomItem";
import axios from "axios";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Button from "react-bootstrap/Button";
import RoomModalSearch from "../rooms/RoomModalSearch";
import NewRoom from "../rooms/NewRoom";

const sortTypes = {
	name_up: {
		fn: (a, b) => b.name.localeCompare(a.name),
	},
	name_down: {
		fn: (a, b) => a.name.localeCompare(b.name),
	},
	type_up: {
		fn: (a, b) => b.procedureTypeName.localeCompare(a.procedureTypeName),
	},
	type_down: {
		fn: (a, b) => a.procedureTypeName.localeCompare(b.procedureTypeName),
	},
	number_up: {
		fn: (a, b) => a.number - b.number,
	},
	number_down: {
		fn: (a, b) => b.number - a.number,
	},
	date_up: {
		fn: (a, b) => b.firstFreeSchedule.localeCompare(a.firstFreeSchedule),
	},
	date_down: {
		fn: (a, b) => a.firstFreeSchedule.localeCompare(b.firstFreeSchedule),
	},
	default: {
		fn: (a, b) => a,
	},
};

class HandlingRooms extends Component {
	state = {
		rooms: [],
		currentSort: "default",
		modalShow: false,
		name: "",
		showAddModal: false
	};

	static contextType = ClinicAdminContext;

	componentDidMount() {
		this.handleShowAll();

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

	handleUpdate = (key) => {
		const items = this.state.rooms.filter((item) => item.id !== key);
		console.log("items", items);
		this.setState({ rooms: items });
		console.log("state", items);
	};

	onSortChange = (name) => {
		const { currentSort } = this.state;
		let nextSort;

		if (name === "name") {
			if (currentSort === "name_down") nextSort = "name_up";
			else if (currentSort === "name_up") nextSort = "default";
			else nextSort = "name_down";
		} else if (name === "type") {
			if (currentSort === "type_down") nextSort = "type_up";
			else if (currentSort === "type_up") nextSort = "default";
			else nextSort = "type_down";
		} else if (name === "date") {
			if (currentSort === "date_down") nextSort = "date_up";
			else if (currentSort === "date_up") nextSort = "default";
			else nextSort = "date_down";
		} else {
			if (currentSort === "number_down") nextSort = "number_up";
			else if (currentSort === "number_up") nextSort = "default";
			else nextSort = "number_down";
		}

		this.setState(
			{
				currentSort: nextSort,
			},
			() => {
				this.renderArrowName();
				this.renderArrowNumber();
				this.renderArrowType();
				this.renderArrowDate();
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

	renderArrowType = () => {
		if (this.state.currentSort === "type_up") {
			return "\u2191";
		} else if (this.state.currentSort === "type_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	renderArrowNumber = () => {
		if (this.state.currentSort === "number_up") {
			return "\u2191";
		} else if (this.state.currentSort === "number_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	renderArrowDate = () => {
		if (this.state.currentSort === "date_up") {
			return "\u2191";
		} else if (this.state.currentSort === "date_down") {
			return "\u2193";
		} else {
			return "";
		}
	};

	handleSearchClick = () => {
		this.setState({ modalShow: !this.state.modalShow });
	};

	handleHideModal = () => {
		this.setState({ modalShow: false });
	};

	handleShowAll = () => {
		axios.get("/api/rooms/all").then((response) => {
			this.setState({
				rooms: response.data,
			});
		});
	};

	handleRoomSearch = (name, number, typeId, date, time) => {
		this.setState({ modalShow: false });
		let rname = null;
		let rnumber = null;
		let rtype = null;
		let rdate = null;

		if (name !== "" && name !== null) {
			rname = name;
		}
		if (number !== "" && number !== null) {
			rnumber = number;
		}
		if (typeId !== "" && typeId !== null) {
			rtype = typeId;
		}
		if (date !== "" && date !== null && time !== "" && time !== null) {
			rdate = date + " " + time;
		}

		console.log(rname, rnumber, rtype, rdate);

		axios
			.post("/api/rooms/search", {
				name: rname,
				number: rnumber,
				typeId: rtype,
				date: rdate,
			})
			.then((response) => {
				this.setState({ rooms: response.data });
			});
	};

	handleAddRoom = () => {
		this.setState({ showAddModal: !this.state.showAddModal })
	}

	handleCreatedRoom = () => {
		this.handleAddRoom()
		this.handleShowAll()
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
							<h4>List of rooms</h4>
						</div>
						<div class='col'>
							<button class='btn btn-success rounded-circle float-right mr-5' onClick={this.handleAddRoom}>+</button>
						</div>
					</div>
					<br />
					<NewRoom
						show={this.state.showAddModal}
						onHide={this.handleAddRoom}
						onSubmit={this.handleCreatedRoom}
					/>
					<Table class="table table-hover ">
						<TableHead>
							<TableRow>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("name")}
								>
									room&nbsp;name{this.renderArrowName()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("number")}
								>
									number{this.renderArrowNumber()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("type")}
								>
									procedure&nbsp;name{this.renderArrowType()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("date")}
								>
									first&nbsp;free&nbsp;date
									{this.renderArrowDate()}
								</TableCell>
								<TableCell class="text-success">
									<Button
										variant="btn btn-success"
										onClick={this.handleShowAll}
									>
										show&nbsp;all
									</Button>
								</TableCell>
								<TableCell class="text-success">
									<Button
										variant="btn btn-success"
										onClick={this.handleSearchClick}
									>
										search
									</Button>
								</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{this.state.rooms
								.sort(sortTypes[this.state.currentSort].fn)
								.map((c) => (
									<TableRow
										className={++i % 2 ? `table-dark` : ``}
									>
										<RoomItem
											key={c.id}
											id={c.id}
											value={c}
											handleUpdate={this.handleUpdate}
										/>
									</TableRow>
								))}
						</TableBody>
					</Table>

					<RoomModalSearch
						show={this.state.modalShow}
						onHide={this.handleHideModal}
						handleSearch={(name, number, typeId, date, time) =>
							this.handleRoomSearch(
								name,
								number,
								typeId,
								date,
								time,
							)
						}
					/>
				</div>
			</div>
		);
	}
}

export default HandlingRooms;
