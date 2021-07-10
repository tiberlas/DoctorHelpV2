import React, { Component, Fragment } from 'react';
import TableCell from '@material-ui/core/TableCell';

class PatientItem extends Component {

    render() { 
        return (
            <Fragment>
                <td class='text-white'>{this.props.value.firstName}</td>
                <td class='text-white'>{this.props.value.lastName}</td>
                <td class='text-white'>{this.props.value.email}</td>
                <td class='text-white'>{this.props.value.insuranceNumber}</td>
                <td></td>
            </Fragment>
         );
    }
}
 
export default PatientItem;