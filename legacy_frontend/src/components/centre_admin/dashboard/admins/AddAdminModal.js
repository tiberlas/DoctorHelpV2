import React, {Fragment, Component} from 'react'
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from "reactstrap"
import NewAdminForm from './NewAdminForm'

class AddAdminModal extends React.Component {


    constructor(props) {
        super(props)
        this.child = React.createRef();

    }

    state = {
        disabled: false
    }

    disabledOn = () => {
        this.setState({disabled: true})
    }

    disabledOff = (success) => {
        this.setState({disabled: false}, ()=>{
            if(success) {
                setTimeout(() => {this.props.update()}, 1000)
            }
        })
    }


    render() {
        return(
            <Fragment>
                <Modal
                isOpen={this.props.modal}
                toggle={this.props.toggle}
                className={this.props.className}
                closeTimeoutMS={2000} >
                                <ModalHeader toggle={this.props.toggle}>
                                New administrator
                                </ModalHeader>
                                        <ModalBody> {/* able to request means the selected dates are validated */}
                                            <NewAdminForm ref={this.child} disabledOn={this.disabledOn} disabledOff={this.disabledOff}/>
                                        </ModalBody>
                                <ModalFooter>
                                    
                            <Button color="secondary" onClick={this.props.toggle}> Close </Button>
                            <Button color="btn btn-success" onClick={()=>{this.child.current.handleSubmit()}} disabled={this.state.disabled}> Create </Button>
                                </ModalFooter>
                            </Modal>
                </Fragment>
        )
    }

}

export default AddAdminModal