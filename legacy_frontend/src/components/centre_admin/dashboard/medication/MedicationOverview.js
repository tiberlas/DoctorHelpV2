import React, {Component, Fragment } from 'react'
import axios from 'axios'

import AddMedicationModal from './AddMedicationModal';

const sortTypes = {
    name_up: {
        fn: (a, b) => b.name.localeCompare(a.name)
    },
    name_down: {
        fn: (a, b) => a.name.localeCompare(b.name)
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

class MedicationOverview extends Component {

    state={
        medication: [],
        currentSort: 'default',
        showAddModal: false
    }


    componentDidMount() {
        axios.get('/api/medication/all').then( response => {
            this.setState({medication: response.data})
        })
    }



    onSortChange = (name) => {
		const { currentSort } = this.state;
        let nextSort;

        if(name === 'name') {
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

    update = () => {
        axios.get('/api/medication/all').then( response => {
            this.setState({medication: response.data, showAddModal: false})
        })
    }

    handleDelete = (medication) => {
        if(medication.reserved)
            return

        axios.delete('/api/medication/delete='+medication.id).then(this.update)
    }


    render()
    {
        let i = 0
        return(
            <Fragment>
                <div>
                    <br/>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th class="text-success cursor-pointer" onClick={() => this.onSortChange('name')}>{this.renderArrowName()} <i class="fas fa-capsules"></i>  Name</th>
                            <th class="text-success"><i class="fas fa-sticky-note"></i> Description</th>
                            <th class="text-success"><button button class='btn btn-success rounded-circle float-right mr-5'  onClick={this.toggle}><i class="fas fa-plus"></i></button></th>
                        </tr>
                    </thead>
                
                    <tbody >
                        {this.state.medication.sort(sortTypes[this.state.currentSort].fn).map (c => (
                            <tr className={(++i)%2? `table-dark` : ``} >
                                <td class='text text-white'>&nbsp;{c.name}</td>
                                <td class='text text-white'>{c.description}</td>
                                <td><button class='btn btn-danger' onClick={() => this.handleDelete(c)} disabled={c.reserved}>Delete</button></td>
                            </tr>
                        ))  }

                    </tbody>
                    
                </table>
                </div>

                {this.state.showAddModal && <AddMedicationModal modal={this.state.showAddModal}
                                    toggle={this.toggle}
                                    update={this.update}/>}
                
            </Fragment>
        )
    }
}

export default MedicationOverview