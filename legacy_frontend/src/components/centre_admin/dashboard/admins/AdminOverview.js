import React, { Fragment, Component } from 'react'
import axios from 'axios'
import Button from 'react-bootstrap/Button'
import AddAdminModal from './AddAdminModal';
import '../../../../customRadioButton.css';

const sortTypes = {
    name_up: {
        fn: (a, b) => b.email.localeCompare(a.email)
    },
    name_down: {
        fn: (a, b) => a.email.localeCompare(b.email)
    },
    type_up: {
        fn: (a, b) => b.procedureTypeName.localeCompare(a.procedureTypeName)
    },
    type_down: {
        fn: (a, b) => a.procedureTypeName.localeCompare(b.procedureTypeName)
    },
    number_up: {
        fn: (a, b) => a.number - b.number
    },
    number_down: {
        fn: (a, b) => b.number - a.number
    },
    date_up: {
        fn: (a, b) => b.firstFreeSchedule.localeCompare(a.firstFreeSchedule)
    },
    date_down: {
        fn: (a, b) => a.firstFreeSchedule.localeCompare(b.firstFreeSchedule)
    },
    default: {
        fn: (a, b) => a
    }
}


export class AdminOverview extends Component {
    
    state = {
        centreAdmins: [],
        clinicAdmins: [],
        currentSort: 'default',
        showAddModal: false,
        showCentreAdmins: true,
        showClinicAdmins: false,
        adminType: 'CENTRE'
    }

    componentDidMount() {
        axios.get('/api/centreAdmins/all').then( response => {
            this.setState({centreAdmins: response.data}, () => {
                axios.get('/api/clinicAdmins/all').then(response => {
                    this.setState({clinicAdmins: response.data})
                })
            })
        })

    }


    
    onSortChange = (name) => {
		const { currentSort } = this.state;
        let nextSort;

        if(name === 'email') {
            if (currentSort === 'name_down') nextSort = 'name_up';
            else if (currentSort === 'name_up') nextSort = 'default';
            else nextSort = 'name_down';
        }

		this.setState({
			currentSort: nextSort
		}, () => {
            this.renderArrowName()
        });
    };


    renderArrowName = () => {
        if(this.state.currentSort === 'name_up') {
            return <i class="fas fa-long-arrow-alt-up fa-lg"> </i>
        } else if(this.state.currentSort === 'name_down') {
            return <i class="fas fa-long-arrow-alt-down fa-lg"> </i>
        } else {
            return ''
        }
    }

    toggle = () => {
        this.setState({showAddModal: !this.state.showAddModal})
    }

    handleCheck = (event) => {
        this.setState( {
            [event.target.name]: event.target.value
        })
    }


    update = () => {
        if(this.state.adminType === 'CENTRE') {
            axios.get('/api/centreAdmins/all').then( response => {
                this.setState({centreAdmins: response.data, admins: response.data, showAddModal: false})
            })
        } else if(this.state.adminType === 'CLINIC') {
            axios.get('/api/clinicAdmins/all').then( response => {
                this.setState({clinicAdmins: response.data, admins: response.data, showAddModal: false})
            })
        }
    }

    render() {
        let i = 0
        return (
            <Fragment> 
                <br/>
            <div class="row d-flex justify-content-center">
                <div class='col-md-3'>
                    <h3> Administrators.</h3>
                    </div>
                </div>
            <div class="row d-flex justify-content-center">
                <div class='col-md-11'>
               <div >
                   <br/>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th class="text-success cursor-pointer" onClick={() => this.onSortChange('email')}>{this.renderArrowName()} <i class="fa fa-envelope-open"></i> Email</th>
                            <th class="text-success"><i class="fas fa-user-circle"></i> First name</th>
                            <th class="text-success"><i class="fas fa-user-circle"></i> Last name</th>
                            {this.state.adminType === 'CLINIC' && <th class="text-success"><i class="fas fa-clinic-medical"></i> Clinic</th>}
                            <th colSpan = "2">
                            <ul>
								<li> 
                            
                            <input required
                                type="radio" 
                                name="adminType"
                                value="CENTRE"
                                id="customRadio1"
                                checked={this.state.adminType === "CENTRE"}
                                onChange={this.handleCheck}
                            />
                        <label for="customRadio1" class="text-white"> Centre admins
                        </label>
                        <div class="check"></div>
                        </li> &nbsp;
                        <li> 
                        
                            <input required
                                type="radio" 
                                name="adminType"
                                value="CLINIC"
                                id="customRadio2"
                                checked={this.state.adminType === "CLINIC"}
                                onChange={this.handleCheck}
                            />
                            <label for="customRadio2" class="text-white">   Clinic admins
                        </label>
                        <div class="check"></div>
                            </li>
                            </ul>
                            </th>

                            <th class="text-success"><button button class='btn btn-success rounded-circle float-right mr-5'  onClick={this.toggle}><i class="fas fa-plus"></i></button></th>
                        </tr>
                    </thead>
                    <tbody >
                        {this.state.adminType === 'CENTRE' && this.state.centreAdmins.sort(sortTypes[this.state.currentSort].fn).map (c => (
                            <tr className={(++i)%2? `table-dark` : ``} >
                                <td colspan="1" class='text text-white'>&nbsp;{c.email}</td>
                                <td class='text text-white'>&nbsp;{c.firstName}</td>
                                <td class='text text-white'>&nbsp;{c.lastName}</td>
                              <td></td>
                              <td/>
                              <td></td>
                            </tr>
                        ))  }

                        {this.state.adminType === 'CLINIC' && this.state.clinicAdmins.sort(sortTypes[this.state.currentSort].fn).map (c => (
                            <tr className={(++i)%2? `table-dark` : ``} >
                                <td colspan="1" class='text text-white'>&nbsp;{c.email}</td>
                                <td class='text text-white'>&nbsp;{c.firstName}</td>
                                <td  class='text text-white'>&nbsp;{c.lastName}</td>
                              <td  class='text text-white'>&nbsp;{c.clinicName}</td>
                              <td></td>
                              <td></td>
                              <td></td>
                            </tr>
                        ))  }

                    </tbody>
                    
                </table>
                </div>
                </div>

                {this.state.showAddModal && <AddAdminModal modal={this.state.showAddModal}
                                    toggle={this.toggle}
                                    update={this.update}/>}
                
               </div>
               
            </Fragment>)
    }
}