import React from 'react'
import Button from 'react-bootstrap/Button'
import HealthRecord from './HealthRecord'

class ShowHealthRecord extends React.Component {

    render() {
        return(
            <div> 
                 <div class="row d-flex justify-content-center">
                    <div class='col-md-6'> 
                    <h2> {this.props.data.patient} </h2>
                    <br/>
                    </div>
                </div>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-11'> 
                    <HealthRecord health_record = {this.props.health_record}/>
                    <Button className = "btn btn-success" onClick = {this.props.toggleUpdate}> Update</Button>
                    </div>
                </div>
            </div>
        )
    }
}

export default ShowHealthRecord