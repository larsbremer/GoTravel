import React from 'react';


class Attributes extends React.Component {

  constructor(props) {
    super();

    this.state = {
      attributes: props.attributes
    };
  }

  render() {

    const attributes = this.state.attributes

    return (

      <div className="left-float-cleared top-margin">
      {

    Object.keys(attributes).map((key, index) => {

          const value = attributes[key];

          if(key === "URL" && value !== "-"){
            return(
              <div className="attributes" key={index}>
                <p className="font-attribute-categories left-float-cleared">Link</p>
                <a href={value} className="font-medium-gray left-float-cleared" target="_blank">link</a>
              </div>
            )
          }else{
            return(
              <div className="attributes" key={index}>
                <p className="font-attribute-categories left-float-cleared">{key}</p>
                <p className="font-medium-gray left-float-cleared">{value}</p>
              </div>
            )
          }
        })
      }
      </div>

    );
  }
}

export default Attributes;
