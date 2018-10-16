package main

import (
	"net/http"
	"os"

	"log"

	"github.com/gorilla/pat"
	"github.com/markbates/goth"
	"github.com/markbates/goth/gothic"
	"github.com/markbates/goth/providers/github"
)

func main() {
	goth.UseProviders(
		github.New(os.Getenv("CLIENT_ID"), os.Getenv("CLIENT_SECRET"), "http://localhost:3000/auth/github/callback"),
	)

	p := pat.New()
	p.Get("/auth/{provider}/callback", func(w http.ResponseWriter, r *http.Request) {

		user, err := gothic.CompleteUserAuth(w, r)
		if err != nil {
			w.WriteHeader(http.StatusUnauthorized)
			return
		}
		cookie, _ := r.Cookie("redirect_url")
		redirectURL := cookie.Value + "?access_token=" + user.AccessToken
		http.Redirect(w, r, redirectURL, http.StatusSeeOther)
	})

	p.Get("/auth/{provider}", func(w http.ResponseWriter, r *http.Request) {
		redirectURL := r.URL.Query().Get("redirect_url")
		cookie := &http.Cookie{
			Name:  "redirect_url",
			Value: redirectURL,
		}
		http.SetCookie(w, cookie)
		gothic.BeginAuthHandler(w, r)
	})

	log.Fatal(http.ListenAndServe(":3000", p))
}
