import React, {Component, Fragment}  from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import axios from 'axios';
import {Link, Redirect} from 'react-router-dom';
import Button from 'react-bootstrap/Button'

const sortTypes = {
    insurance_up: {
        fn: (a, b) => a.insuranceNumber - b.insuranceNumber
    },
    insurance_down: {
        fn: (a, b) => b.insuranceNumber - a.insuranceNumber
    },
    first_up: {
        fn: (a, b) => b.firstName.localeCompare(a.firstName)
    },
    first_down: {
        fn: (a, b) => a.firstName.localeCompare(b.firstName)
    },
    last_up: {
        fn: (a, b) => b.lastName.localeCompare(a.lastName)
    },
    last_down: {
        fn: (a, b) => a.lastName.localeCompare(b.lastName)
    },
    email_up: {
        fn: (a, b) => b.email.localeCompare(a.email)
    },
    email_down: {
        fn: (a, b) => a.email.localeCompare(b.email)
    },
    default: {
        fn: (a, b) => a
    }
};


class PatientList extends Component {

	state = {
        patients: [],
        filter: "",
        url: "",
        redirect: false,
        currentSort: 'default'
	}

	componentDidMount () {
		axios.get ('/api/nurses/patientList')
		.then (response => {
			this.setState ({
				patients: response.data
			})
        })
    }

    onSortChange = (name) => {
		const { currentSort } = this.state;
        let nextSort;

        if(name === 'insurance') {
            if (currentSort === 'insurance_down') nextSort = 'insurance_up';
            else if (currentSort === 'insurance_up') nextSort = 'default';
            else nextSort = 'insurance_down';
        } else if(name === 'firstName') {
            if (currentSort === 'first_down') nextSort = 'first_up';
            else if (currentSort === 'first_up') nextSort = 'default';
            else nextSort = 'first_down';
        } else if(name === 'lastName') {
            if (currentSort === 'last_down') nextSort = 'last_up';
            else if (currentSort === 'last_up') nextSort = 'default';
            else nextSort = 'last_down';
        } else {
            if (currentSort === 'email_down') nextSort = 'email_up';
            else if (currentSort === 'email_up') nextSort = 'default';
            else nextSort = 'email_down';
        }

		this.setState({
			currentSort: nextSort
		}, () => {
            this.renderArrowFirst()
            this.renderArrowLast()
            this.renderArrowEmail()
            this.renderArrowInsurance()
        });
	};

    renderArrowInsurance = () => {
        if(this.state.currentSort === 'insurance_up') {
            return <i class="fas fa-long-arrow-alt-up fa-lg"></i>
        } else if(this.state.currentSort === 'insurance_down') {
            return <i class="fas fa-long-arrow-alt-down fa-lg"></i>
        } else {
            return ''
        }
    }

    renderArrowFirst = () => {
        if(this.state.currentSort === 'first_up') {
            return <i class="fas fa-long-arrow-alt-up fa-lg"></i>
        } else if(this.state.currentSort === 'first_down') {
            return <i class="fas fa-long-arrow-alt-down fa-lg"></i>
        } else {
            return ''
        }
    }

    renderArrowLast = () => {
        if(this.state.currentSort === 'last_up') {
            return <i class="fas fa-long-arrow-alt-up fa-lg"></i>
        } else if(this.state.currentSort === 'last_down') {
            return <i class="fas fa-long-arrow-alt-down fa-lg"></i>
        } else {
            return ''
        }
    }

    renderArrowEmail = () => {
        if(this.state.currentSort === 'email_up') {
            return <i class="fas fa-long-arrow-alt-up fa-lg"></i>
        } else if(this.state.currentSort === 'email_down') {
            return <i class="fas fa-long-arrow-alt-down fa-lg"></i>
        } else {
            return ''
        }
    }
    handleChange = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        })

        console.log("filter: " + this.state.filter)
    }

    filterSubmit = (event) => {
        event.preventDefault()
        axios.post('/api/nurses/filterPatients', {
            filterResults: this.state.filter
        }).then(response => {
            this.setState({
                patients: response.data
            })
        })
    }

    handleClick = (insurance) => {
        let profileUrl = '/profile/' + insurance
        this.setState({url: profileUrl, redirect: true})
    }
  

	render () {

        let size = this.state.patients.length

        if(this.state.redirect == true) 
        {
            return <Redirect exact to = {this.state.url}/>
        }

		return (
			<div>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-11'>
                            <table class="table table-hover">
                                <thead> 
                                    <tr> 
                                    <td/>
                                    <td/>
                                    <td/>
                                        <td > 
                                        <div class="input-group"> <input type = "text" class="form-control"  placeholder="Filter patients..." name = "filter" onChange = {this.handleChange}/> 
                                        <Button class="primary" onClick = {this.filterSubmit}><i class="fas fa-search"></i> 
                                        </Button>
                                        </div>
                                        </td>
                                        <td> </td>
                                   
                                    </tr>
                                    <tr>
                                        <th class='text-success cursor-pointer' onClick={() => this.onSortChange('firstName')}> {this.renderArrowFirst()} <i class="fas fa-user-circle"></i>  First name</th>
                                        <th class='text-success cursor-pointer' onClick={() => this.onSortChange('lastName')}> {this.renderArrowLast()} <i class="fas fa-user-circle"></i>  Last name</th>
                                        <th class='text-success cursor-pointer' onClick={() => this.onSortChange('email')}> {this.renderArrowEmail()} <i class="fas fa-envelope-open"></i> Email</th>
                                        <th class='text-success cursor-pointer' onClick={() => this.onSortChange('insurance')}> {this.renderArrowInsurance()} <i class="fas fa-file-medical"></i> Insurance number</th>
                                        <td> </td>
                                    </tr>
                                </thead>
                                <tbody>
                                    {size > 0 ?  [...this.state.patients].sort(sortTypes[this.state.currentSort].fn).map (row => (
                                              <tr style={{cursor:"pointer"}} onClick = {() => this.handleClick(row.insuranceNumber)}>
                                              <td>{row.firstName}</td>
                                              <td>{row.lastName}</td>
                                              <td>{row.email}</td>
                                              <td>{row.insuranceNumber}</td>
                                              <td></td>
                                           </tr>
                                        
                                    )) : <tr>
                                        <td colSpan = "5"> <h3> No results found.</h3> </td> 
                                       
                                    </tr> }
                                </tbody>
                            </table>
                    </div>
                </div>
			</div>
		);
	}

}

export default PatientList;