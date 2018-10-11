const path = require("path");
var webpack = require("webpack");
require("dotenv").config();

var defineEnv = new webpack.DefinePlugin({
  "process.env": {
    TEST: JSON.stringify(process.env.TEST)
  }
});

module.exports = {
  entry: "./app.js",
  output: {
    path: path.join(__dirname, "static/js"),
    filename: "bundle.js"
  },
  resolve: {
    modules: ["node_modules"],
    extensions: [".js", ".jsx"]
  },
  module: {
    rules: [
      {
        test: /\.js$/,
        exclude: /node_modules/,
        use: {
          loader: "babel-loader",
          options: {
            presets: ["@babel/preset-env", "@babel/preset-react"]
          }
        }
      }
    ]
  },
  plugins: [defineEnv]
};
