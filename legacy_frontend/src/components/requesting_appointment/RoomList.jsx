import React, { Component } from "react";
import { ClinicAdminContext } from "../../context/ClinicAdminContextProvider";
import RoomReservingItem from "./RoomReservingItem.jsx";
import axios from "axios";
import Table from "@material-ui/core/Table";
import TableBody from "@material-ui/core/TableBody";
import TableCell from "@material-ui/core/TableCell";
import TableHead from "@material-ui/core/TableHead";
import TableRow from "@material-ui/core/TableRow";
import Button from "react-bootstrap/Button";
import RoomModalSearch from "../rooms/RoomModalSearch";
import ScheduleAppointment from "./ScheduleAppointment";
import { Redirect } from "react-router-dom";
import ScheduleOperation from "../requesting_operations/ScheduleOperation";

const sortTypes = {
	name_up: {
		fn: (a, b) => b.name.localeCompare(a.name),
	},
	name_down: {
		fn: (a, b) => a.name.localeCompare(b.name),
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

class RoomList extends Component {
	state = {
		type: this.props.type,
		date: this.props.date,
		appointmentId: this.props.appointment,
		operationId: this.props.operationId,
		rooms: [],
		currentSort: "default",
		modalShow: false,
		name: "",
		book: false,
		roomId: 0,
		go_appointmentList: false,
		go_operationList: false,
		operation: false,
	};

	static contextType = ClinicAdminContext;

	componentDidMount() {
		this.handleGetAllRooms();
	}

	componentDidUpdate(prevProps, prevState) {
		if (prevProps.type !== this.props.type) {
			this.setState(
				{
					type: this.props.type,
					date: this.props.date,
					appointmentId: this.props.appointment,
					operation: this.props.operation,
					operationId: this.props.operationId,
				},
				() => {
					this.handleGetAllRooms();
				},
			);
		}
	}

	handleGetAllRooms = () => {
		axios
			.post("/api/rooms/all/", {
				typeId: this.state.type,
				date: this.state.date,
			})
			.then((response) => {
				this.setState({ rooms: response.data, currentSort: "default" });
			});
	};

	onSortChange = (name) => {
		const { currentSort } = this.state;
		let nextSort;

		if (name === "name") {
			if (currentSort === "name_down") nextSort = "name_up";
			else if (currentSort === "name_up") nextSort = "default";
			else nextSort = "name_down";
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
		this.handleGetAllRooms();
	};

	handleRoomSearch = (name, number, date, time) => {
		this.setState({ modalShow: false });
		let rname = null;
		let rnumber = null;
		let rdate = null;

		if (name !== "" && name !== null) {
			rname = name;
		}
		if (number !== "" && number !== null) {
			rnumber = number;
		}
		if (date !== "" && date !== null && time !== "" && time !== null) {
			rdate = date + " " + time;
		}

		console.log(rname, rnumber, rdate);

		axios
			.post("/api/rooms/search", {
				name: rname,
				number: rnumber,
				typeId: this.state.type,
				date: rdate,
			})
			.then((response) => {
				let items = [];
				for (var i = 0; i < response.data.length; ++i) {
					let item = {
						idRoom: response.data[i].id,
						freeDate: response.data[i].firstFreeSchedule,
						roomName: response.data[i].name,
						roomNumber: response.data[i].number,
					};

					items.push(item);
				}

				this.setState({ rooms: items }); //daje sa drugin nazivima treba premapirati
			});
	};

	handleCancleBooking = (success) => {
		this.setState({ book: false }, () => {
			console.log("success", success);
			if (success === true) {
				if (this.state.operation == true) {
					this.setState({ go_operationList: true });
				} else {
					this.setState({ go_appointmentList: true });
				}
			}
		});
	};

	handleSchedule = (id) => {
		this.setState({ book: true, roomId: id, nurse: this.props.nurse });
	};

	render() {
		if (this.state.go_appointmentList === true)
			return (
				<Redirect to="/clinic-administrator/requests/appointments"></Redirect>
			);
		if (this.state.go_operationList === true)
			return (
				<Redirect to="/clinic-administrator/requests/operations"></Redirect>
			);
		let i = 0;
		return (
			<div class="row d-flex justify-content-center">
				<div class="col-md-11">
					<h4>Room list:</h4>
					<br />
					<Table class="table table-hover ">
						<TableHead >
							<TableRow class="table-active">
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("name")}
								>
									<i class="fas fa-door-open"></i> Name {this.renderArrowName()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("number")}
								>
									<i class="fas fa-door-open"></i> Number #{this.renderArrowNumber()}
								</TableCell>
								<TableCell
									class="text-success cursor-pointer"
									onClick={() => this.onSortChange("date")}
								>
									<i class="fas fa-clock"></i> Available date{this.renderArrowDate()}
								</TableCell>
								<TableCell class="text-success">
									<Button
										class="btn btn-success"
										id="all_rooms"
										onClick={this.handleShowAll}
									>
										Show all
									</Button>
								</TableCell>
								<TableCell>
									<Button
										data-testid="searchButton"
										id="search_rooms"
										onClick={this.handleSearchClick}
									>
										Search
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
										<RoomReservingItem
											key={c.id}
											id={c.id}
											value={c}
											handleSchedule={(id) => {
												this.handleSchedule(id);
											}}
										/>
									</TableRow>
								))}
						</TableBody>
					</Table>

					<RoomModalSearch
						data-testid="modalSearch"
						hasType={true}
						show={this.state.modalShow}
						onHide={this.handleHideModal}
						handleSearch={(name, number, date, time) =>
							this.handleRoomSearch(name, number, date, time)
						}
					/>

					{this.state.operation ? (
						<ScheduleOperation
							operationId={this.state.operationId}
							onHide={(success) => {
								this.handleCancleBooking(success);
							}}
							show={this.state.book}
							roomId={this.state.roomId}
						/>
					) : (
						<ScheduleAppointment
							onHide={(success) => {
								this.handleCancleBooking(success);
							}}
							show={this.state.book}
							roomId={this.state.roomId}
							appointmentId={this.state.appointmentId}
						/>
					)}
				</div>
			</div>
		);
	}
}

export default RoomList;
