import React, {Component, createContext} from 'react'

export const CentreAdminContext = createContext()

class CentreAdminContextProvider extends Component {
    state = {  
        admin: {
            id: this.props.admin.id, 
            email: this.props.admin.email,
            firstName: this.props.firstName,
            lastName: this.props.admin.lastName,
            address: this.props.admin.address,
            city: this.props.admin.city,
            state: this.props.admin.state,
            phoneNumber: this.props.admin.phoneNumber,
            birthday: this.props.admin.birthday,
        }
    }

    componentDidUpdate(prevProps, prevState) {
        if (prevProps.admin !== this.props.admin) {
            this.setState({
                admin:{ id: this.props.admin.id, 
                    email: this.props.admin.email,
                    firstName: this.props.admin.firstName,
                    lastName: this.props.admin.lastName,
                    address: this.props.admin.address,
                    city: this.props.admin.city,
                    state: this.props.admin.state,
                    phoneNumber: this.props.admin.phoneNumber,
                    birthday: this.props.admin.birthday,
                }
            })
        }
      }

    render() {

        return ( 
            <CentreAdminContext.Provider value = {{...this.state}}>
                {this.props.children}
            </CentreAdminContext.Provider>
         )
    }
}
 
export default CentreAdminContextProvider