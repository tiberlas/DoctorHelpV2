import React, { Component, Fragment } from 'react';
import TableCell from '@material-ui/core/TableCell';
import Button from 'react-bootstrap/Button';

class RoomReservingItem extends Component {
    state = {
        roomId: this.props.value.idRoom,
        roomName: this.props.value.roomName,
        roomNumber: this.props.value.roomNumber,
        firstFreeDate: this.props.value.freeDate
    }

    render() { 
        return (
            <Fragment>
                <TableCell class='text text-white'>{this.state.roomName}</TableCell>
                <TableCell class='text text-white'>{this.state.roomNumber}</TableCell>
                <TableCell colSpan='2' class='text text-white'>{this.state.firstFreeDate}</TableCell>
                <TableCell>
                    <Button variant="primary" id={"room_selected"+this.state.roomId} onClick={(id) => {this.props.handleSchedule(this.state.roomId)}}>
                        Assign
                    </Button>
                </TableCell>
            </Fragment>
        );
    }
}
 
export default RoomReservingItem;