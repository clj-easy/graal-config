(ns example.core
  (:require
   [clj-http.client :as client])
  (:gen-class))

(defn -main
  [& _args]
  (println (client/post "http://example.com" {:cookie-policy :standard}))
  (println "Swagger Version (Cheshire included)"
           (:swagger (:body (client/get "https://generator.swagger.io/api/swagger.json"
                                        {:accept :json
                                         :as :json})))))
