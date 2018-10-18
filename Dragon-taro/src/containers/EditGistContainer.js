import { connect } from "react-redux";
import mapDispatchToProps from "./mapDispatchToProps";
import { EditGist } from "../components/EditGist";

function mapStateToProps(state) {
  return { editor: state.editor, gist: state.gist };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EditGist);
