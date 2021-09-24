(ns build-shared)

(def args (apply hash-map *command-line-args*))

(doseq [[arg-name arg-val] args]
  (intern *ns* (symbol (subs arg-name 1)) arg-val))

(defn def-default [sym default]
  (when-not (resolve sym)
    (intern *ns* sym default)))

(def-default 'graalvm-version "21.1.0")
