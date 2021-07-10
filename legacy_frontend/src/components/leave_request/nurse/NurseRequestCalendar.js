import React from 'react'
import {NurseContext} from '../../../context/NurseContextProvider'
import axios from 'axios'
import FullCalendar from '@fullcalendar/react'
import dayGridPlugin from '@fullcalendar/daygrid'
import bootstrapPlugin from '@fullcalendar/bootstrap'
import interaction from "@fullcalendar/interaction"
import Moment from 'moment';
import '../../../main.scss' 
import LeaveRequestModal from '../LeaveRequestModal'
import LeaveRequestHelpModal from '../LeaveRequestHelpModal'

class NurseRequestCalendar extends React.Component {

    static contextType = NurseContext
    
    state = {
        appointments: [],
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
        let url = '/api/appointments/leave-request-appointments/nurse=' + this.context.nurse.id 
        axios.get(url).then((response) => {
            this.setState({
            appointments: response.data,
            loading: false
            })
        })

        axios.get('/api/nurses/nurse='+this.context.nurse.id+'/business-hours')
        .then(response => {
        this.setState({businessHours: response.data}, () => {
            console.log('business hours:', this.state.businessHours)
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
                events = {this.generateEventList()}
                eventLimit = {true}
                eventRender={this.handleEventRender}
                eventClick={this.handleEventClick}
                plugins={[ dayGridPlugin, bootstrapPlugin, interaction]} 
                  themeSystem = 'bootstrap' />

              {this.state.showRequestModal 
                  && <LeaveRequestModal modal={this.state.showRequestModal} 
                                        toggle={this.toggle}
                                        appointments={this.state.appointments}
                                        selectedDates={this.state.selectedDates}
                                        id={this.context.nurse.id}
                                        role='NURSE'/>}

              {this.state.showHelpModal && <LeaveRequestHelpModal modal={this.state.showHelpModal}
                                                                  toggle={this.toggleHelp}/>}

              </div>
          )
              }
    }
}

export default NurseRequestCalendar