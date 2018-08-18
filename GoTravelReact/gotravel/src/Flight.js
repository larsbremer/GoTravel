import React from 'react';
import Modal from 'react-modal';
import Attributes from './Attributes.js';
import * as utils from './Utils.js';


Modal.setAppElement('body')

class Flight extends React.Component {

  constructor(props) {
    super();

    this.state = {
      modalIsOpen: false,
      segment: props.segment,
      formData: props.segment
    };

    this.openModal = this.openModal.bind(this);
    this.closeModal = this.closeModal.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleSubmit = this.handleSubmit.bind(this);
  }

  handleChange(event) {
    var formData = this.state.formData
    formData[event.target.name] = event.target.value

    this.setState({formData: formData})
  }

  handleSubmit(event) {
    event.preventDefault();
    
    var formData = this.state.formData

    fetch('http://localhost:8080/GoTravelBackend/rest/flights/'+formData.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(formData)
    })
    .then(response => response.json()).then(updatedFlight => {
      utils.convertStringsToDateObjects(updatedFlight);
      this.setState({segment: updatedFlight})
     }); 

    this.closeModal()
  }


  openModal() {

    var formData = Object.assign({}, this.state.segment)
    utils.convertDateObjectsToStrings(formData)

    this.setState({formData: formData})
    this.setState({modalIsOpen: true});
  }

  closeModal() {
    this.setState({modalIsOpen: false});
  }

  printFlight(){

    const segment = this.state.segment;
  
    const startDate = utils.formatHours(segment.startDateObj)
    const endDate = utils.formatHours(segment.endDateObj)
    
    return(
      <div onClick={this.openModal}>
          <div>
            <img src="/assets/img/plane-sign.svg" className="transportation-icon" alt="" />
          </div>
          <p className="font-small-gray flight-header-pos">
            {startDate} - {endDate} ⎟ {segment.airline} ({segment.number})
          </p>
          <div className="trip-overview">
            <p className="font-large-gray flight-firstline-pos">{segment.departureLocation.city}, {segment.departureLocation.country}</p>
            <p className="font-large-gray flight-secondline-pos">{segment.arrivalLocation.city}, {segment.arrivalLocation.country}</p>
            <div className="circle circle-first-pos"></div>
            <div className="circle circle-second-pos"></div>
            <div className="line"></div>
          </div>
          <Attributes attributes={segment.attributes}/>
        </div>

    )
  }

  printModal(){
    
    return(
      <Modal className="modal" isOpen={this.state.modalIsOpen}
      onRequestClose={this.closeModal} contentLabel="Flight">
        <p className="tripname">Flight</p> 
        <form onSubmit={this.handleSubmit}>
          <p className="font-large-gray">
            Start Date: 
            <input className="formposition formsize" type="text" name="startDate" value={utils.formatFull(this.state.formData.startDateObj)} onChange={this.handleChange} />
          </p>
          <p className="font-large-gray">
            End Date: 
            <input className="formposition formsize" type="text" name="endDate" value={utils.formatFull(this.state.formData.endDateObj)} onChange={this.handleChange} /></p>
          <p className="font-large-gray">
            Flight Number: 
            <input className="formposition formsize" type="text" name="number" value={this.state.formData.number} onChange={this.handleChange} /></p>
          <p className="font-large-gray">
            Airline: 
            <input className="formposition formsize" type="text" name="airline" value={this.state.formData.airline} onChange={this.handleChange} /></p>
          <p className="font-large-gray">
            Seat Number: 
            <input className="formposition formsize" type="text" name="seatNumber" value={this.state.formData.seatNumber} onChange={this.handleChange} /></p>
          <p className="font-large-gray">
            Airplane: 
            <input className="formposition formsize" type="text" name="airplane" value={this.state.formData.airplane} onChange={this.handleChange} /></p>
          <button>Save</button>
        </form>
      </Modal>
    )
  }

  render() {
    return (
      <div>
        {this.printFlight()}
        {this.printModal()}
      </div>
    );
  }
}

export default Flight;
