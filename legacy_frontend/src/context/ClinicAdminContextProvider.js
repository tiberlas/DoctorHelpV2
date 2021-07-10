import React, {Component, createContext} from 'react'

export const ClinicAdminContext = createContext()

class ClinicAdminContextProvider extends Component {
    state = {  
        admin: {
            id: this.props.admin.id, 
            email: this.props.admin.email,
            firstName: this.props.admin.firstName,
            lastName: this.props.admin.lastName,
            address: this.props.admin.address,
            city: this.props.admin.city,
            state: this.props.admin.state,
            phoneNumber: this.props.admin.phoneNumber,
            birthday: this.props.admin.birthday,
            clinicId: this.props.admin.clinicId
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
                    clinicId: this.props.admin.clinicId
                }
            })
        }
      }

    render() {

        return ( 
            <ClinicAdminContext.Provider value = {{...this.state}}>
                {this.props.children}
            </ClinicAdminContext.Provider>
         )
    }
}
 
export default ClinicAdminContextProvider