import { connect } from "react-redux";
import Header from "../components/Header";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { oauth: state.oauth, user: state.user };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Header);
