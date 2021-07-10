import React from 'react'
import Button from 'react-bootstrap/Button'
import axios from 'axios'

class UpdateHealthRecord extends React.Component {

    state = { //predefinisan state je ono sto je i bilo ranije u health recordu
       
        height: "",
        weight: "",
        diopter: "",
        bloodType: "",
        allergy: "",
        allergyList: [],
        error: false,
        errorAllergy: false
    }


    componentWillReceiveProps(props) {
        this.setState({
                height: props.health_record.height,
                weight: props.health_record.weight,
                diopter: props.health_record.diopter,
                bloodType: props.health_record.bloodType,
                allergyList: props.health_record.allergyList,
                allergy: props.health_record.allergyList.join(',')
        })
    }

    handleChange = (e) => {
        console.log("event" + e.target.name + " " + e.target.value)
        this.setState({
            [e.target.name]: e.target.value
        }, ()=>{this.validate()})
    }


    validate = () => { //validira unos alergija
        this.setState({error: false, errorAllergy: false})
        let allergyList = []
        let regex = new RegExp("[^a-zA-Z0-9\s:]$") //glupav regex koji proverava da li ima upitnika ili nesto u sebi sto nisu cifre i karakteri
        allergyList = this.state.allergy.trim().split(',')
        for(let i=0; i < allergyList.length; i++) {
            if(allergyList[i].trim() === "" || regex.test(allergyList[i])) {
                this.setState({error: true, errorAllergy: true})
                break
            }
        }

    }



    handleSubmit = (event) => {
        event.preventDefault()

        if(this.state.error)
            return

        let record = {
            allergyList: this.state.allergy.split(','),
            weight: parseFloat(this.state.weight),
            height: parseFloat(this.state.height),
            diopter: parseFloat(this.state.diopter),
            bloodType: this.state.bloodType.replace(' ', '_').toUpperCase()
        }

        let url = 'http://localhost:8080/api/healthRecord/update/insurance='+this.props.data.patientInsurance
        axios.put(url, {
            weight: record.weight,
            height: record.height,
            diopter: record.diopter,
            bloodType: record.bloodType,
            allergyList: record.allergyList
        }).then(this.props.update(record))
    }


   
    generateSelect = () => {
        let items = []
        items.push(<option value="">Select a blood type</option>)
        items.push(<option value="O positive">O+</option>)
        items.push(<option value="O negative">O-</option>)
        items.push(<option value="A positive">A+</option>)
        items.push(<option value="A negative">A-</option>)
        items.push(<option value="B positive">B+</option>)
        items.push(<option value="B negative">B-</option>)
        items.push(<option value="AB positive">AB+</option>)
        items.push(<option value="AB negative">AB-</option>)
        return items
    }


    heightDisplay = () => {
        return this.state.height
    }

    weightDisplay = () => {
        return this.state.weight
    }

    diopterDisplay = () => {
        return this.state.diopter
    }

    allergyDisplay = () => {

        let allergies = ""
        for(let i = 0; i < this.state.allergyList.length; i++) {
            allergies += this.state.allergyList[i]
            if(i !== this.state.allergyList.length - 1) {
                allergies += ', '
            }
            
        }
        return allergies
    }

    bloodTypeDisplay = () => {
        let bloodType = ""

        bloodType = this.state.bloodType.replace('_', ' ')
        bloodType = bloodType.substr(0, 1) + bloodType.substr(1, bloodType.length).toLowerCase()
        return bloodType
    }

    render() {
        return (
            <div>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-6'> 
                    <h2> {this.props.data.patient} </h2>
                    </div>
                    </div>

                    <div class="row d-flex justify-content-center">
                    <div class='col-md-11'> 
                    <br/>
                    <form onSubmit = {this.handleSubmit}>
                    <div class="table-responsive">
                    <table class="table table-hover">
                        <tbody>
                            <tr>
                                <th scope="row">Height: </th>
                                <td><input name="height" type="number" step="any" defaultValue = {this.heightDisplay()} onChange={this.handleChange}/></td>
                            </tr>
                            <tr>
                                <th scope="row">Weight:</th>
                                    <td><input name="weight" type="number" step="any" defaultValue={this.weightDisplay()} onChange={this.handleChange}/></td>
                            </tr>
                            <tr>
                                <th scope="row">Diopter:</th>
                                    <td><input name="diopter" type="number" step="any" defaultValue={this.diopterDisplay()} onChange={this.handleChange}/></td>
                            </tr>
                            <tr>
                                <th scope="row">Allergies:</th>
                                    <td>
                                        <input id="allergyInput" name="allergy" type="text" 
                                    defaultValue={this.allergyDisplay()} 
                                    onChange={this.handleChange}/>
                                    {!this.state.errorAllergy ? <div> <br/><br/> </div>
                                        :  <div class="invalid-feedback d-block"> Invalid allergy format. <br/>Allergies are seperated by a comma.</div>}
                                    </td>
                                
                            </tr>

                            <tr>
                                <th scope="row">Blood type:</th>
                                    <td>
                                        <select id="selectBloodType" name="bloodType" onChange={this.handleChange}>
                                            {this.generateSelect()}
                                        </select>
                                    </td>
                            </tr>
                            
                        </tbody>
                    </table>
                    </div>
                    <div>
                    <button type='button' class = "btn btn-secondary" onClick = {this.props.toggleUpdate}> Back</button>
                    &nbsp;&nbsp;
                    <input type = "submit" value = "Confirm" className = "btn btn-success" className={`btn btn-success ${this.state.error ? 'disabled': ''}`}/>
                    </div>
                    </form>
                    </div>
                    </div>
                </div>
        )
    }
}

export default UpdateHealthRecord