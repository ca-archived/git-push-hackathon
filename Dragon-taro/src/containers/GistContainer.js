import { connect } from "react-redux";
import Gist from "../components/Gist";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { oauth: state.oauth, user: state.user };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Gist);
