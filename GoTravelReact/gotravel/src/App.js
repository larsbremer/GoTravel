import React, { Component } from 'react';
import './App.css';
import Trip from './Trip.js';
import TripList from './TripList.js';
import { Switch, Route, Link  } from 'react-router-dom'

class App extends Component {

  render() {

    return(

      <div>
        <Link to={'/'}>
          <header>
            <div className="header">GO TRAVEL</div>
          </header>
        </Link>
        <Switch>
          <Route exact path='/' component={TripList}/>
          <Route path='/trips/:tripId' component={Trip}/>
        </Switch>
      </div>
    )
 
  }
}

export default App;
