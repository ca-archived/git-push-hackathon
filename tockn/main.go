package main

import (
	"encoding/json"
	"net/http"
	"os"

	"log"

	"github.com/gorilla/pat"
	"github.com/markbates/goth"
	"github.com/markbates/goth/gothic"
	"github.com/markbates/goth/providers/github"
)

type AccessToken struct {
	AccessToken string `json:"access_token"`
}

func main() {
	goth.UseProviders(
		github.New(os.Getenv("CLIENT_ID"), os.Getenv("CLIENT_SECRET"), "http://localhost:3000/auth/github/callback"),
	)

	p := pat.New()
	p.Get("/auth/{provider}/callback", func(res http.ResponseWriter, req *http.Request) {

		user, err := gothic.CompleteUserAuth(res, req)
		if err != nil {
			res.WriteHeader(http.StatusUnauthorized)
			return
		}
		a := AccessToken{user.AccessToken}
		json.NewEncoder(res).Encode(a)
	})

	p.Get("/auth/{provider}", func(res http.ResponseWriter, req *http.Request) {
		gothic.BeginAuthHandler(res, req)
	})

	log.Fatal(http.ListenAndServe(":3000", p))
}
