import { connect } from "react-redux";
import Header from "../components/Header";
import mapDispatchToProps from "./mapDispatchToProps";

export default connect(
  state => state,
  mapDispatchToProps
)(Header);
