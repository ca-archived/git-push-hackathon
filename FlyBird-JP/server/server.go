package main

import(
	"os"
	"strings"
	"strconv"
	"net/http"
	"path/filepath"
	"encoding/json"
	"io/ioutil"
	"golang.org/x/oauth2"
	"github.com/gorilla/mux"
	"github.com/google/uuid"
)

const(
	clientSecretPath = "../env/client_secret.txt"
	clientIdPath = "../env/client_id.txt"
	certPath =  "../env/cert.pem"
	keyPath =  "../env/key.pem"
	rootPath = "../client"
	port = 49650
)

var github *oauth2.Config
var stateMap map[string]string = map[string]string{}

func main(){
	path,_ := filepath.Abs(clientSecretPath)
	bytes, err := ioutil.ReadFile(path)
	if err != nil {
		panic(err.Error())
	}
	clientSecret := strings.TrimRight(string(bytes), "\n")

	path,_ = filepath.Abs(clientIdPath)
	bytes, err = ioutil.ReadFile(path)
	if err != nil {
		panic(err.Error())
	}
	clientId := strings.TrimRight(string(bytes), "\n")

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
	router.NotFoundHandler = http.HandlerFunc(handler)

	http.ListenAndServeTLS(":" + strconv.Itoa(port), certPath, keyPath, router)
}

var fileServer http.Handler = http.FileServer(http.Dir(rootPath))

func getClientId(req *http.Request) (string){
	return strings.Split(req.RemoteAddr, ":")[0] + "_git-push-hackathon_" + req.Header.Get("User-Agent")
}

func Exists(path string) bool {
    _, err := os.Stat(path)
    return err == nil
}

func handler(res http.ResponseWriter, req *http.Request) {
	if Exists(rootPath + req.URL.Path) {
		fileServer.ServeHTTP(res, req)
	} else {
		http.ServeFile(res, req, rootPath + "/index.html")
	}
}

func auth(res http.ResponseWriter, req *http.Request) {
	vars := mux.Vars(req)
	service := vars["service"]

	state := uuid.New().String()
	cliendId := getClientId(req)
	stateMap[cliendId] = state

	var authUrl string
	switch service {
		case "github":	authUrl = github.AuthCodeURL(state)
		default: authUrl = "/"
	}
	http.Redirect(res, req, authUrl, 302)
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
	state := reqJson.(map[string]interface{})["state"]
	cliendId := getClientId(req)

	if code != nil && stateMap[cliendId] == state {
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

	delete(stateMap, cliendId)
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