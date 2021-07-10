import React, { Fragment } from 'react'

class UserRequestItem extends React.Component {


    handleRequestClick = () => {
        this.props.setRequestSelected(this.props.userRequest)
    }

    render() {
        return(
            <Fragment>
                 <tr style={{cursor:"pointer"}} onClick={this.handleRequestClick}> 
                                    <td>{this.props.userRequest.email} </td>
                                    <td>{this.props.userRequest.firstName}</td>
                                    <td>{this.props.userRequest.lastName}</td>
                </tr>
            </Fragment>
        )
    }
}

export default UserRequestItem