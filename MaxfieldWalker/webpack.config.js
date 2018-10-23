const path = require("path");
const CopyWebpackPlugin = require("copy-webpack-plugin");
const ExtractTextPlugin = require("extract-text-webpack-plugin");
const MonacoWebpackPlugin = require("monaco-editor-webpack-plugin");
const Dotenv = require("dotenv-webpack");

const devMode = process.env.NODE_ENV === "development";

module.exports = {
  entry: {
    bundle: "./src/index.tsx",
    main: "./src/styles.scss"
  },
  output: {
    path: path.resolve(__dirname, "./dist"),
    // publicPath: "/",
    filename: "[name].js"
  },

  mode: devMode ? "development" : "production",

  devtool: devMode ? "source-map" : false,

  resolve: {
    extensions: [".ts", ".tsx", ".mjs", ".js", ".scss", ".css"]
  },

  devServer: {
    historyApiFallback: true,
    noInfo: true,
    publicPath: "/",
    contentBase: path.resolve(__dirname, "./dist"),
    hot: true,
    headers: {
      "Access-Control-Allow-Origin": "*"
    }
    // staticOptions: {
    //   setHeaders: (res, path, stat) => {
    //     if (/.js$/.test(path)) {
    //       res.setHeader("Content-Type", "application/javascript");
    //     }
    //   }
    // }
  },

  plugins: [
    new CopyWebpackPlugin([
      {
        from: "./src/index.html"
      },
      {
        from: "./src/images",
        to: "images"
      },
      {
        from: "./node_modules/monaco-editor/min/vs",
        to: "vs"
      }
    ]),
    new ExtractTextPlugin({
      filename: "[name].css",
      allChunks: true
    }),
    new Dotenv({
      path: "./.env"
    }),
    new MonacoWebpackPlugin()
  ],

  module: {
    rules: [
      {
        test: /\.tsx?$/,
        use: [{ loader: "ts-loader" }]
      },
      {
        test: /\.css$/,
        use: ["style-loader", "css-loader"]
      },
      {
        test: /\.scss$/,
        use: ExtractTextPlugin.extract({
          fallback: "style-loader",
          use: ["css-loader", "sass-loader"]
        })
      }
    ]
  }
};
