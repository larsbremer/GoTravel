import React, { Component } from 'react';
import './App.css';

class App extends Component {

  constructor(props){
    super(props);

    this.state = {
      trip: [],
      segments: []
    };
  }

  componentDidMount() {

    fetch('http://localhost:8080/GoTravelBackend/rest/trips/5b635c63e47a545f722bd893?expand=true')
       .then(response => response.json()).then(data => this.setState({ trip: data,  segments: data.segments }));
    
  }


  render() {
    return (
      <div className="App">
        <h1>{this.state.trip.name}</h1>
        <h3>
        {
          this.state.segments.map(segment => {
          return (
            <p>{segment.type}</p>
          );
          }) 
        }
        </h3>
      </div>
    );
  }
}

export default App;
