import { connect } from "react-redux";
import { NewGist } from "../components/gistEditor/NewGist";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { editor: state.editor, gist: state.gist, load: state.load };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewGist);
