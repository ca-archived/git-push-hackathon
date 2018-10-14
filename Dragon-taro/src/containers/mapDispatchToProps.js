import { bindActionCreators } from "redux";
import * as actions from "../actions/actions";

export default function mapDispatchToProps(dispatch) {
  return { actions: bindActionCreators(actions, dispatch) };
}
