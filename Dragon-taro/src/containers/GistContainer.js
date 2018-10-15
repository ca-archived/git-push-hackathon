import { connect } from "react-redux";
import Gist from "../components/Gist";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { gist: state.gist };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Gist);
