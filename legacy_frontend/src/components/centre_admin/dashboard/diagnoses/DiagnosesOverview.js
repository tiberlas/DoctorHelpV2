import React, { Fragment } from 'react'
import axios from 'axios'
import AddDiagnosisModal from './AddDiagnosisModal';



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


class DiagnosesOverview extends React.Component {

    state = {
        diagnoses: [],
        currentSort: 'default',
        showAddModal: false
    }

    componentDidMount() {
        axios.get('/api/diagnoses/all').then( response => {
            this.setState({diagnoses: response.data})
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
        axios.get('/api/diagnoses/all').then( response => {
            this.setState({diagnoses: response.data, showAddModal: false})
        })
    }

    handleDelete = (diagnosis) => {
        if(diagnosis.reserved)
            return

        axios.delete('/api/diagnoses/delete='+diagnosis.id).then(this.update)
    }

    render() {
        let i = 0
        return (
           
               <div >
                   <br/>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th class="text-success cursor-pointer" onClick={() => this.onSortChange('name')}>{this.renderArrowName()} <i class="fas fa-stethoscope"></i>  Name</th>
                            <th class="text-success"><i class="far fa-sticky-note"></i>  Description</th>
                            <th class="text-success"><button button class='btn btn-success rounded-circle float-right mr-5'  onClick={this.toggle}><i class="fas fa-plus"></i></button></th>
                        </tr>
                    </thead>
                    <tbody>
                        {/* <div class="scrollit">  */}
                        {this.state.diagnoses.sort(sortTypes[this.state.currentSort].fn).map (c => (
                            <tr className={(++i)%2? `table-dark` : ``} >
                                <td class='text text-white'>&nbsp;{c.name}</td>
                                <td class='text text-white'>{c.description}</td>
                                <td><button class='btn btn-danger' onClick={() => this.handleDelete(c)} disabled={c.reserved}>Delete</button></td>
                            </tr>
                        ))  }
                        {/* </div> */}
                    </tbody>
                    
                </table>
                

                {this.state.showAddModal && <AddDiagnosisModal modal={this.state.showAddModal}
                                    toggle={this.toggle}
                                    update={this.update}/>}
                
                </div>
        )
    }
}

export default DiagnosesOverview