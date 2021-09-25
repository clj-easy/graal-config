(ns example.core
  (:require
   [io.pedestal.log :as log])
  (:gen-class))

(defn -main
  [& _args]
  (log/error :in 'my-fn :message "this is a message")
  (log/error :hello "world")
  (log/info :hello "world"))
