import React, {Component, createContext} from 'react';

export const NurseContext = createContext();

class NurseContextProvider extends Component {
    state = { 
        nurse: {
            id: this.props.nurse.id,
            email: this.props.nurse.email,
            firstName: this.props.nurse.firstName,
            lastName: this.props.nurse.lastName,
            address: this.props.nurse.address,
            city: this.props.nurse.city,
            state: this.props.nurse.state,
            phoneNumber: this.props.nurse.phoneNumber,
            birthday: this.props.nurse.birthday,
            clinicId: this.props.nurse.clinicId
        }
     }

     componentDidUpdate(prevProps, prevState) {
        if (prevProps.nurse !== this.props.nurse) {
            this.setState({
                nurse:{ 
                    id: this.props.nurse.id,
                    email: this.props.nurse.email,
                    firstName: this.props.nurse.firstName,
                    lastName: this.props.nurse.lastName,
                    address: this.props.nurse.address,
                    city: this.props.nurse.city,
                    state: this.props.nurse.state,
                    phoneNumber: this.props.nurse.phoneNumber,
                    birthday: this.props.nurse.birthday,
                    clinicId: this.props.nurse.clinicId
                }
            })
        }
      }

    render() { 
        return ( 
            <NurseContext.Provider value = {{...this.state}}>
                {this.props.children}
            </NurseContext.Provider>
         );
    }
}
 
export default NurseContextProvider;