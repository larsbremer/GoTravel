import React, { Component } from 'react';
import './App.css';
import Flight from './Flight.js';
import Attributes from './Attributes.js';
import moment from 'moment';
import * as utils from './Utils.js';

class Trip extends Component {

  constructor(props){
    super(props);

    this.state = {
      tripId: props.match.params.tripId,
      expand: {},
      dayList: [],
      daySegments: [],
      trip: [],
      dateFormatterDate: new Intl.DateTimeFormat('en-GB', { 
        weekday: 'short',
        month: '2-digit', 
        day: '2-digit' 
      })
    };
  }

  componentDidMount() {
    fetch('http://localhost:8080/GoTravelBackend/rest/trips/'+this.state.tripId+'?expand=true')
       .then(response => response.json()).then(trip => {
        this.setState({trip: trip})
        utils.convertStringsToDateObjects(trip);
        this.getDaySegments(trip);
       });    
  }

  getDateRangeString(currentDaySegments) {

    var startDate = null;
    var endDate = null;

    for ( var segmentId in currentDaySegments) {

      var segment = currentDaySegments[segmentId]
      
      if (Number(segmentId) === 0) {
	      startDate = utils.formatDay(segment.startDateObj)
      }

      if (segment.type !== 'accommodation') {
	      endDate = utils.formatDay(segment.endDateObj)
      }
    }

    if (segment.startDateObj.isSame(segment.endDateObj, 'd')) {
      console.log("same")
      return [ startDate ];
    }

    return [ startDate, endDate ];
  }

  isSameDay(m1, m2){
    return m1.date() === m2.date() && m1.month() === m2.month() && m1.year() === m2.year()
  }

  getDaySegments(trip){

    var allSegments = []

    trip.segments.forEach(segment => {

      // Make evening accommodation one element
      allSegments.push(segment)

      if (segment.eveningAccommodation != null) {
        var eveningAccommodation = segment.eveningAccommodation
	      allSegments.push(eveningAccommodation)
      }
    })

    var currentDaySegments = []
    var currentDay = moment("01-01-1000", "MM-DD-YYYY");

    var segments = []
    var dayList = []

    trip.segments.forEach(segment => {

      this.setAttributes(segment)
      utils.convertStringsToDateObjects(segment)
      
      var isSameDayAsBefore = this.isSameDay(currentDay, segment.startDateObj);
      
      if (isSameDayAsBefore || segment.type === 'accommodation') {

	      currentDaySegments.push(segment);
      } else {

        if (currentDaySegments.length > 0) {
          segments.push(currentDaySegments)
          dayList.push(this.getDateRangeString(currentDaySegments))
       }

	      currentDaySegments = [ segment ]
        currentDay = segment.startDateObj
      }
      
    })

    segments.push(currentDaySegments)
    dayList.push(this.getDateRangeString(currentDaySegments))

    
    this.setState({daySegments: segments, dayList: dayList})
  }


  

  setAttributes(segment) {

    var displayedAttributes = {
      "flight" : [ "seat", "airplane", "url" ],
      "busride" : [ "number", "url" ],
      "trainride" : [ "url", "seatNumber", "note" ],
      "accommodation" : [ "url" ]
    }

    var messageCatalog = {
      "flight" : "Flight",
      "seat" : "Seat",
      "airplane" : "Airplane",
      "accommodation" : "Accommodation",
      "url" : "URL",
      "trainride" : "Train Ride",
      "busride" : "Bus Ride",
      "number" : "Number",
      "note" : "Note",
      "seatNumber" : "Seat"
    }

    // Create map of properties
    if (segment.type in displayedAttributes) {

      var attributes = {};

      for ( var internalAttrId in displayedAttributes[segment.type]) {

        var internalAttrName = displayedAttributes[segment.type][internalAttrId]
        var displayAttrName = messageCatalog[internalAttrName]
        attributes[displayAttrName] = segment[internalAttrName]
        if (attributes[displayAttrName] == null) {
          attributes[displayAttrName] = "-"
        }
      }

      segment.attributes = attributes
    }

  }


  printTrainRide(segment) {

    const startDate = utils.formatHours(segment.startDateObj)
    const endDate = utils.formatHours(segment.endDateObj)

    return (
      
      <div>
        <div>
					<img src="/assets/img/train-sign.svg" className="transportation-icon" alt="" />
				</div>
        <p className="font-small-gray flight-header-pos">
          {startDate} - {endDate} ⎟ {segment.airline} ({segment.name})
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
    );
  }


  printBusRide(segment) {

    const startDate = utils.formatHours(segment.startDateObj)
    const endDate = utils.formatHours(segment.endDateObj)

    return (
      
      <div> 
        <div>
					<img src="/assets/img/bus-sign.svg" className="transportation-icon" alt="" />
				</div>
        <p className="font-small-gray flight-header-pos">
          {startDate} - {endDate} ⎟ {segment.name} ({segment.service})
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
    );
  }

  isExpanded(segmentId){

    var expand = this.state.expand;
    var exists = segmentId in expand;

    if(exists){
      return expand[segmentId];
    }

    return false;
  }

  printTripOverview(trip) {

    const df = this.state.dateFormatterDate;
  
    const startDate = df.format(trip.startDateObj)
    const endDate = df.format(trip.endDateObj)

    return (
      <div className="left-float-cleared left-margin">
          <div className="attributes">
            <p className="font-attribute-categories left-float-cleared">Start Date</p>
            <p className="font-medium-gray left-float-cleared">{startDate}</p>
          </div>
          <div className="attributes">
            <p className="font-attribute-categories left-float-cleared">End Date</p>
            <p className="font-medium-gray left-float-cleared">{endDate}</p>
          </div>
          <div className="attributes">
            <p className="font-attribute-categories left-float-cleared">Duration</p>
            <p className="font-medium-gray left-float-cleared">{utils.calculateDuration(trip)}</p>
          </div>
      </div>
    )
  }

  printAccomodation(segment) {

    return (
      
      <div>
        <p className="font-large-gray accommodation-firstline">{segment.name}</p>
				<p className="font-small-gray accommodation-sub">{segment.location.city}, {segment.location.country}</p>
        <div className="left-float-cleared top-margin">
          <div className="attributes">
            <p className="font-attribute-categories left-float-cleared">Link</p>
            <a href={segment.url} className="font-medium-gray left-float-cleared" target="_blank">link</a>
          </div>
        </div>
        <Attributes attributes={segment.attributes}/>
      </div>
    );
  }


  printDateSegment(segment) {

    return (
      segment.activities.map(activity => {
        
        const startDate = utils.formatHours(activity.startDateObj)
        
        if(activity.startDate && !activity.endDate){
          return (<p className="font-activity">{startDate}: {activity.note}</p>)
        }else if(activity.startDate && activity.endDate){
          const endDate = utils.formatHours(activity.endDateObj)
          return (<p className="font-activity" key={activity.id}>{startDate} - {endDate}: {activity.note}</p>)
        }else{
          return (<p className="font-activity" key={activity.id}>{activity.note}</p>)
        }
      })
    );
  }

  printDateColumn(day, dayIndex, segmentIndexInDay){
    if(segmentIndexInDay === 0){

      var dateString = day[0];
      if(day.length === 2){
        dateString = dateString + " - " + day[1];
      }

      return(
        <td className="date">
          <p className="main-date">{dateString}</p>
        </td> 
      )
    }
  }

  toggleExpand(id){

    var expand = this.state.expand;

    if(this.isExpanded(id)){
      expand[id] = false;
    }else{
      expand[id] = true;
    }

    this.setState({expand: expand})
  }

  printSegmentColumn(segment, segmentIndexInDay, day){

    if(segment.type === "flight"){
      return(<td className="segments flight"><Flight segment={segment}/></td>);
    }else if(segment.type === "trainride"){
      return(<td className="segments trainride" onClick={this.toggleExpand.bind(this, segment.id)}> {this.printTrainRide(segment)}</td>);
    }else if(segment.type === "busride"){
      return(<td className="segments busride" onClick={this.toggleExpand.bind(this, segment.id)}>{this.printBusRide(segment)}</td>);
    }else if(segment.type === "accommodation"){
      return(<td className="segments accomodation" onClick={this.toggleExpand.bind(this, segment.id)}> {this.printAccomodation(segment)}</td>);
    }else if(segment.type === "datesegment"){
      return(<td className="segments datesegment" onClick={this.toggleExpand.bind(this, segment.id)}> {this.printDateSegment(segment)}</td>);
    }
  }


  render() {

    if(this.state.daySegments.length === 0){
      return <h3>no data</h3>
    }

    return (
      <div className="App">
        <p className="font-xlarge tripname">{this.state.trip.name}</p> 
        <div>{this.printTripOverview(this.state.trip)}</div>
        <br />
        <table className="main-table">
        {
          Array.from(this.state.dayList).map((day, dayIndex) => 
            Array.from(this.state.daySegments[dayIndex]).map((segment, segmentIndexInDay) => 
              <tbody key={dayIndex+"."+segmentIndexInDay}>
                <tr>
                  {this.printDateColumn(day, dayIndex, segmentIndexInDay, this.state.daySegments[dayIndex].length)}
                </tr><tr>
                  {this.printSegmentColumn(segment, segmentIndexInDay, day)}
                </tr>
              </tbody>
            )
          ) 
        }
      </table>
      </div>
    );
  }
}

export default Trip;
