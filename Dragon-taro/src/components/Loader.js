import React from "react";
import { If } from "./If";
import { connect } from "react-redux";

const Loader = props => {
  const {
    load: { isLoading }
  } = props;
  return (
    <If condition={isLoading}>
      <div className="p-button">
        <span className="cssload-loader">
          <span className="cssload-loader-inner" />
        </span>
        <p>Loading...</p>
      </div>
    </If>
  );
};

export default connect(state => state)(Loader);
