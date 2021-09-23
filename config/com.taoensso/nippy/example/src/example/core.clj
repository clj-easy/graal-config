(ns example.core
  (:require
   [taoensso.nippy :as nippy])
  (:gen-class))

(defn -main
  [& _args]
  (prn (into [] (nippy/freeze nippy/stress-data)))
  (prn (nippy/thaw (nippy/freeze nippy/stress-data))))
