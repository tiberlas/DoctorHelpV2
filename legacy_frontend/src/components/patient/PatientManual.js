import React, {Component, Fragment} from 'react';


class PatientManual extends Component {

    render () {
        return (
            <div>
                <div class="row d-flex justify-content-left">
					<div class='col-md-2'></div>
                        <div>
                        <br />
                        <h1>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Manual</h1>
                        <br />
                        <br />
                        <br />
                        <p>
                            Click the name of a doctor or clinic on any screen you see it and you <br />
                            will be redirected to their profile
                        </p>
                        <br />
                        <h4>&emsp;Requesting Custom Appointments</h4>
                        <br />
                        <p>
                            To create a custom appointment, clic the Clinic tab. <br />
                            Once there, you will be presented with a list of all the clinics in our System. <br />
                            In the top right corner, you will see a Search button. Clic on it to open the Search card. <br />
                            Once you have chosen a procedure type, the price for that procedure type will appear in the Clinics table. <br />
                            A procedure type may have different costs in different clinics. <br/>
                            Once you have selected a procedure type and a date (and an optional number of other filters), <br />
                            A Reserve link will appear next to the clinics which match your search criteria. <br />
                            Click on it to choose a doctor and time of day for your appointment. <br />
                            Once you have chosen a time of day for your doctor of choice, click Request to confirm your appointment request. <br />
                            After that, an admin will accept or decline your request, and you will recieve an email notification about it. 
                        </p>
                        <br />
                        <h4>&emsp;View Pending Appointments</h4>
                        <br />
                        <p>
                            Mouse over the appointmetns tab and click the Pending appointments option. <br />
                            You will be taken to a page that shows all your pending appointments. <br />
                            The status column shows an appointmets status. Statuses have the following meainngs: <br /><br />
                            <strong>Requested </strong> - You have submitted a request for this appointment. <br />
                            &emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;One of our admins will accept or decline it soon, and inform you by email. <br /><br />
                            <strong>Perscribed </strong> - A doctor has perscribed an appointment for you, but one of our admins must still confirm it. <br />
                            &emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;You will be notified about it by email. <br /><br />
                            <strong>Approved </strong> - One of our admins has approved your request, but it may have some slight changes, like precise time and doctor. <br />
                            &emsp;&emsp;&emsp;&emsp;&emsp;&nbsp;&nbsp;If you find these changes acceptable, confirm. <br />
                            <strong>Confirmed </strong> - You have confirmed you'll come to this appointment. <br /><br />
                            If there are at least 24 hours remaining before an appointment, you will have the option to cancel it. <br />
                        </p>
                        <br />
                        <h4>&emsp;View Patient History</h4>
                        <br />
                        <p>
                            Mouse over the appointmetns tab and click the Patient history option. <br />
                            Here you will see a list of all appointments and operations you have had within our system so far. <br />
                            If you want to find a speciffic appointment, clic on the Search button in the top right to open the search menu. <br />
                        </p>
                        <br />
                        <h4>&emsp;Requesting Predefined Appointments</h4>
                        <br />
                        <p>
                            Mouse over the appointmetns tab and click the Patient history option. <br />
                            Here you will see a preview  of all predefined appointments in our system. <br />
                            If you reserve a predefined appointment, you won't have to wait for an admin to approve it. <br />
                            Whats more, you'll probably get a sliht discount on appointmenr price. <br />
                            Just click the Reserve button to reserve a predefined appointment,  <br />
                            Or click the Search button to find a speciffic one. <br />
                        </p>
                        <br />
                        <h4>View Health Record</h4>
                        <br />
                        <p>
                            Click the Health record tab to see the information contained in your health record. <br/>
                        </p>
                        <br />
                        <h4>&emsp;View Personal Profile</h4>
                        <br />
                        <p>
                            Here you can view or change your personal information. 
                        </p>
                        <br />
                    </div>
                </div>
            </div>
        );
    }

}


export default PatientManual;