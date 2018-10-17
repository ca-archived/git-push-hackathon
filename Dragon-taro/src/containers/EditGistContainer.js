import { connect } from "react-redux";
import mapDispatchToProps from "./mapDispatchToProps";
import { EditGist } from "../components/EditGist";

function mapStateToProps(state) {
  return { gist: state.gist };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EditGist);
