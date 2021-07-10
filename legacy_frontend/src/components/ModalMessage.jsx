import React, { Component } from 'react';
import Modal from 'react-bootstrap/Modal'

class ModalMessage extends Component {
    state = {
        title: this.props.title,
        message: this.props.message
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.title !== this.props.title) {
            this.setState({title: this.props.title})
        }
        if (prevProps.message !== this.props.message) {
            this.setState({message: this.props.message})
        }
    }

    render() {
        return (
            <Modal
              size="md"
              aria-labelledby="contained-modal-title-vcenter"
              centered
              show={this.props.show}
            >
              <Modal.Header closeButton onClick={this.props.onHide}>
                    <Modal.Title id="contained-modal-title-vcenter">{this.state.title}</Modal.Title>
              </Modal.Header>
                    <Modal.Body>
                            <p>{this.state.message}</p>
                    </Modal.Body>
                    <Modal.Footer>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal" onClick={this.props.onHide}>Close</button>
                    </Modal.Footer>
                
            </Modal>
          );
    }
}

export default ModalMessage;