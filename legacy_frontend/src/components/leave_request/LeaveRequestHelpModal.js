import React, {Fragment} from 'react'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"

class LeaveRequestHelpModal extends React.Component {

    render() {
        return (<Fragment> 
                    <Modal
                      isOpen={this.props.modal}
                      toggle={this.props.toggle}
                       >
                      <ModalHeader toggle={this.props.toggle}>
                      Help
                      </ModalHeader>
                              <ModalBody>
                                To make a leave request, drag your cursor along the calendar dates you wish to request.

                                <br/>
                                Gray colored dates are your days off, based on your assigned work days.
                              </ModalBody>
                      <ModalFooter>
                          
                  <Button color="secondary" onClick={this.props.toggle}> Close</Button>
                      </ModalFooter>
                  
                  </Modal>
            </Fragment>)
    }
}

export default LeaveRequestHelpModal