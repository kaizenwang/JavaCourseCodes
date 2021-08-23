package main

import (
	"flag"
	"fmt"
	"log"
	"net/http"
)

var port = flag.String("port", "", "")

func handler(w http.ResponseWriter, r *http.Request) {
	fmt.Fprintf(w, "Hello "+*port)
}

func main() {
	flag.Parse()
	http.HandleFunc("/", handler)
	log.Fatal(http.ListenAndServe(*port, nil))
}
