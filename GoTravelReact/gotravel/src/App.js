import React, { Component } from 'react';
import './App.css';
import moment from 'moment'

class App extends Component {

  constructor(props){
    super(props);

    this.state = {
      trip: [],
      segments: [],
      myday: [],
      mytrip: [],
      dateFormatterDate: new Intl.DateTimeFormat('en-GB', { 
        weekday: 'short',
        month: '2-digit', 
        day: '2-digit' 
      }),
      dateFormatterHours: new Intl.DateTimeFormat('en-GB', { 
        hour: '2-digit', 
        minute: '2-digit' 
      })
    };
  }

  componentDidMount() {

    fetch('http://localhost:8080/GoTravelBackend/rest/trips/5b635c63e47a545f722bd893?expand=true')
       .then(response => response.json()).then(data => this.setState({ trip: data,  segments: data.segments }));    
  }

  getDateRangeString(currentDaySegments) {

    var startDate = null;
    var endDate = null;

    for ( var segmentId in currentDaySegments) {

      var segment = currentDaySegments[segmentId]
      
      if (segmentId == 0) {
	      startDate = segment.startDateReduced
      }

      if (segment.type !== 'accommodation') {
	      endDate = segment.endDateReduced
      }
    }

    if (startDate === endDate) {
      return [ startDate ];
    }

    return [ startDate, endDate ];
  }

  isSameDay(m1, m2){
    return m1.date() === m2.date() && m1.month() === m2.month() && m1.year() === m2.year()
  }

  getDaySegments(segments){

    var allSegments = []

    this.state.segments.map(segment => {

      // Make evening accommodation one element
      allSegments.push(segment)

      if (segment.eveningAccommodation != null) {
        var eveningAccommodation = segment.eveningAccommodation
	      allSegments.push(eveningAccommodation)
      }
    })

    var currentDaySegments = []
    var currentDay = moment("01-01-1000", "MM-DD-YYYY");

    console.log(this.state.segments)

    this.state.segments.map(segment => {

      this.setAttributes(segment)
      this.setDateAttributes(segment)
      
      var isOneDaySegment = this.isSameDay(segment.startDateObj, segment.endDateObj);
      var isSameDayAsBefore = this.isSameDay(currentDay, segment.startDateObj);
      
      if ((isOneDaySegment && isSameDayAsBefore) || segment.type === 'accommodation') {

	      currentDaySegments.push(segment);
      } else {

        if (currentDaySegments.length > 0) {
          this.state.mytrip.push(currentDaySegments)
          this.state.myday.push(this.getDateRangeString(currentDaySegments))
       }

	      currentDaySegments = [ segment ]
	      currentDay = segment.startDateObj
      }
      
    })

    this.state.mytrip.push(currentDaySegments)
    this.state.myday.push(this.getDateRangeString(currentDaySegments))

    console.log("myday: "+this.state.myday)
    console.log(this.state.myday)
    console.log("mytrip: "+this.state.mytrip)
    console.log(this.state.mytrip)

  }


  setDateAttributes(segment) {

    var timeFormat = "YYYY-MM-DD'T'HH:mm:ss.SSSZ"

    var startDate = segment.startDate
    var startDateObj = moment.utc(startDate, timeFormat)

    var endDate = segment.endDate
    var endDateObj = moment.utc(endDate, timeFormat)

    segment.startHours = startDateObj.format('HH:mm');
    segment.endHours = endDateObj.format('HH:mm');

    segment.startDateReduced = startDateObj.format('ddd, DD.MM.');
    segment.endDateReduced = endDateObj.format('ddd, DD.MM.');

    segment.startDateObj = startDateObj
    segment.endDateObj = endDateObj

    var durationMinutes = Math.round((endDateObj.format('x') - startDateObj.format('x')) / (1000 * 60));

    var days = Math.floor(durationMinutes / (24 * 60))
    var remainingMinutes = durationMinutes - (days * 24 * 60)
    var hours = Math.floor(remainingMinutes / 60)
    remainingMinutes = remainingMinutes - (hours * 60)
    var minutes = remainingMinutes

    var durationString = ""
    if (days > 0) {
      durationString += days + "d "
    }

    if (days > 0 || hours > 0) {
      durationString += hours + "h "
    }

    durationString += minutes + "min"

    segment.duration = durationString

  }

  setAttributes(segment) {

    var displayedAttributes = {
      "flight" : [ "seat", "airplane", "url" ],
      "busride" : [ "number", "url" ],
      "trainride" : [ "url" ],
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
      "number" : "Number"
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

  printFlight(segment) {

    const df = this.state.dateFormatterDate;
  
    const startDate = df.format(segment.startDateObj)
    const endDate = df.format(segment.endDateObj)

    return (
      
      <div>
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
        {this.printAttributes(segment)}
      </div>
    );
  }

  printTrainRide(segment) {

    const df = this.state.dateFormatterDate;
  
    const startDate = df.format(segment.startDateObj)
    const endDate = df.format(segment.endDateObj)

    return (
      
      <div>
        <div>
					<img src="/assets/img/train-sign.svg" className="transportation-icon" alt="" />
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
        {this.printAttributes(segment)}
      </div>
    );
  }


  printBusRide(segment) {

    const df = this.state.dateFormatterDate;
  
    const startDate = df.format(segment.startDateObj)
    const endDate = df.format(segment.endDateObj)

    return (
      
      <div> 
        <div>
					<img src="/assets/img/bus-sign.svg" className="transportation-icon" alt="" />
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
        {this.printAttributes(segment)}
      </div>
    );
  }

  printAttributes(segment) {

    return (
      <div className="left-float-cleared top-margin">
      {

        Object.keys(segment.attributes).map((key, index) => {

          const value = segment.attributes[key];

          if(key === "URL" && value !== "-"){
            return(
              <div className="attributes">
                <p className="font-attribute-categories left-float-cleared">Link</p>
                <a href={value} className="font-medium-gray left-float-cleared" target="_blank">link</a>
              </div>
            )
          }else if(key !== "URL" || value === "-"){
            return(
              <div className="attributes">
                <p className="font-attribute-categories left-float-cleared">{key}</p>
                <p className="font-medium-gray left-float-cleared">{value}</p>
              </div>
            )
          }
        })
      }
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
        {this.printAttributes(segment)}
      </div>
    );
  }


  printDateSegment(segment) {

    const df = this.state.dateFormatterHours;
  
    const startDate = df.format(segment.startDateObj)
    const endDate = df.format(segment.endDateObj)

    return (
          segment.activities.map(activity => {

            if(activity.startDate && !activity.endDate){
              return (<p className="font-activity">{startDate}: {activity.note}</p>)
            }else if(activity.startDate && activity.endDate){
              return (<p className="font-activity">{startDate} - {endDate}: {activity.note}</p>)
            }else{
              return (<p className="font-activity">{activity.note}</p>)
            }
          })
    );
  }

  printDateColumn(day, dayIndex, segmentIndexInDay, rowspan){

    if(segmentIndexInDay == 0 && day.length == 1){
      return(
        <td className="date" rowSpan={rowspan}>
          <p className="main-date">{day[0]}</p>
        </td> 
      )
    
    }else if(segmentIndexInDay == 0 && day.length == 2){
      return(
        <td className="date" rowSpan={rowspan}>
          <p className="main-date">{day[0]}<br />-<br />{day[1]}</p>
        </td> 
      )
    }

  }

  printSegmentColumn(segment, segmentIndexInDay, day){

          if(segment.type === "flight"){
            return(<td className="segments flight"> {this.printFlight(segment)}</td>);
          }else if(segment.type === "trainride"){
            return(<td className="segments flight"> {this.printTrainRide(segment)}</td>);
          }else if(segment.type === "busride"){
            return(<td className="segments flight">{this.printBusRide(segment)}</td>);
          }else if(segment.type === "accommodation"){
            return(<td className="segments accomodation"> {this.printAccomodation(segment)}</td>);
          }else if(segment.type === "datesegment"){
            return(<td className="segments flight"> {this.printDateSegment(segment)}</td>);
          }
  }


  render() {

    if(this.state.segments.length === 0){
      return <h3>no data</h3>
    }

    this.getDaySegments(this.state.segments);

    return (
      <div className="App">
        <h1>{this.state.trip.name}</h1> 
        <table className="main-table"><tbody>
        {
          Array.from(this.state.myday).map((day, dayIndex) => 
            Array.from(this.state.mytrip[dayIndex]).map((segment, segmentIndexInDay) => 
              <tr>
                {this.printDateColumn(day, dayIndex, segmentIndexInDay, this.state.mytrip[dayIndex].length)}
                {this.printSegmentColumn(segment, segmentIndexInDay, day)}
              </tr>
            )
          ) 
        }
      </tbody></table>
      </div>
    );
  }
}

export default App;
