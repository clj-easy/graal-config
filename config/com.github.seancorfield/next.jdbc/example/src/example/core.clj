(ns example.core
  (:import
   [java.sql SQLException])
  (:require
   [next.jdbc :as jdbc])
  (:gen-class))

(set! *warn-on-reflection* true)

(defn -main
  [& _args]
  (try
    (jdbc/get-connection {:dbtype "h2" :dbname "example"})
    (catch SQLException ^SQLException err
      (if (= (.getMessage ^SQLException err)
             "No suitable driver found for jdbc:h2:./example")
        (println "We're good. See h2 example. Now we only test if next.jdbc connection is working. Full case is covered by h2 example.")
        (System/exit 1)))))
