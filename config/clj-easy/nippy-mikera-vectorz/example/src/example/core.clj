(ns example.core
  (:require
   [clojure.core.matrix :as matrix]
   [taoensso.nippy :as nippy])
  (:import
   [java.time LocalDate]
   [mikera.vectorz Vector Vector1 Vector2 Vector3 Vector3 Vector4 Vectorz])
  (:gen-class))

(set! *warn-on-reflection* true)

(matrix/set-current-implementation :vectorz)

(nippy/extend-freeze
 Vector
 1
 [^Vector x data-output]
 (nippy/freeze-to-out! data-output (.asDoubleArray x)))

(nippy/extend-thaw
 1
 [data-input]
 (Vector/wrap ^doubles (nippy/thaw-from-in! data-input)))


(nippy/extend-freeze
 Vector1
 2
 [^Vector1 x data-output]
 (nippy/freeze-to-out! data-output (.toDoubleArray x)))


(nippy/extend-thaw
 2
 [data-input]
 (Vectorz/create ^doubles (nippy/thaw-from-in! data-input)))

(nippy/extend-freeze
 Vector2
 3
 [^Vector2 x data-output]
 (nippy/freeze-to-out! data-output (.toDoubleArray x)))


(nippy/extend-thaw
 3
 [data-input]
 (Vectorz/create ^doubles (nippy/thaw-from-in! data-input)))

(nippy/extend-freeze
 Vector3
 4
 [^Vector3 x data-output]
 (nippy/freeze-to-out! data-output (.toDoubleArray x)))


(nippy/extend-thaw
 4
 [data-input]
 (Vectorz/create ^doubles (nippy/thaw-from-in! data-input)))

(nippy/extend-freeze
 Vector4
 5
 [^Vector4 x data-output]
 (nippy/freeze-to-out! data-output (.toDoubleArray x)))

(nippy/extend-thaw
 5
 [data-input]
 (Vectorz/create ^doubles (nippy/thaw-from-in! data-input)))


(defn -main
  [& _args]
  (println
   "Roundrip:"
   (nippy/thaw
    (nippy/freeze
     {:date (LocalDate/now)
      :vector1 (Vector1. 1.0)
      :vector2 (Vector2. 1.0 2.0)
      :vector3 (Vector3. 1.0 2.0 3.0)
      :vector4 (Vector4. 1.0 2.0 3.0 4.0)
      :vector-n (Vectorz/create ^doubles [1 2 3 4 5 6 7 8 9 10])
      :matrix (matrix/matrix [1 2 3 4 5 6 7])}))))
