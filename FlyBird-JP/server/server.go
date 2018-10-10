package main

import(
	"fmt"
	"net"
	"net/http"
	"net/http/fcgi"
	"path/filepath"
	"io/ioutil"
	"golang.org/x/oauth2"
)

const(
	clientSecretPath = "../setting/client_secret.txt"
	clientIdPath = "../setting/client_id.txt"
)

var github *oauth2.Config

func main(){
	path,_ := filepath.Abs(clientSecretPath)
	bytes, err := ioutil.ReadFile(path)
	if err != nil{
		panic(err.Error())
	}
	clientSecret := string(bytes)

	path,_ = filepath.Abs(clientIdPath)
	bytes, err = ioutil.ReadFile(path)
	if err != nil{
		panic(err.Error())
	}
	clientId := string(bytes)

	github = &oauth2.Config{
		ClientID :     clientId,
		ClientSecret: clientSecret,
		Endpoint:    oauth2.Endpoint{
			AuthURL:  "https://github.com/login/oauth/authorize",
			TokenURL: "https://github.com/login/oauth/access_token",
		},
		Scopes:       []string{"gist", "read:user"},
	  }

	http.HandleFunc("/auth", auth)
	http.HandleFunc("/token", token)
	
	listener, err := net.Listen("tcp", "127.0.0.1:49651")
	if err != nil {
		panic(err.Error())
	}
	defer listener.Close()

	fcgi.Serve(listener, nil)
}

func auth(res http.ResponseWriter, req *http.Request) {
	parameter := req.URL.Query()
	service := parameter["service"][0]

	var authUrl string
	state := "test"
	switch service {
		case "github":	authUrl = github.AuthCodeURL(state)
		default: authUrl = "/"
	}
	http.Redirect(res, req, authUrl, 303)
}

func token(res http.ResponseWriter, req *http.Request) {
	parameter := req.URL.Query()
	code := parameter["code"][0]
	service := parameter["service"][0]

	var token *oauth2.Token
	var err error
	switch service {
		case "github":	token, err = github.Exchange(oauth2.NoContext, code)
	}
  	if err != nil {
    	panic(err.Error())
  	}

	fmt.Fprint(res, 
		`<!DOCTYPE html>
		<html>
		<head>
			<title>Gist</title>
		</head>
		<body>
			<script>localStorage.setItem("`+ service +`AccessToken", "` + token.AccessToken + `");location.href = "/";</script>
		</body>
		</html>`)
}