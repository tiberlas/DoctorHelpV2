import React, { Component, Fragment } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap";

class RoomModalView extends Component {
    state = {

    }

    render() { 
        let begin = new Date(this.props.reservedRoom.startTime);
        let end = new Date(this.props.reservedRoom.endTime);
        return (

            <Fragment>
            <Modal
              isOpen = {this.props.show}
              size="md"
              aria-labelledby="contained-modal-title-vcenter"
              centered
            >
              <ModalHeader closeButton onClick={this.props.onHide}>
                    {this.props.reservedRoom.title}
              </ModalHeader>
                    <ModalBody>
                        <p>Room is reserved from {begin.toLocaleDateString("en-us")} {begin.toLocaleTimeString("en-us")} to {end.toLocaleDateString("en-us")} {end.toLocaleTimeString("en-us")}</p>
                    </ModalBody>
                    <ModalFooter>
                        <Button color='secondary' onClick={this.props.onHide}>Close</Button>
                    </ModalFooter>
                
            </Modal>
            </Fragment>
        );
    }
}
 
export default RoomModalView;