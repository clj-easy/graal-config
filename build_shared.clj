(ns build-shared)

;;;; Command line parsing

;; This processes command line args like
;;
;; $ bb native-image-test :dir config/com.h2database/h2/example :graalvm-version 21.2.0
;;
;; and defines a var for each command line arg to be used within tasks.

(doseq [[arg-name arg-val] (partition 2 *command-line-args*)]
  (intern *ns* (symbol (subs arg-name 1)) arg-val))

;; Helper to define defaults for optional arguments

(defn def-default [sym default]
  (when-not (resolve sym)
    (intern *ns* sym default)))

(def-default 'graalvm-version "21.1.0")

;;;; End command line parsing
