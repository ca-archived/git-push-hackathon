import { connect } from "react-redux";
import Header from "../components/Header";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return state;
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Header);
