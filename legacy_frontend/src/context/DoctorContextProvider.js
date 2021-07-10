import React, {Component, createContext} from 'react';

export const DoctorContext = createContext();

class DoctorContextProvider extends Component {
    state = { 
        doctor: {
            id: this.props.doctor.id,
            email: this.props.doctor.email,
            firstName: this.props.doctor.firstName,
            lastName: this.props.doctor.lastName,
            address: this.props.doctor.address,
            city: this.props.doctor.city,
            state: this.props.doctor.state,
            phoneNumber: this.props.doctor.phoneNumber,
            birthday: this.props.doctor.birthday,
            clinicId: this.props.doctor.clinicId
        }
     }

     componentDidUpdate(prevProps, prevState) {
        if (prevProps.doctor !== this.props.doctor) {
            this.setState({
                doctor:{ 
                    id: this.props.doctor.id,
                    email: this.props.doctor.email,
                    firstName: this.props.doctor.firstName,
                    lastName: this.props.doctor.lastName,
                    address: this.props.doctor.address,
                    city: this.props.doctor.city,
                    state: this.props.doctor.state,
                    phoneNumber: this.props.doctor.phoneNumber,
                    birthday: this.props.doctor.birthday,
                    clinicId: this.props.doctor.clinicId
                }
            })
        }
      }

    render() { 
        return ( 
            <DoctorContext.Provider value = {{...this.state}}>
                {this.props.children}
            </DoctorContext.Provider>
         );
    }
}
 
export default DoctorContextProvider;