import React, { Component, Fragment } from "react";
import axios from "axios";
import ChangeRoomModal from "./ChangeRoomModal";
import Button from "react-bootstrap/Button";
import ButtonToolbar from "react-bootstrap/ButtonToolbar";
import ModalMessage from "../ModalMessage";
import TableCell from "@material-ui/core/TableCell";
import { Redirect } from "react-router-dom";

class RoomItem extends Component {
	constructor(props) {
		super(props);

		this.state = {
			id: this.props.value.id,
			name: this.props.value.name,
			number: this.props.value.number,
			type: this.props.value.procedureTypeName,
			typeId: this.props.value.procedureTypeId,
			firstFreeSchedule: this.props.value.firstFreeSchedule,
			reserved: this.props.value.reserved,
			modalShow: false,
			messageShow: false,
			message: "",
			title: "",
			goto_schedule: false,
		};
	}

	handeViewSchedule = () => {
		this.setState({ goto_schedule: true });
	};

	onDelite = () => {
		axios
			.delete(
				"/api/rooms/delete/id=" + this.state.id,
			)
			.then((response) => {
				this.props.handleUpdate(this.state.id);
			})
			.catch((error) => {
				this.setState({
					messageShow: true,
					message:
						"Could not delete room. Please reload page and try again!",
					title: "Some error has occured",
				});
			});
	};

	update = (rname, rnumber, rtype, rid) => {
		this.setState({
			name: rname,
			number: rnumber,
			type: rtype,
			typeId: rid,
			modalShow: false,
		});
	};

	setModalShow = () => {
		this.setState({ modalShow: true });
	};

	setModalHide = () => {
		this.setState({ modalShow: false });
	};

	setMessageHide = () => {
		this.setState({ messageShow: false });
	};

	render() {
		return (
			<Fragment>
				{this.state.goto_schedule && (
					<Redirect exact to={`/schedule/${this.state.id}`} />
				)}

				<TableCell
					class="text text-white"
					onClick={this.handeViewSchedule}
				>
					{this.state.name}
				</TableCell>
				<TableCell
					class="text text-white"
					onClick={this.handeViewSchedule}
				>
					{this.state.number}
				</TableCell>
				<TableCell
					class="text text-white"
					onClick={this.handeViewSchedule}
				>
					{this.state.type}
				</TableCell>
				<TableCell
					class="text text-white"
					onClick={this.handeViewSchedule}
				>
					{this.state.firstFreeSchedule}
				</TableCell>
				<TableCell>
					<button
						onClick={this.onDelite}
						class="btn btn-danger"
						disabled={this.state.reserved}
					>
						delete
					</button>
				</TableCell>
				<TableCell>
					<ButtonToolbar>
						<Button
							variant="success"
							onClick={this.setModalShow}
							disabled={this.state.reserved}
						>
							change
						</Button>

						<ChangeRoomModal
							id={this.state.id}
							name={this.state.name}
							number={this.state.number}
							type={this.state.type}
							typeId={this.state.typeId}
							handleUpdate={(rname, rnumber, rtype, rid) =>
								this.update(rname, rnumber, rtype, rid)
							}
							show={this.state.modalShow}
							onHide={this.setModalHide}
						/>
					</ButtonToolbar>

					<ModalMessage
						title={this.state.title}
						message={this.state.message}
						show={this.state.messageShow}
						onHide={this.setMessageHide}
					/>
				</TableCell>
			</Fragment>
		);
	}
}
export default RoomItem;
