import React, { Fragment } from "react"
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';



class LeaveRequestHistory extends React.Component {

    state = {
        requests: []
    }
    
    componentWillReceiveProps(props) {
        this.setState({requests: props.requests})
    }

    displayType = (request) => {
        if(request.leaveType === 'ANNUAL') {
            return 'Annual'
        } else if(request.leaveType === 'PERSONAL') {
            return 'Personal'
        }
    }

    displayNote = (request) => {
        if(request.note.trim() === "") {
            return '-'
        } else {
           //let text = request.note.trim().replace(/\r?\n/g, '<br />')
            return  request.note.trim()
        }
    }

    displayLeaveStatus = (request) => {
        if(request.leaveStatus === 'REQUESTED') {
            return 'Requested'
        } else if(request.leaveStatus === 'APPROVED') {
            return 'Approved'
        } else if(request.leaveStatus === 'DECLINED') {
            return 'Declined'
        } else
            return ""
    }


    render() {
        return(
            <Fragment> 
                <h3> Request history </h3>
                <br/>
                {this.state.requests.length > 0 ? 
                <Table class="table table-hover ">
                    <TableHead>
                        <TableRow>
                            <TableCell class='text-success'><i class="fas fa-calendar-minus"></i> Start</TableCell>
                            <TableCell class='text-success'><i class="fas fa-calendar-plus"></i> End</TableCell>
                            <TableCell class='text-success'><i class="fas fa-sign-out-alt"></i> Type</TableCell>
                            <TableCell class='text-success '><i class="far fa-sticky-note"></i> Note</TableCell>
                            <TableCell class='text-success '> <i class="fas fa-clock"></i> Status</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                         {this.state.requests.map (row => (
                        <TableRow key={row.id}>
                        <TableCell><p class='text-white'>{new Date(row.startDate).toLocaleDateString("en-US")}</p></TableCell>
                        <TableCell><p class='text-white'>{new Date(row.endDate).toLocaleDateString("en-US")}</p></TableCell>
                        <TableCell><p class='text-white'>{this.displayType(row)}</p></TableCell>
                        <TableCell style={{wordBreak: "break-all"}}><p class='text-white'>{this.displayNote(row)}</p></TableCell>
                        <TableCell><p class='text-white'>{this.displayLeaveStatus(row)}</p></TableCell>
                        </TableRow>))}
                    </TableBody>
                </Table> : <div> <br/> Nothing to display. </div>}
                 
            </Fragment>
        )
    }
}


export default LeaveRequestHistory