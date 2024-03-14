(ns example.core
  (:require
   [clojure.core.matrix :as matrix]
   [taoensso.nippy :as nippy])
  (:import
   [mikera.vectorz Vector])
  (:gen-class))

(set! *warn-on-reflection* true)

(matrix/set-current-implementation :vectorz)

(nippy/extend-freeze
 Vector
 :mikera.vectorz/Vector
 [^Vector x data-output]
 (nippy/freeze-to-out! data-output (.getElements x)))

(nippy/extend-thaw
 :mikera.vectorz/Vector
 [data-input]
 (Vector/wrap ^doubles (nippy/thaw-from-in! data-input)))

(defn -main
  [& _args]
  (println
   "Roundrip:" (nippy/thaw (nippy/freeze (matrix/matrix [1 2 3 4])))))
