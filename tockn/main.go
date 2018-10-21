package main

import (
	"fmt"
	"net/http"
	"os"

	"log"

	"github.com/gorilla/pat"
	"github.com/markbates/goth"
	"github.com/markbates/goth/gothic"
	"github.com/markbates/goth/providers/github"
)

func main() {
	apiEndPoint := os.Getenv("API_ENDPOINT")
	redirectURL := os.Getenv("REDIRECT_URL")

	goth.UseProviders(
		github.New(os.Getenv("CLIENT_ID"), os.Getenv("CLIENT_SECRET"), apiEndPoint+"/auth/github/callback", "gist"),
	)

	p := pat.New()
	p.Get("/auth/{provider}/callback", func(w http.ResponseWriter, r *http.Request) {

		user, err := gothic.CompleteUserAuth(w, r)
		if err != nil {
			w.WriteHeader(http.StatusUnauthorized)
			return
		}
		cookie, _ := r.Cookie("path")
		URL := fmt.Sprintf("%s/auth/callback?access_token=%s&path=%s", redirectURL, user.AccessToken, cookie.Value)
		http.Redirect(w, r, URL, http.StatusSeeOther)
	})

	p.Get("/auth/{provider}", func(w http.ResponseWriter, r *http.Request) {
		URL := r.URL.Query().Get("path")
		cookie := &http.Cookie{
			Name:  "path",
			Value: URL,
			Path:  "/",
		}
		http.SetCookie(w, cookie)
		gothic.BeginAuthHandler(w, r)
	})

	log.Fatal(http.ListenAndServe(":3000", p))
}
