(ns build-shared)

(def version "0.0.1")

(let [[& {dir ":dir"
          graalvm-version ":graalvm-version"}]  *command-line-args*]
  (def dir dir)
  (def graalvm-version graalvm-version))

(assert dir "Must provide :dir argument")
(assert graalvm-version "Must provide :graalvm-version")
