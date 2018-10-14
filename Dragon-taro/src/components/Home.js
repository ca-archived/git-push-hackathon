import React, { Component } from "react";

class Home extends Component {
  constructor() {
    super();
  }

  componentDidMount() {
    this.props.actions.getGists();
  }

  render() {
    return <div>home</div>;
  }
}

export default Home;
