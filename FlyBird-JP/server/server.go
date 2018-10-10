package main

import(
	"fmt"
	"net"
	"net/http"
	"net/http/fcgi"
	"net/url"
	"path/filepath"
	"io/ioutil"
)

const(
	clientSecretPath = "../setting/client_secret.txt"
	clientIdPath = "../setting/client_id.txt"
	endpoint = "https://github.com/login/oauth/access_token"
)

var clientSecret string
var clientId string

func main(){
	path,_ := filepath.Abs(clientSecretPath)
	bytes, err := ioutil.ReadFile(path)
	if err != nil{
		panic(err.Error())
	}
	clientSecret = string(bytes)

	path,_ = filepath.Abs(clientIdPath)
	bytes, err = ioutil.ReadFile(path)
	if err != nil{
		panic(err.Error())
	}
	clientId = string(bytes)

	http.HandleFunc("/auth", auth)
	http.HandleFunc("/auth_callback", authCallback)
	
	listener, err := net.Listen("tcp", "127.0.0.1:49651")
	if err != nil {
		panic(err.Error())
	}
	defer listener.Close()

	fcgi.Serve(listener, nil)
}

func auth(res http.ResponseWriter, req *http.Request) {
	http.Redirect(res, req, "https://github.com/login/oauth/authorize?client_id=" + clientId, 303)
}

func authCallback(res http.ResponseWriter, req *http.Request) {
	parameter := req.URL.Query()
	code := parameter["code"][0]

	values := url.Values{}
	values.Add("client_id", clientId)
	values.Add("client_secret", clientSecret)
	values.Add("code", code)

	postRes, err := http.PostForm(endpoint, values)
	if err != nil {
		panic(err.Error())
	}

	if postRes.StatusCode == 200 {
		defer postRes.Body.Close()
		body,_ := ioutil.ReadAll(postRes.Body)
		bodyString := string(body)
		data,_ := url.ParseQuery(bodyString)
		
		fmt.Fprint(res, 
			`<!DOCTYPE html>
			<html>
			<head>
				<title>Gist</title>
			</head>
			<body>
				<script>localStorage.setItem("accessToken", "` + data["access_token"][0] + `");location.href = new URL(location.href).origin;</script>
			</body>
			</html>`)
	} else {
		http.Redirect(res, req, "/", 303)
	}
}