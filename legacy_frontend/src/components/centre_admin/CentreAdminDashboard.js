import React, { Fragment } from 'react'
import DiagnosesOverview from './dashboard/diagnoses/DiagnosesOverview'
import MedicationOverview from './dashboard/medication/MedicationOverview'

class CentreAdminDashboard extends React.Component {

    render() {
        return(
            <div>
                <br/>
                <div class="row d-flex justify-content-center">
                    <div class='col-md-3'> 
                        <h3> Diagnoses. </h3>
                    </div>

                    <div class='col-md-3'> 
                        <h3> Medication. </h3>
                    </div>
                </div>

                <div class="row d-flex justify-content-center">
                    <div class='col-md-5'> 
                    <DiagnosesOverview/>
                    </div>

                    <div class='col-md-5'> 
                    <MedicationOverview/>
                    </div>
                </div>
                   {/* <div class="row d-flex justify-content-center">
                        <div class='col-md-3'>
                            <h3> Diagnoses. </h3>
                        </div>
                        </div>
                             <div class="row d-flex justify-content-center">
                                <div class='col-md-5'>
                            <DiagnosesOverview/>
                            </div>
                        </div>
                        
                        <div class="row d-flex justify-content-center"> 
                        <div class='col-md-3'>
                            <h3> Medication. </h3>
                            </div>
                            </div>
                            <div class="row d-flex justify-content-center">
                                <div class='col-md-5'>
                                    <MedicationOverview/>
                             </div>
                             </div> */}
            </div>
        )
    }
}

export default CentreAdminDashboard