import { connect } from "react-redux";
import Home from "../components/Home";
import mapDispatchToProps from "./mapDispatchToProps";

function mapStateToProps(state) {
  return { gists: state.gists, gist: state.gist };
}

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(Home);
