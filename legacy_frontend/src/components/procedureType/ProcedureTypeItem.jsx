import React, { Component, Fragment } from 'react';
import axios from 'axios';
import ChangeProcedureTypeModal from './ChangeProcedureTypeModal';
import Button from 'react-bootstrap/Button';
import ButtonToolbar from 'react-bootstrap/ButtonToolbar';
import ModalMessage from '../ModalMessage';
import Checkbox from '@material-ui/core/Checkbox';
import TableCell from '@material-ui/core/TableCell';


class ProcedureTypeItem extends Component {
    state = {
        id: this.props.value.id,
        name: this.props.value.name,
        price: this.props.value.price,
        operation: this.props.value.operation,
        duration: this.props.value.duration,
        canChange: this.props.value.inUse,
        modalShow: false,
        messageShow: false,
        message: '',
        title: ''
    }
    
    onDelite = () => {
        axios.delete("/api/procedure+types/delete/id="+this.state.id)
        .then(response => {
            this.props.handleUpdate(this.state.id);
        }).catch(error => {
            this.setState({
                messageShow: true,
                message: 'Could not delete procedure type becouse it is in use in a appointment type!',
                title: 'Some error has occured'
            })
        })
    };

    update = (rname, rprice, roperation, rduration) => {
        this.setState({name: rname, price: rprice, operation: roperation, duration: rduration, modalShow: false})
    }

    setModalShow = () => {
        this.setState({modalShow: true})
    }

    setModalHide = () => {
        this.setState({modalShow: false})
    }

    setMessageHide= () => {
        this.setState({messageShow: false})
    }

    render() { 
        let durationParts = this.state.duration.split(':');
        return (
            <Fragment>
                <TableCell class="text-white">{this.state.name}</TableCell>
                <TableCell class="text-white">{durationParts[0]}:{durationParts[1]}H</TableCell>
                <TableCell class="text-white"><Checkbox checked={this.state.operation} name='operation' value='operation' disabled/></TableCell>
                <TableCell class="text-white">{this.state.price}&#x20bf;</TableCell>
                <TableCell class="text-white"><button onClick={this.onDelite} class='btn btn-danger' disabled={this.state.canChange}>delete</button></TableCell>
                <TableCell>
                    <ButtonToolbar>
                        <Button variant="success" onClick={this.setModalShow} disabled={this.state.canChange}>
                            change
                        </Button>

                        <ChangeProcedureTypeModal
                            id={this.state.id} 
                            name={this.state.name} 
                            price={this.state.price}
                            duration={this.state.duration}
                            operation={this.state.operation} 
                            handleUpdate={(rname, rprice, roperation, rduration) => this.update(rname, rprice, roperation, rduration)}
                            show={this.state.modalShow}
                            onHide={this.setModalHide}
                        />
                    </ButtonToolbar>

                    <ModalMessage
                        title={this.state.title}
                        message={this.state.message} 
                        show={this.state.messageShow}
                        onHide={this.setMessageHide}/>
                </TableCell>
            </Fragment>
         );
    }
}
 
export default ProcedureTypeItem;