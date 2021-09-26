(ns example.core
  (:require
   [clojure.java.shell :refer [sh]]
   [clj-test-containers.core :as tc])
  (:gen-class))

(defn -main
  [& _args]
  (try
    (let [container (-> (tc/create {:image-name "postgres:12.1"
                                    :exposed-ports [5432]
                                    :env-vars {"POSTGRES_PASSWORD" "verysecret"}})
                        (tc/bind-filesystem! {:host-path "/tmp"
                                              :container-path "/opt"
                                              :mode :read-only})
                        (tc/start!))
          _ (println "CONTAINER_STARTED")]
      (println container)
      (tc/stop! container))
    (catch Exception ex
      (println ex))))
