import { connect } from "react-redux";
import Home from "../components/Home";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return state;
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
