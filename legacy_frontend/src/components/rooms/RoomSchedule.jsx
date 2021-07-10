import React from 'react';
import FullCalendar from '@fullcalendar/react';import dayGridPlugin from '@fullcalendar/daygrid';
import timeGridPlugin from "@fullcalendar/timegrid";
import bootstrapPlugin from '@fullcalendar/bootstrap';
import interaction from "@fullcalendar/interaction";
import axios from 'axios';
import '../../main.scss' // webpack must be configured to do this
import RoomModalView from './RoomModalView';

class RoomSchedule extends React.Component {

    state = {
        appointments: [],
        event: {
          title: '',
          startTime: '',
          endTime: ''
        },
        modalShow: false
    }

  componentDidMount() {
    let path_parts = window.location.pathname.split('schedule/')
    axios.get(`/api/rooms/room=${path_parts[1]}/schedule`)
        .then((response) => {
            this.setState({appointments: response.data})
        })
  }

  generateEvents = () => {
    let events = []
    for(let i = 0; i < this.state.appointments.length; i++) {
        let event = {
            appointemntId: this.state.appointments[i].appointmentId,
            operationId: this.state.appointments[i].operationId,
            title: this.state.appointments[i].title,
            start: this.state.appointments[i].date +'T'+ this.state.appointments[i].startTime,
            end: this.state.appointments[i].date +'T'+ this.state.appointments[i].endTime
        }
        events.push(event)
    }

    return events
  }

  handleEventClick = ({ event, el }) => {
    let info = {
        title: event.title,
        startTime: event.start,
        endTime: event.end
    }
    
    this.setState({
                event: info
    }, () => {
      this.setState({modalShow: true })
    })
  }

  onHide = () => {
    this.setState({modalShow: false})
  }


  render() {
      return (
        <div className='demo-app-calendar'>
          <FullCalendar defaultView="dayGridMonth" 
            header={{
              left: "",
              center: "title",
              right: "prev, next"
            }}
            buttonText={
              {
                prev: '<',
                next: '>'
              }
            } 
            titleFormat={
              {
              year: 'numeric'
              }
            }
            selectable={true}
            eventClick={this.handleEventClick} 
            events = {this.generateEvents()}
            plugins={[ dayGridPlugin, timeGridPlugin, bootstrapPlugin, interaction]} 
            themeSystem = 'bootstrap' />

            <RoomModalView 
              reservedRoom={this.state.event}
              show={this.state.modalShow}
              onHide={this.onHide} />
        </div>
      )
  }

}
export default RoomSchedule