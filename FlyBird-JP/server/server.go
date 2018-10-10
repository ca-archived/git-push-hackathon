package main

import(
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
	router.HandleFunc("/auth", auth).Methods("GET")
	router.HandleFunc("/token", token).Methods("POST")
	
	listener, err := net.Listen("tcp", "127.0.0.1:49651")
	if err != nil {
		panic(err.Error())
	}
	defer listener.Close()

	fcgi.Serve(listener, router)
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
	bytes, err := ioutil.ReadAll(req.Body)
	defer req.Body.Close()
	if err != nil {
    	panic(err.Error())
	}

	var reqJson interface{}
	err = json.Unmarshal(bytes, &reqJson)

	service := reqJson.(map[string]interface{})["service"]
	code := reqJson.(map[string]interface{})["code"]

	if service != nil && code != nil {
		var token *oauth2.Token
		switch service.(string) {
			case "github":	token, err = github.Exchange(oauth2.NoContext, code.(string))
		}
  		if err != nil {
    		panic(err.Error())
		}
		resJson := map[string]string{}
		resJson["service"] = service.(string)
		resJson["accessToken"] = token.AccessToken
	  
		respondWithJson(resJson, res)
	} else {
		res.WriteHeader(http.StatusNotFound)
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