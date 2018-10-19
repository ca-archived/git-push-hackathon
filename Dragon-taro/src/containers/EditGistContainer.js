import { connect } from "react-redux";
import mapDispatchToProps from "./mapDispatchToProps";
import { EditGist } from "../components/gistEditor/EditGist";

function mapStateToProps(state) {
  return { editor: state.editor, gist: state.gist, load: state.load };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(EditGist);
