;; see https://ask.clojure.org/index.php/10905/control-transient-deps-that-compiled-assembled-into-uberjar?show=10913#c10913
(require 'clojure.tools.deps.alpha.util.s3-transporter)

(ns build
  (:require [clojure.tools.build.api :as b]))

(def basis (b/create-basis {:project "deps.edn"}))
(def class-dir "target/classes")

(defn compile-sources [_]
  (b/compile-clj {:basis basis
                  :class-dir class-dir
                  :src-dirs ["src"]
                  :ns-compile '[example.core]}))

(defn uber [{:keys [uber-file]}]
  (println "Compiling...")
  (compile-sources {})
  (println "Uberjarring...")
  (b/uber {:class-dir class-dir
           :basis basis
           :uber-file uber-file
           :main 'example.core}))
