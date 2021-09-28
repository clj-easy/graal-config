(ns example.core
  (:require [net.cgrand.xforms :as x])
  (:gen-class))

(defn -main
  [& _args]
  (println (sequence (x/partition 4 (x/reduce +)) (range 16)))
  )
