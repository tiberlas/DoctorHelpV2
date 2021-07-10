import React, {Component} from 'react';

class ClinicItem extends Component {

	constructor (props) {
		super (props);

		this.state = {
			name: this.props.value.name, 
			address: this.props.value.address, 
			description: this.props.value.description
		}
	}

	render () {
		return (
			<span>
				<p class='text-primary'>{this.state.name}</p>
			</span>
		);
	}

}

export default ClinicItem;