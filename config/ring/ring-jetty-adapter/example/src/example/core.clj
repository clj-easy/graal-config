(ns example.core
  (:require
   [clojure.java.shell :refer [sh]]
   [ring.adapter.jetty :as jetty])
  (:import
   [org.eclipse.jetty.server Server])
  (:gen-class))

(defn -main
  [& _args]
  (let [^Server server (jetty/run-jetty
                        (fn [req]
                          {:status 200
                           :headers {}
                           :body "hello world"})
                        {:join? false
                         :port 3000})]
    (= (:out (sh "curl" "http://localhost:3000")) "hello world")
    (.stop server)
    (shutdown-agents)))
