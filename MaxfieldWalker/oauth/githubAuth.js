// 参考: http://andreybleme.com/2018-02-24/oauth-github-web-flow-cors-problem/

var axios = require("axios");
var express = require("express");
var app = express();

app.get("/my-oauth", function(req, res) {
  const GITHUB_AUTH_ACCESSTOKEN_URL =
    "https://github.com/login/oauth/access_token";
  axios({
    method: "post",
    url: GITHUB_AUTH_ACCESSTOKEN_URL,
    data: {
      client_id: req.query["client_id"],
      client_secret: req.query["client_secret"],
      code: req.query["code"]
    }
  })
    .then(function(response) {
      res.setHeader("Access-Control-Allow-Origin", "*");
      res.send(response.data);
      console.log("Success " + response);
    })
    .catch(function(error) {
      console.error("Error " + error.message);
    });
});

app.listen(3000, function() {
  console.log("my-oauth listening on port 3000!");
});
