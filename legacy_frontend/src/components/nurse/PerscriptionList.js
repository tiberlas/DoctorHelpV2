import React, {Component, Fragment}  from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import axios from 'axios';
import Button from 'react-bootstrap/Button'
import {NurseContext} from '../../context/NurseContextProvider';

class PerscriptionList extends Component {
    state = {
        perscriptions: []
    }

    static contextType = NurseContext

    componentDidMount () {
		axios.get('/api/nurses/pendingPerscriptions/nurse='+this.context.nurse.id)
		.then (response => {
			this.setState ({
				perscriptions: response.data
			})
        })
    }

    handleClick = (event) => {
        event.preventDefault()
        let url = '/api/nurses/signOff/' + this.context.nurse.id + '/' + event.target.value
        axios.put (url)
		.then (response => {
            console.log("response data" + response.data)
			this.handleUpdate(response.data.id)
        })
    }

    handleUpdate = (key) => {
        const items = this.state.perscriptions.filter(item => item.perscriptionId !== key);

        this.setState({ perscriptions: items});    
    }

    generatePerscriptionRows(row) {
                const meds = row.medicationList.map(item => item.medicationName)
                let medString = ""
                for(let i = 0; i < meds.length; i++) {
                    medString += meds[i] + ","
                }
                medString = medString.substring(0, medString.length - 1)
        return (
            <tr>
                
                <td>{row.doctor}</td>
                <td>{row.patient}</td>
                <td>{row.diagnosis}</td>
                <td>{medString}</td>
                <td><Button name = "perscriptionId" value = {row.perscriptionId} class="primary" onClick = {this.handleClick}> Sign off</Button></td>
             </tr>
        )
    }


    render() {

         let size = this.state.perscriptions.length
		return (
			<div>
                <br/>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-11'>
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th class='text-success'><i class="fas fa-user-md"></i> Doctor</th>
                                        <th class='text-success'><i class="fas fa-user-injured"></i>  Patient</th>
                                        <th class='text-success'><i class="fas fa-stethoscope"></i> Diagnosis</th>
                                        <th class='text-success'><i class="fas fa-capsules"></i>  Perscribed medication</th>
                                        <td></td>
                                    </tr>
                                </thead>
                                <tbody>
                                    {size > 0 ? this.state.perscriptions.map (row => (
                                            this.generatePerscriptionRows(row)
                                    )) : <tr>
                                        <td colSpan="5"> <h3> No unsigned perscriptions yet. </h3>  </td>
                                         </tr>}
                                </tbody>
                            </table>
                    </div>
                </div>
			</div>
		);

    }



}


export default PerscriptionList