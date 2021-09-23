(ns example.core
  (:require
   [clojure.java.io :as io]
   [cheshire.core :as json])
  (:gen-class))

(def data {:hello "world"
           ;; :date (java.util.Date. 0)
           :world {"Hello" "world"}})


(defn -main
  [& _args]
  (spit "some.json" (json/encode {:hello "world"}))
  (json/parse-stream (io/reader "some.json"))
  (println (-> data json/encode json/decode))
  (println (-> data (json/encode {:pretty true}) json/decode))
  (println (-> data (json/encode {:pretty true}) json/decode))
  (println (-> data (json/encode {:pretty true}) ((fn [x] (json/decode x true))))))
