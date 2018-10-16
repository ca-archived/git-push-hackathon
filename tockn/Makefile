deps:
	which dep || go get -u github.com/golang/dep/cmd/dep
	dep ensure

dev:
	go run main.go &
	npm run dev --prefix view
