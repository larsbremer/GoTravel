import React, { Component } from 'react';
import './App.css';
import { Link } from 'react-router-dom'
import moment from 'moment'


class TripList extends Component {

  constructor(props){
    super(props);

    this.state = {
      trips: [],
      dateFormatterDate: new Intl.DateTimeFormat('en-GB', { 
        month: '2-digit', 
        day: '2-digit',
        year: 'numeric'
      })
    };
  }

  componentDidMount() {
    fetch('http://localhost:8080/GoTravelBackend/rest/trips')
       .then(response => response.json()).then(trips => {
        this.setState({trips: trips})
       });    
  }

  getDate(dateString){
    const df = this.state.dateFormatterDate;
    var timeFormat = "YYYY-MM-DD'T'HH:mm:ss.SSSZ"
    var dateObject = moment.utc(dateString, timeFormat)
    return df.format(dateObject)
  }

  render() {

    if(this.state.trips.length === 0){
      return <h3>no data</h3>
    }

    return (
      <div className="App">
        <table className="trips-table">
        {
          Array.from(this.state.trips).map((trip, tripIndex) => 
                <tr><td className="segments"><Link to={'/trips/'+trip.id}>
                  <p className="font-large-gray left-float">{trip.name} ({this.getDate(trip.startDate)} - {this.getDate(trip.endDate)})</p>
                  </Link></td></tr>
            )
        }
      </table>
      </div>
    );
  }
}

export default TripList;
