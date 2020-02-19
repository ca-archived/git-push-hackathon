"use strict";

const path = require("path");
const HtmlWebpackPlugin = require("html-webpack-plugin");
const ForkTsCheckerWebpackPlugin = require("fork-ts-checker-webpack-plugin");

module.exports = {
  entry: { app: path.resolve(__dirname, "../src/index.tsx") },

  resolve: {
    extensions: [".js", ".ts", ".jsx", ".tsx"],
    alias: {
      "@": path.resolve(__dirname, "../src")
    }
  },

  plugins: [
    new HtmlWebpackPlugin({ template: "./public/index.html" }),
    new ForkTsCheckerWebpackPlugin({
      workers: 1,
      tslint: true
    })
  ],

  module: {
    rules: [
      {
        enforce: "pre",
        test: /\.(ts|tsx)?$/,
        use: [
          {
            loader: "tslint-loader",
            options: {
              typeCheck: true,
              fix: true
            }
          }
        ]
      },
      {
        test: /\.(ts|tsx)?$/,
        loader: "ts-loader",
        exclude: /node_modules/,
        options: { transpileOnly: true }
      },
      {
        test: /\.css$/,
        loaders: [
          "style-loader",
          { loader: "css-loader", options: { url: false } }
        ]
      }
    ]
  }
};
