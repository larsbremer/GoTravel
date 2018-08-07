import React, { Component } from 'react';
import './App.css';
import { Link } from 'react-router-dom'


class TripList extends Component {

  constructor(props){
    super(props);

    this.state = {
      trips: [],
    };
  }

  componentDidMount() {
    // 5b635c63e47a545f722bd893
    fetch('http://localhost:8080/GoTravelBackend/rest/trips')
       .then(response => response.json()).then(trips => {
        this.setState({trips: trips})
       });    
  }


  render() {

    if(this.state.trips.length === 0){
      return <h3>no data</h3>
    }

    return (
      <div className="App">
        <table className="main-table">
        {
          Array.from(this.state.trips).map((trip, tripIndex) => 
                <tr><td className="segments"><Link to={'/trips/'+trip.id}>
                  <p className="font-attribute-categories left-float-cleared">TripId: {trip.id}</p>
                  <p className="font-attribute-categories left-float-cleared">startDate: {trip.startDate}</p>
                  <p className="font-attribute-categories left-float-cleared">endDate: {trip.endDate}</p>
                  <p className="font-attribute-categories left-float-cleared">name: {trip.name}</p>
                  </Link></td></tr>
            )
        }
      </table>
      </div>
    );
  }
}

export default TripList;
