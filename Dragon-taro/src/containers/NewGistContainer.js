import { connect } from "react-redux";
import { NewGist } from "../components/NewGist";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { editor: state.editor, gist: state.gist };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(NewGist);
