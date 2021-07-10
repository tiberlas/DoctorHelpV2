import React, {Fragment} from 'react'

class HealthRecord extends React.Component {

   
    state = { //komponenta health-record koja kao props prima PatientHealthRecordDTO sa beka
            health_record: {
                firstName: "",
                lastName: "",
                height: "",
                weight: "", 
                diopter: "",
                bloodType: "",
                birthday: "",
                allergyList: [] 
            },
            loading: true
    }
   

    componentWillReceiveProps(props) {
        this.setState({
            health_record: {
                firstName: props.health_record.firstName,
                lastName: props.health_record.lastName,
                height: props.health_record.height,
                weight: props.health_record.weight,
                diopter: props.health_record.diopter,
                bloodType: props.health_record.bloodType,
                birthday: new Date(props.health_record.birthday),
                allergyList: props.health_record.allergyList
            }
        }, ()=>{
            this.setState({loading: false})
        })
    }

    componentWillUpdate(prevProps, prevState) {
        if(prevProps.health_record !== this.props.health_record) {
            this.setState({
                health_record: {
                    height: prevProps.health_record.height,
                    weight: prevProps.health_record.weight,
                    diopter: prevProps.health_record.diopter,
                    bloodType: prevProps.health_record.bloodType,
                    birthday: new Date(prevProps.health_record.birthday),
                    allergyList: prevProps.health_record.allergyList
                }
            })
         }
    }


    heightDisplay = () => {
        return this.state.health_record.height
    }

    weightDisplay = () => {
        return this.state.health_record.weight
    }

    diopterDisplay = () => {
        return this.state.health_record.diopter
    }

    allergyDisplay = () => {
        if (this.state.health_record.allergyList === undefined) {
            return;
        }
        let allergies = ""
        for(let i = 0; i < this.state.health_record.allergyList.length; i++) {
            allergies += this.state.health_record.allergyList[i]
            if(i !== this.state.health_record.allergyList.length - 1) {
                allergies += ', '
            }
            
        }
        return allergies
    }


    ageDisplay = () => {

        let thisYear = new Date().getFullYear()
        let bornYear = new Date(this.state.health_record.birthday).getFullYear()

        let age = Math.abs(thisYear - bornYear).toString()
        let born = new Date(this.state.health_record.birthday).toLocaleDateString("en-US")

        return age + ' - (born ' + born + ')'
    }

    bloodTypeDisplay = () => {
        
        if ((this.state.health_record.bloodType === undefined) || (this.state.health_record.bloodType === null)) {
            return;
        }
        let bloodType = ""

        bloodType = this.state.health_record.bloodType.replace('_', ' ')
        bloodType = bloodType.substr(0, 1) + bloodType.substr(1, bloodType.length).toLowerCase()
        return bloodType
    }


    render() {
        return (
            <Fragment> 
                {this.state.loading ? <div> Loading... </div> : 
                <table class="table table-hover">
                <tbody>
                    <tr>
                        <th scope="row"><i class="fas fa-birthday-cake"></i> Age:</th>
                            <td>{this.ageDisplay()}</td>
                    </tr>
                    <tr>
                        <th scope="row"><i class="fas fa-arrows-alt-v"></i> Height: </th>
                        <td>{this.heightDisplay()} m</td>
                    </tr>
                    <tr>
                        <th scope="row"><i class="fas fa-weight"></i> Weight:</th>
                            <td>{this.weightDisplay()} kg</td>
                    </tr>
                    <tr>
                        <th scope="row"><i class="fas fa-glasses"></i> Diopter:</th>
                            <td>{this.diopterDisplay()}</td>
                    </tr>
                    <tr>
                        <th scope="row"><i class="fas fa-fan"></i> Allergies:</th>
                            <td>{this.allergyDisplay()}</td>
                    </tr>
    
                    <tr>
                        <th scope="row"><i class="fas fa-atom"></i> Blood type:</th>
                            <td>{this.bloodTypeDisplay()}</td>
                    </tr>
                    
                </tbody>
            </table>}
            
        </Fragment>
        )
    }
}

export default HealthRecord