import React from "react";
import { If } from "./If";
import { connect } from "react-redux";

const Loader = props => {
  const {
    load: { isLoading },
    isDisableLaod,
    message
  } = props;
  return (
    <If condition={isLoading && !isDisableLaod}>
      <div className="p-loader">
        <span className="cssload-loader">
          <span className="cssload-loader-inner" />
        </span>
        <p>{message}</p>
      </div>
    </If>
  );
};

Loader.defaultProps = {
  isDisableLaod: false,
  message: "Loading..."
};

export default connect(state => state)(Loader);
