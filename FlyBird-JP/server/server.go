package main

import(
	"strconv"
	"net"
	"net/http"
	"net/http/fcgi"
	"path/filepath"
	"encoding/json"
	"io/ioutil"
	"golang.org/x/oauth2"
	"github.com/gorilla/mux"
)

const(
	clientSecretPath = "../setting/client_secret.txt"
	clientIdPath = "../setting/client_id.txt"
	port = 49651
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
		ClientID : clientId,
		ClientSecret: clientSecret,
		Endpoint: oauth2.Endpoint{
			AuthURL:  "https://github.com/login/oauth/authorize",
			TokenURL: "https://github.com/login/oauth/access_token",
		},
		Scopes: []string{"gist", "read:user"},
	  }

	router := mux.NewRouter().StrictSlash(false)
	router.HandleFunc("/auth/{service}", auth).Methods("GET")
	router.HandleFunc("/token/{service}", token).Methods("POST")
	
	listener, err := net.Listen("tcp", "127.0.0.1:" + strconv.Itoa(port))
	if err != nil {
		panic(err.Error())
	}
	defer listener.Close()

	fcgi.Serve(listener, router)
}

func auth(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	service := vars["service"]

	var authUrl string
	state := "test"
	switch service {
		case "github":	authUrl = github.AuthCodeURL(state)
		default: authUrl = "/"
	}
	http.Redirect(res, req, authUrl, 303)
}

func token(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	service := vars["service"]

	bytes, err := ioutil.ReadAll(req.Body)
	defer req.Body.Close()
	if err != nil {
    	panic(err.Error())
	}

	var reqJson interface{}
	err = json.Unmarshal(bytes, &reqJson)
	code := reqJson.(map[string]interface{})["code"]

	if code != nil {
		var token *oauth2.Token
		switch service {
			case "github":	token, err = github.Exchange(oauth2.NoContext, code.(string))
		}
  		if  token == nil || err != nil {
    		res.WriteHeader(http.StatusBadRequest)
		} else {
			resJson := map[string]string{}
			resJson["service"] = service
			resJson["accessToken"] = token.AccessToken
			respondWithJson(resJson, res)
		}
	} else {
		res.WriteHeader(http.StatusBadRequest)
	}
}

func respondWithJson(data interface{}, res http.ResponseWriter) {
	res.Header().Set("Content-Type", "application/json")
	encorder := json.NewEncoder(res)
	encorder.SetIndent("", "\t")

	err := encorder.Encode(data)
	if err != nil {
		panic(err.Error())
	}
}