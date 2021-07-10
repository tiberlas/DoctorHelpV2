import React, { Component } from "react";
import Modal from "react-bootstrap/Modal";
import axios from "axios";
import TimePicker from "react-time-picker/dist/TimePicker";
import FormControl from "react-bootstrap/FormControl";
import Select from "react-select";

const fontStyles = {
	option: (provided) => ({
		...provided,
		color: "black",
	}),
	control: (provided) => ({
		...provided,
		color: "black",
	}),
	singleValue: (provided) => ({
		...provided,
		color: "black",
	}),
};

class ScheduleOperation extends Component {
	state = {
		room: "",
		patient: "",
		procedure: "",
		procedureId: "",
		dateAndTime: "",
		time: "",
		date: "",
		scheduleRecomendedDate: "",
		doctorsOptions: [],
		selectedDoctor: [],
		success: false,
		fatalError: false,
		errorDoctor: false,
		errorDoctoCount: true,
		errorNoDoctors: false,
		doctorScheduleError: false,
	};

	componentDidUpdate(prevProps, prevState) {
		if (prevProps.roomId != this.props.roomId) {
			axios
				.get(
					"/api/rooms/one/room=" +
						this.props.roomId,
				)
				.then((response) => {
					this.setState({
						room: response.data.name + " #" + response.data.number,
					});
				});

			axios
				.get(
					"/api/operations/requests/id=" +
						this.props.operationId,
				)
				.then((response) => {
					this.setState(
						{
							dateAndTime: response.data.date,
							procedure:
								response.data.procedureName +
								" " +
								response.data.procedureDuration +
								"h",
							procedureId: response.data.procedureId,
							patient: response.data.patient,
						},
						() => {
							this.getDoctors();
							this.handelStringToDateAndTimeConversion();
						},
					);
				});
		}
	}

	handelStringToDateAndTimeConversion = () => {
		let parts = this.state.dateAndTime.split(" ");
		let dates = parts[0].split("/");
		let svDate = dates[2] + "-" + dates[0] + "-" + dates[1];
		this.setState({ date: svDate }, () => {
			this.handleChanngeDateAndTime();
		});

		if (parts[2] == "PM") {
			let hoursAndMinutes = parts[1].split(":");
			let hours = parseInt(hoursAndMinutes[0]);
			hours += 12;

			let timeString = hours + ":" + hoursAndMinutes[1];
			this.setState({ time: timeString }, () => {
				this.handleChanngeDateAndTime();
			});
		} else {
			this.setState({ time: parts[1] }, () => {
				this.handleChanngeDateAndTime();
			});
		}
	};

	handleDateAndTimeToStringConversion = () => {
		let dateParts = this.state.date.split("-");
		let time = this.state.time;
		let dayPeriod = "AM";
		let timePart = this.state.time.split(":");
		if (parseInt(timePart[0]) > 11) {
			dayPeriod = "PM";
			time = parseInt(timePart[0]) - 11;
			time = time.toString + ":" + timePart[1];
		}

		return (
			dateParts[1] +
			"/" +
			dateParts[2] +
			"/" +
			dateParts[0] +
			" " +
			time +
			" " +
			dayPeriod
		);
	};

	getDoctors = () => {
		axios
			.get(
				`/api/doctors/all/specialization=${this.state.procedureId}`,
			)
			.then((response) => {
				let items = [];
				var size = Object.keys(response.data).length;

				for (let i = 0; i < size; i++) {
					let option = {
						label:
							"dr " +
							response.data[i].firstName +
							" " +
							response.data[i].lastName,
						value: response.data[i].id,
					};
					items.push(option);
				}

				console.log("doctors: ", items);
				this.setState({
					doctorsOptions: items,
					errorDoctor: true,
					disabledDoctors: false,
					errorDoctoCount: true,
				});
			})
			.catch((error) => {
				this.setState({
					errorDoctor: true,
					disabledDoctors: true,
					errorNoDoctors: true,
					doctorsOptions: [],
					errorDoctoCount: true,
				});
			});
	};

	handleDoctorChange = (options) => {
		if (options === null) {
			this.setState({ selectedDoctor: [] });
			return;
		}
		let doctors = [];
		for (let i = 0; i < options.length; ++i) {
			doctors.push(options[i].value);
		}

		this.setState({ selectedDoctor: doctors }, () => {
			if (this.state.selectedDoctor.length === 3) {
				this.setState({ errorDoctoCount: false });
				axios
					.post(
						"/api/operations/schedules/check",
						{
							doctor0: this.state.selectedDoctor[0],
							doctor1: this.state.selectedDoctor[1],
							doctor2: this.state.selectedDoctor[2],
							dateAndTimeString:
								this.state.date + " " + this.state.time,
						},
					)
					.then((response) => {
						if (response.status === 201) {
							this.setState(
								{ scheduleRecomendedDate: response.data },
								() => {
									if (
										this.state.dateAndTime !=
										this.state.scheduleRecomendedDate
									) {
										this.setState({
											doctorScheduleError: true,
										});
									} else {
										this.setState({
											doctorScheduleError: false,
										});
									}
								},
							);
						} else {
							this.setState({ doctorScheduleError: false });
						}
					});
			} else {
				this.setState({ errorDoctoCount: true });
			}
		});
	};

	handleChanngeDateAndTime = () => {
		this.setState({
			dateAndTime: this.handleDateAndTimeToStringConversion(),
		});
	};

	handleChangeTime = (time) => {
		this.setState({ time }, () => {
			this.handleChanngeDateAndTime();
		});
	};

	handleChangeDate = (event) => {
		this.setState({ date: event.target.value }, () => {
			this.handleChanngeDateAndTime();
		});
	};

	handleBless = (event) => {
		event.preventDefault();
		let dateAndTimeString = this.state.date + " " + this.state.time;
		axios
			.post("/api/operations/schedules/bless", {
				dateAndTimeString: dateAndTimeString,
				operationId: this.props.operationId,
				doctor0: this.state.selectedDoctor[0],
				doctor1: this.state.selectedDoctor[1],
				doctor2: this.state.selectedDoctor[2],
				roomId: this.props.roomId,
			})
			.then((response) => {
				this.setState({
					errorDateAndTime: false,
					success: true,
					doctorScheduleError: false,
					errorDoctors: false,
					doctorScheduleError: false,
				});
			})
			.catch((error) => {
				if (error.response.status == 409) {
					//dobijen predlog za drugi termin
					let errorParts = error.response.data.split("#");
					if (errorParts[0] == "DOCTOR") {
						this.setState({
							errorDateAndTime: false,
							errorDoctors: true,
							doctorScheduleError: true,
							scheduleRecomendedDate: errorParts[1],
						});
					} else {
						this.setState({
							//sobi ne odgovara
							errorDoctors: false,
							doctorScheduleError: false,
							errorDateAndTime: true,
							scheduleRecomendedDate: errorParts[1],
						});
					}
				} else {
					if (this.state.errorDoctoCount == false) {
						this.setState({ fatalError: true });
					}
				}
			});
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
						Assign an operating room
					</Modal.Title>
				</Modal.Header>
				<form onSubmit={this.handleBless}>
					<Modal.Body>
						<div class="form-group">
								<label
									class="control-label"
									for="disabledInput"
								>
									<i class="fas fa-procedures"></i> Procedure &emsp; {this.state.procedure}
								</label>
								
						</div>
						<div class="form-group">
								<label
									class="control-label"
									for="disabledInput"
								>
									<i class="fas fa-user-injured"></i> Patient&emsp;{this.state.patient}
								</label>
						</div>
						<div class="form-group">
								<label
									class="control-label"
									for="disabledInput"
								>
									<i class="fas fa-door-open"></i> Room &emsp; {this.state.room}
								</label>
						</div>
						<hr class="my-4" />

						<div
							className={`form-group ${
								this.state.errorDoctor ? "has-danger" : ""
							}`}
						>
							<label for="doctorsSelect">Doctors</label>
							<Select
								id="doctorsSelect"
								styles={fontStyles}
								isMulti
								className={"basic-multi-select"}
								classNamePrefix="select"
								name="doctors"
								options={this.state.doctorsOptions}
								onChange={this.handleDoctorChange}
								isClearable="true"
								isDisabled={this.state.success}
							/>
						</div>
						{this.state.errorDoctoCount && (
							<p class="text-warning">
								3 doctors have to be selected.
							</p>
						)}
						{this.state.doctorScheduleError && (
							<p class="text-danger">
								Unfortunately, some doctors are unavailable. Try with {" "}
								{this.state.scheduleRecomendedDate}.
							</p>
						)}

						<div>
							<label for="date">Date</label>
							<FormControl
								type="date"
								id="date"
								placeholder="Date in format: dd/mm/yyyy"
								onChange={this.handleChangeDate}
								value={this.state.date}
								className={`form-control ${
									this.state.errorDateAndTime
										? "is-invalid"
										: "is-valid"
								}`}
								disabled={this.state.success}
							/>
						</div>
						<div>
							<label for="time">Time</label>
							<TimePicker
								name="duration"
								id="time"
								onChange={this.handleChangeTime}
								locale="us"
								value={this.state.time}
								className={`form-control ${
									this.state.errorDateAndTime
										? "is-invalid"
										: "is-valid"
								}`}
								disabled={this.state.success}
							/>
						</div>
						{this.state.errorDateAndTime && (
							<div class="text-danger">
								{" "}
								Work schedule is occupied. Available date-time is {" "}
								{this.state.scheduleRecomendedDate}.{" "}
							</div>
						)}

						{this.state.success && (
							<p class="text-success">
								Successfully blessed operation!
							</p>
						)}
						{this.state.fatalError && (
							<p class="text-danger">
								Something went wrong, please refresh the page and try again.
							</p>
						)}
					</Modal.Body>
					<Modal.Footer>
						<button
							type="button"
							class="btn btn-secondary"
							data-dismiss="modal"
							onClick={(success) => {
								this.props.onHide(this.state.success);
							}}
						>
							Close
						</button>
						<input
							type="submit"
							class="btn btn-success"
							value="Bless"
							disabled={
								this.state.success
									? true
									: this.state.errorDoctoCount
							}
						/>
					</Modal.Footer>
				</form>
			</Modal>
		);
	}
}

export default ScheduleOperation;
