const path = require("path");

module.exports = {
  entry: "./app.js",
  output: {
    path: path.join(__dirname, "static/js"),
    filename: "bundle.js"
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        loader: "babel-loader"
      }
    ]
  }
};
