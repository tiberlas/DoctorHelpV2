import React, { Component } from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import OperationItem from '../operation/OperationItem.jsx';
import axios from 'axios';

class DoctorRequestedOperations extends Component {
    state = { 
        operations: []
    }

    componentDidMount() {
        axios.get('/api/doctors/schedules/operation/requested')
            .then(response => {
                this.setState({operations: response.data})
            })
    }

    handleUpdate = (key) => {
        const items = this.state.operations.filter(item => item.id !== key);
        this.setState({ operations: items});
    }

    render() { 
        let i = 0;
        return (
                <div class='row d-flex justify-content-center'>
                <div class='col-md-7'> 
                    <br/>
                    <h4>List of requested operations</h4>
                    <br/>
                    <Table class="table table-hover ">
                        <TableHead class="table-active">
                            <TableRow class="table-active">
                                <TableCell class="text-success cursor-pointer" >date</TableCell>
                                <TableCell class="text-success cursor-pointer" >procedure</TableCell>
                                <TableCell class="text-success">patien</TableCell>
                                <TableCell class="text-success cursor-pointer" >first doctor</TableCell>
                                <TableCell class="text-success cursor-pointer" >second doctor</TableCell>
                                <TableCell class="text-success cursor-pointer" >third doctor</TableCell>
                                <TableCell class="text-success cursor-pointer" >status</TableCell>
                                <TableCell></TableCell>
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {this.state.operations.map (c => (
                                <TableRow className={(++i)%2? `table-dark` : ``} >
                                    <OperationItem key={c.id} id={c.id} value={c} handleUpdate={this.handleUpdate}/>
                                </TableRow>
                            )) }    
                        </TableBody>
                    </Table>
                </div>
                </div> );
    }
}
 
export default DoctorRequestedOperations;