import React, { Component } from 'react';
import { ClinicAdminContext } from '../../context/ClinicAdminContextProvider';
import axios from 'axios';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import RequestedOperationItem from '../requesting_operations/RequestedOperationItem.jsx';
import { Redirect } from "react-router-dom";

class ClinicAdminOperationRequest extends Component {
    state = {
        name: '',
        operations: [],
        id: 0,
        redirectState: false
    }

    static contextType = ClinicAdminContext;

    componentDidMount() {
        this.getAllOperations();
        this.handleClinicName();
    }

    getAllOperations = () => {
        axios.get('/api/operations/requested/all')
            .then(response => {
                this.setState({
                    operations: response.data
                })
            })
    }

    handleClinicName = () => {
        axios.get('/api/clinics/id=' + this.context.admin.clinicId)
            .then(response => {
                this.setState({
                    name: response.data.name
                })
            })
    }

    handleRedirect = (id) => {
        this.setState({ redirectState: true, id: id })
    }

    render() {
        let i = 0;
        return (
            <div>
                    {this.state.redirectState &&
                        <Redirect exact to={`/request/operation/${this.state.id}`} />
                    }
                    <br />
                    <div class='row d-flex justify-content-center'>
                <div class='col-md-4'>
                    <h3> <i class="fas fa-hospital-alt"></i> {this.state.name}</h3>

                    </div>
                    </div>


                    <div class='row d-flex justify-content-center'>
                <div class='col-md-11'> 
                <br/>
                    <Table class="table table-hover ">
                        <TableHead>
                            <TableRow class="table-active">
                                <TableCell class="text-success cursor-pointer" > <i class="fas fa-clock"></i>  Date</TableCell>
                                <TableCell class="text-success cursor-pointer" ><i class="fas fa-procedures"></i> Procedure</TableCell>
                                <TableCell class="text-success cursor-pointer" ><i class="fas fa-user-injured"></i> Patient</TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.operations.map(c => (
                                <TableRow style={{cursor: 'pointer'}}
                                    className={(++i) % 2 ? `table-dark` : ``}
                                    onClick={(id) => this.handleRedirect(c.operationId)}
                                >
                                    <RequestedOperationItem key={c.operationId} id={c.operationId} value={c} handleUpdate={this.handleUpdate} />
                                </TableRow>
                            ))}

                        </TableBody>
                    </Table>
                </div>
            </div>
            </div>
        );
    }
}

export default ClinicAdminOperationRequest;