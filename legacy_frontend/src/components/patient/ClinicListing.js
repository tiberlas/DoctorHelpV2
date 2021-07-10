import React, {Component, Fragment}  from 'react';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { FormControl } from 'react-bootstrap';
import Select from 'react-select';
import Slider from '@material-ui/core/Slider';
import { Modal } from 'react-bootstrap';
import Button from 'react-bootstrap/Button';
import { Tab } from '@material-ui/core';

const crniFont = {
	option: provided => ({
		...provided,
		color: 'black'
	  }),
	  control: provided => ({
		...provided,
		color: 'black'
	  }),
	  singleValue: (provided) => ({
		...provided,
		color: 'black'
	  })
}


class ClinicListing extends Component {

	state = {
		clinics: [], 
		activeFilter: 'unfiltered', 
		appointmentTypes: [],
		selectedDate: 'unfiltered', 
		cantReserve : true, 
		types : [], 
		states : [], 
		cities : [], 
		addresses : [], 

		stateFilter: 'unfiltered',
		cityFilter : 'unfiltered',
		addressFilter : 'unfiltered', 
		minRat : 'unfiltered', 
		maxRat : 'unfiltered', 
		minPrice : 'unfiltered', 
		maxPrice : 'unfiltered', 

		showModal : false
	}

	componentDidMount () {
		axios.get ('/api/clinics/listing/proc_type=unfiltered/date=unfiltered/stat=unfiltered/cit=unfiltered/adr=unfiltered/min_rat=unfiltered/max_rat=unfiltered/min_price=unfiltered/max_price=unfiltered')
		.then (response => {
			this.setState ({
				clinics : response.data.clinicList, 
				appointmentTypes : response.data.procedureType, 
				activeFilter  : response.data.procedureTypeString, 
				selectedDate : response.data.dateString, 
				states : response.data.stateList, 
				stateFilter : response.data.stateString, 
				cityFilter : response.data.cityString, 
				addressFilter : response.data.addressString
			})
			let items =[];
			var size = response.data.procedureType.length;
			items.push ({
				label : "-", 
				value : "-"
			})
			for (let i = 0; i < size; ++i) {
				items.push ({
					label : response.data.procedureType[i],
					value : response.data.procedureType[i]
				})
			}

			let itemsState = [];
			let sizeState = response.data.stateList.length;
			itemsState.push ({
				label : "-", 
				value : "-"
			})
			for (let i = 0; i < sizeState; ++i) {
				itemsState.push ({
					label : response.data.stateList[i],
					value : response.data.stateList[i]
				})
			}

			let itemsCity = [];
			let sizeCity = response.data.cityList.length;
			itemsCity.push ({
				label : "-", 
				value : "-"
			})
			for (let i = 0; i < sizeCity; ++i) {
				itemsCity.push ({
					label : response.data.cityList[i],
					value : response.data.cityList[i]
				})
			}

			let itemsAddress = [];
			let sizeAddress = response.data.addressList.length;
			itemsAddress.push ({
				label : "-", 
				value : "-"
			})
			for (let i = 0; i < sizeAddress; ++i) {
				itemsAddress.push ({
					label : response.data.addressList[i], 
					value : response.data.addressList[i]
				})
			}

			this.setState ({
				types : items,
				states : itemsState,
				cities : itemsCity,
				addresses : itemsAddress
			})
		})
	}

	generateClinicRows(row) {
		let profileUrl = "";// = 'http://localhost:3000';
		if ((this.state.activeFilter !== '') && (this.state.activeFilter !== 'unfiltered')) {
			profileUrl += '/clinic/' + row.id + '/' + this.state.activeFilter;
		} else {
			profileUrl += '/clinic/' + row.id + '/unfiltered';
		}
		profileUrl += '/' + this.state.selectedDate;
		return (
            <Fragment>
                <TableCell width="150" class="text-white"><Link exact to = {profileUrl} >{row.name}</Link></TableCell>
				<TableCell width="100px" class="textext-whitet">{row.address}</TableCell>
				<TableCell width="75px" class="text-white">{row.city}</TableCell>
				<TableCell width="50px" class="text-white">{row.state}</TableCell>
				<TableCell width="50px" class="text-white">{row.rating}</TableCell>
				<TableCell width="50px" class="text-white">{row.price}</TableCell>
                <TableCell width="50px" class="text-white">
					< Link exact to = {profileUrl} hidden={this.state.cantReserve} >Reserve</Link>
				</TableCell>
				{/* <TableCell hidden={this.state.cantReserve}><Form onSubmit={newUrl}><p class='text-white'><Button type="submit" onClick={alert (profileUrl)} >Reserve</Button></p></Form></TableCell> */}
		    </Fragment>

			// <Fragment>
            //     <TableCell width="350px"><Link exact to = {profileUrl} >{row.name}</Link></TableCell>
			// 	<TableCell width="100px"><p class='text-white'>{row.address}</p></TableCell>
			// 	<TableCell width="100px"><p class='text-white'>{row.city}</p></TableCell>
			// 	<TableCell width="50px"><p class='text-white'>{row.state}</p></TableCell>
			// 	<TableCell width="50px"><p class='text-white'>{row.rating}</p></TableCell>
			// 	<TableCell width="75px"><p class='text-white'>{row.price}</p></TableCell>
            //     <TableCell width="50px">< Link exact to = {profileUrl} hidden={this.state.cantReserve} >Reserve</Link></TableCell>
			// 	{/* <TableCell hidden={this.state.cantReserve}><Form onSubmit={newUrl}><p class='text-white'><Button type="submit" onClick={alert (profileUrl)} >Reserve</Button></p></Form></TableCell> */}
		    // </Fragment>


        )
    }

	// goToDoctorListing (row) {
	// 	// alert ("Looking for some doctors?")
	// 	let profileUrl = 'http://localhost:3000' + '/clinic/' + row.id + '/' + this.state.activeFilter + '/' + this.state.selectedDate;
	// 	alert (profileUrl);
	// 	window.location.href = profileUrl;
	// 	// exact to = profileUrl;
	// }


	handleFilterType (text)  {
		text = text.value;
		text = text.replace (' ', '_');
		let element = document.getElementById ("picker");
		let value = element.value;
		if (value === "") {
			value = 'unfiltered'
		}
		if (text === "-") {
			text = 'unfiltered'
		}
		
		this.setState ({
			selectedDate : value,
			activeFilter : text
		});
		
		if ((value === 'unfiltered') || (text === 'unfiltered')) {
			// alert ("Unfiltered =D");
			this.setState ({
				cantReserve : true
			})
		}
		else {
			// alert ("Filtered")
			this.setState ({
				cantReserve : false
			})
		}

		this.fetchData(text, value, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, this.state.maxPrice);
	}

	toggleMenu = () => {
		if(!this.state.visible){
		  this.setState({ visible: true, hide: true });
		} else {
		  this.setState({ visible: false, hide: false});
		}
	}



	

	handleFilterDate () {
		
		this.toggleMenu();
		let element = document.getElementById ("picker");
		let value = element.value;
		let text = this.state.activeFilter;
		
		let year = value.split("-")[0];
		let month = value.split("-")[1];
		let day = value.split("-")[2];

		let actualDate = new Date();
		if (actualDate.getFullYear() > year) {
			// alert ("Selected is in the past")
			value = "";
		}
		else {
			// alert (actualDate.getDate() + " " +  (actualDate.getMonth() + 1) + " " + actualDate.getFullYear());
			if ((actualDate.getMonth() + 1) > month) {
				// alert ("Selected is in the past");
				value = "";
			}
			else {
				if (actualDate.getDate() >= day) {
					// alert ("Selected is in the past")
					value = "";
				}
				else {
					// alert ("Selected is in the future");
				}
			}
		}

		if (value === "") {
			value = "unfiltered";
		}

		this.setState ({
			selectedDate : value,
			activeFilter : text
		})

		if ((value === 'unfiltered') || (text === 'unfiltered')) {
			this.setState ({
				cantReserve : true
			})
		}
		else {
			this.setState ({
				cantReserve : false
			})
		}

		this.fetchData(text, value, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, this.state.maxPrice);

	}

	handleFilterState (text) {
		// alert ("Filtering by state!");
		let str = text.value;
		if (str === '-') {
			str = 'unfiltered'
		} 

		this.toggleMenu();

		this.setState ({
			stateFilter : str
		})
		this.fetchData(this.state.activeFilter, this.state.selectedDate, str, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, this.state.maxPrice);
	}

	handleFilterCity (text) {
		let str = text.value;
		if (str === '-') {
			str = 'unfiltered';
		}

		this.toggleMenu();

		this.setState ({
			cityFilter : str
		})
		this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, str, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, this.state.maxPrice);
	}

	handleFilterAddress (text) {
		let str = text.value;
		if (str === '-') {
			str = 'unfiltered';
		}

		this.toggleMenu();

		this.setState ({
			addressFilter : str
		})
		this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, str, this.state.minRat, this.state.maxRat, this.state.minPrice, this.state.maxPrice);
	}

	handleMinRatingFilter (value) {
		// alert (value);
		this.setState ({
			minRat : value
		});

		this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, value, this.state.maxRat, this.state.minPrice, this.state.maxPrice);
	}

	handleMaxRatingFilter (value) {
		this.setState ({
			maxRat : value
		});
		
		this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, value, this.state.minPrice, this.state.maxPrice);
	}

	handleMinPriceFilter () {
		if (this.state.activeFilter === 'unfiltered') {
			return;
		}
		let element = document.getElementById ("id_min_price");
		if (element.value === '') {
			// alert ("NaN");
			this.setState ({
				minPrice : 'unfiltered'
			})

			this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, 'unfiltered', this.state.maxPrice);
		}
		else {
			// alert (element.value);
			this.setState ({
				minPrice : element.value
			})	

			this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, element.value, this.state.maxPrice);
		}
	}

	handleMaxPriceFilter () {
		if (this.state.activeFilter === 'unfiltered') {
			return;
		}
		let element = document.getElementById ("id_max_price");
		if (element.value === '') {
			// alert ("NaN");
			this.setState ({
				maxPrice : 'unfiltered'
			})

			this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, 'unfiltered');
		}
		else {
			// alert (element.value);
			this.setState ({
				maxPrice : element.value
			})	

			this.fetchData (this.state.activeFilter, this.state.selectedDate, this.state.stateFilter, this.state.cityFilter, this.state.addressFilter, this.state.minRat, this.state.maxRat, this.state.minPrice, element.value);
		}
	}

	fetchData (dil, dat, sFilter, cFilter, aFilter, minrFilter, maxrFilter, minpFilter, maxpFilter) {
		// let text = this.state.activeFilter;
		// let date = this.state.selectedDate;
		let requestPartOne = '/api/clinics/listing/proc_type=';
		let requestPartTwo = dil + '/date=' + dat;
		requestPartTwo += '/stat=' + sFilter;
		requestPartTwo += '/cit=' + cFilter;
		requestPartTwo += '/adr=' + aFilter;
		requestPartTwo += '/min_rat=' + minrFilter;
		requestPartTwo += '/max_rat=' + maxrFilter;
		requestPartTwo += '/min_price=' + minpFilter;
		requestPartTwo += '/max_price=' + maxpFilter;
		let wholeRequest = requestPartOne + requestPartTwo;
		
		axios.get (wholeRequest)
		.then (response => {
			this.setState ({
				clinics: response.data.clinicList, 
				appointmentTypes: response.data.procedureType, 
				
			})
		})
	}

	
	switchDialog () {
		if (this.state.showModal === true) {
			this.setState ({
				showModal : false
			})
		}
		else {
			this.setState ({
				showModal : true
			})
		}
	}
	
	render () {




		let size = this.state.clinics.length;
		let numberOfTypes = this.state.appointmentTypes.length;
		return (
			<div class="row d-flex justify-content-center">
                <div class='col-md-8'>

					<Modal show={this.state.showModal} onHide={() => this.switchDialog()}>
						<Modal.Header closeButton>
							<Modal.Title>Filter through appointments</Modal.Title>
						</Modal.Header>

						<Modal.Body>
							
							<Table>
								
								<TableRow>
									<TableCell colSpan="4">To request an appointment, you must choose a procedure type and date. Other fields are optional.</TableCell>
								</TableRow>
								<TableRow>
									<TableCell colSpan="4">
										<form>
											<Select
											className="select-procedure-type"
												styles={crniFont}
												placeholder={(this.state.activeFilter === 'unfiltered') ? ('Choose a procedure type') : (this.state.activeFilter.replace('_', ' '))}
												onChange={this.handleFilterType.bind(this)}
												options={this.state.types}
												// defaultValue = {(this.state.activeFilter === 'unfiltered') ? (this.state.types[0]) : (this.state.activeFilter)}
											/>
										</form>
									</TableCell>
								</TableRow>
								<TableRow>
									<TableCell width="180px">
										<form>
											<FormControl id="picker" type="date" onChange={() => this.handleFilterDate ()}></FormControl>
										</form>		
									</TableCell>	
									<TableCell></TableCell>		
									<TableCell>{(this.state.selectedDate === 'unfiltered') ? ("Date must be after dotay") : ("Current: " + this.state.selectedDate)}</TableCell>		
									<TableCell></TableCell>					
								</TableRow>
								<TableRow>
									<TableCell width="205px" colSpan="2">
										<form>
											<Select 
												styles={crniFont}
												onChange={this.handleFilterState.bind(this)}
												options={this.state.states}
												placeholder={(this.state.stateFilter === 'unfiltered') ? ('Choose a state') : (this.state.stateFilter)}
											/>
										</form>
									</TableCell>
									<TableCell colSpan="2">
										<form>
											<Select 
												styles={crniFont}
												onChange = {this.handleFilterCity.bind(this)}
												options={this.state.cities}
												placeholder={(this.state.cityFilter === 'unfiltered') ? ('Choose a city') : (this.state.cityFilter)}
											/>
										</form>
									</TableCell>
								</TableRow>
								<TableRow>
									<TableCell colSpan = "4">
										<form>
											<Select 
												styles={crniFont}
												onChange = {this.handleFilterAddress.bind(this)}
												options={this.state.addresses}
												placeholder={(this.state.addressFilter === 'unfiltered') ? ('Choose an address') : (this.state.addressFilter)}
											/>
										</form>
									</TableCell>
								</TableRow>
								<TableRow>
									<TableCell colSpan="20">
										Minimal rating: 
										<Slider 
											style = {{color : "#5bc0de"}}
											defaultValue={(this.state.minRat === 'unfiltered') ? (1) : (this.state.minRat)}
											aria-labelledby="discrete-slider"
											valueLabelDisplay="auto"
											step={1}
											marks
											min={1}
											max={5}
											onChange = {(event, value) => this.handleMinRatingFilter(value)}
										/>
										Maximal rating: 
										<Slider 
											style = {{color : "#5bc0de"}}
											defaultValue={(this.state.maxRat === 'unfiltered') ? (5) : (this.state.maxRat)}
											aria-labelledby="discrete-slider"
											valueLabelDisplay="auto"
											step={1}
											marks
											min={1}
											max={5}
											onChange = {(event, value) => this.handleMaxRatingFilter(value)}
										/>
									</TableCell>
								</TableRow>
								<TableRow>
									<TableCell>
										<FormControl type="number" 
											onChange = {(event, text) => this.handleMinPriceFilter()}
											id = "id_min_price"
											placeholder={(this.state.minPrice === 'unfiltered') ? ('Minimal price') : (this.state.minPrice)}
										/>
									</TableCell>
									<TableCell></TableCell>
									<TableCell width="180px">
										<FormControl type="number" 
											onChange = {(event, text) => this.handleMaxPriceFilter()}
											id = "id_max_price"
											placeholder={(this.state.maxPrice === 'unfiltered') ? ('Maximum price') : (this.state.maxPrice)}
										/>
									</TableCell>
									<TableCell></TableCell>
								</TableRow>
							</Table>
						</Modal.Body>

						<Modal.Footer>
							<Button id= "close_clinic_modal_button" variant="secondary" onClick={() => this.switchDialog()}>Close</Button>
						</Modal.Footer>
					</Modal>


					

					<Table >
						<TableHead>
							<TableRow> 
								<TableCell width="150px" class="text-success"><i class="fas fa-plus-circle"></i> Clinic Name</TableCell>
								<TableCell width="100px" class="text-success"><i class="fas fa-map-marker-alt"></i> Address</TableCell>
								<TableCell width="75px" class="text-success"><i class="fas fa-city"></i> City</TableCell>
								<TableCell width="50px" class="text-success"><i class="fas fa-globe-africa"></i> State</TableCell>
								<TableCell width="50px" class="text-success"><i class="fas fa-star"></i> Rating</TableCell>
								<TableCell width="50px" class="text-success"><i class="fab fa-bitcoin"></i> Price</TableCell>
								<TableCell width="50px" class="text-success">
									<Button onClick={() => this.switchDialog()} 
										type = "button"
										id="filter_clinics_button"
									>	
										<i class="fas fa-search"></i> Search
									</Button>
								</TableCell>
							</TableRow>
						</TableHead>
						<TableBody>
							{
								size > 0 ? this.state.clinics.map (row => (
									<TableRow key={row.id}>
										{this.generateClinicRows(row)}
									</TableRow>
								)) : <h3> No results found. :( </h3> 
							}
						</TableBody>
					</Table>

				</div>
			</div>
		);
	}

}

export default  ClinicListing; 