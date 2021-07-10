import React from 'react'
import {DoctorContext} from '../../../context/DoctorContextProvider'
import axios from 'axios'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import bootstrapPlugin from '@fullcalendar/bootstrap'
import interaction from "@fullcalendar/interaction"
import Moment from 'moment';
import '../../../main.scss' 
import LeaveRequestModal from '../LeaveRequestModal'
import LeaveRequestHelpModal from '../LeaveRequestHelpModal'

class DoctorRequestCalendar extends React.Component {


    static contextType = DoctorContext
    
    state = {
        appointments: [],
        operations: [],
        businessHours: [],
        showRequestModal: false,
        selectedDates: {},
        event: {
            id: 0,
            title: "",
            start: new Date(),
            end: new Date(),
            patient: "",
            doctor: "",
            procedure: "",
            price: "",
            discount: "",
            status: "",
            patientInsurance: ""
        },
        loading: true,
        showHelpModal: false
    }

    toggle = () => {
        this.setState({ showRequestModal: !this.state.showRequestModal}, ()=>{this.props.update()}) //update the parent component to rerender request history
      }

      toggleHelp = () => {
        this.setState({showHelpModal: !this.state.showHelpModal})
      }

    componentDidMount() {
        let url = '/api/appointments/leave-request-appointments/doctor=' + this.context.doctor.id 
        axios.get(url).then((response) => {
            this.setState({
            appointments: response.data,
            loading: false
            })
        })
        axios.get('/api/operations/all-approved/doctor='+this.context.doctor.id)
        .then(response => {
          this.setState({operations: response.data})
        })
        axios.get('/api/doctors/doctor='+this.context.doctor.id+'/business-hours')
        .then(response => {
        this.setState({businessHours: response.data}, () => {
        })
        })
    }


    generateEventList = () => {
        let events = []
        let info = ''
        for(let i = 0; i < this.state.appointments.length; i++) {
            let appointment = this.state.appointments[i]

            info = appointment.roomName + ' ' + appointment.roomNumber
            let start = new Date(appointment.startDate).toISOString()
            let end = new Date(appointment.endDate).toISOString()

            let event = { 
              id: appointment.appointment_id,
              title: info, 
              start: start,
              end: end,
              color: '#ff9f89',
              allDay: true
            }
    
            events.push(event)
        }
        return events
    }

    generateOperationsList = () => {
      let events = []
      for(let i = 0; i < this.state.operations.length; i++) {
        let operation = this.state.operations[i]
  
        let start = new Date(operation.startDate).toISOString()
        let end = new Date(operation.endDate).toISOString()
        let color = '#4B0082' //purple
        let event = {
          title: operation.roomName + ' ' + operation.roomNumber,
          start: start,
          end: end,
          color: color,
          allDay: true
        }
        events.push(event)
      }
      return events
    }

    handleSelectedDates = (info) => {
        this.setState({showRequestModal: true, selectedDates: info}, () => { console.log('selected ' + info.startStr + ' to ' + info.endStr)})
    }

    handleHelpModal = () => {
      this.setState({showHelpModal: true})
    }


    render() {
        if(this.state.loading) {
            return (<div> Loading... </div>)
          } else {
              return (
                  <div className='demo-app-calendar'> 
                      <FullCalendar defaultView="dayGridMonth" 
                    customButtons = {
                      {
                        helpButton: {
                          text: "Help",
                          click: this.handleHelpModal
                      }}
                    }
                    header={{
                        left: 'helpButton',
                        center: "title",
                        right: "prev, next"
                    }}

                    businessHours = { 
                      this.state.businessHours
                    }
                    selectable={true}
    
                    selectAllow={ //restrikcija da se ne mogu selektovati datumi pre danas
                      function(selectInfo) {
                        return Moment().diff(selectInfo.start) <= 0
                      }
                    }
                    select={ //aktivira modal kojem prosledjuje datume koji su selektovani
                          this.handleSelectedDates
                    }
                    eventSources={
                      [
                        this.generateEventList(),
                        this.generateOperationsList()
                      ]
                    }
                    eventLimit = {true}
                    eventRender={this.handleEventRender}
                    eventClick={this.handleEventClick}
                    plugins={[ dayGridPlugin, bootstrapPlugin, interaction]} 
                      themeSystem = 'bootstrap' />
    
                  {this.state.showRequestModal 
                      && <LeaveRequestModal modal={this.state.showRequestModal} 
                                            toggle={this.toggle}
                                            appointments={this.state.appointments}
                                            operations={this.state.operations}
                                            selectedDates={this.state.selectedDates}
                                            id={this.context.doctor.id}
                                            role='DOCTOR'/>}
    
                  {this.state.showHelpModal && <LeaveRequestHelpModal modal={this.state.showHelpModal}
                                                                      toggle={this.toggleHelp}/>}
    
                  </div>
              )
                  }
    }
}

export default DoctorRequestCalendar