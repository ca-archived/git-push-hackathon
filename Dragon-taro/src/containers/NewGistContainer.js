import { connect } from "react-redux";
import { NewGist } from "../components/NewGist";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { gists: state.gists };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewGist);
