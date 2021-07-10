import React, {Fragment} from 'react'

class LeaveRequestItem extends React.Component {
   
    handleRequestClick = () => {
        this.props.setRequestSelected(this.props.userRequest)
    }

    staffRoleDisplay = () => {
        return this.props.userRequest.staffRole.substr(0,1) 
                + this.props.userRequest.staffRole.substr(1, this.props.userRequest.staffRole.length).toLowerCase()
    }

    leaveTypeDisplay = () => {
        return this.props.userRequest.leaveType.substr(0,1) + this.props.userRequest.leaveType.substr(1, this.props.userRequest.leaveType.length).toLowerCase()
    }
    
    render() {
        let i = 0
        return(
            <Fragment>
                 <tr className={(++i)%2 ? `table-inverse` : ``} style={{cursor:"pointer"}} onClick={this.handleRequestClick}> 
                                    <td>{this.props.userRequest.firstName}&nbsp;{this.props.userRequest.lastName}</td>
                                    <td>{this.staffRoleDisplay()}</td>
                                    <td>{this.leaveTypeDisplay()}</td>
                                    <td>{new Date(this.props.userRequest.startDate).toLocaleDateString("en-US")}</td>
                                    <td>{new Date(this.props.userRequest.endDate).toLocaleDateString("en-US")}</td>
                </tr>
            </Fragment>
        )
    }
}

export default LeaveRequestItem