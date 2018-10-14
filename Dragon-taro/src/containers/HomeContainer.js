import { connect } from "react-redux";
import Home from "../components/Home";
import mapDispatchToProps from "./mapDispatchToProps";

export default connect(
  state => state,
  mapDispatchToProps
)(Home);
