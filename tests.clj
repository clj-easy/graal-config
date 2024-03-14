(require
 '[babashka.process :as p]
 '[clojure.string :as s]
 '[clojure.java.shell :as csh])

(defn- shell
  [inherit? cmd & args]
  (p/process (into (p/tokenize cmd) (remove nil? args)) {:inherit inherit?}))


(defn github-graalvm-version
  []
  (let [result (s/trim (:out (csh/sh (str (System/getenv "GRAALVM_HOME") "/bin/native-image") "--version")))
        graalvm-version (second (first (re-seq #"GraalVM ([A-Za-z\.0-9-]+)" result)))]
    (println "> GraalVM Version" graalvm-version)
    graalvm-version))
(def configs
  [{:name     "ring/ring-jetty-adapter"}
   {:name     "com.taoensso/nippy"}
   {:name     "cheshire/cheshire"}
   {:name     "com.h2database/h2"}
   {:name     "com.github.seancorfield/next.jdbc"}
   {:name     "org.slf4j/slf4j-simple"}
   {:name     "clj-http/clj-http"}
   {:name     "clj-easy/nippy-mikera-vectorz"}])

(defn config->root-path
  [config]
  (str "config/" (:name config)))

(defn config->test-path
  [config]
  (str (config->root-path config) "/example"))

(defn should-test?
  [config]
  (= 1 (:exit @(shell false "git" "diff" "--exit-code" "HEAD~1" "HEAD" "--" (config->root-path config)))))

(defn run-tests!
  []
  (doseq [config configs
          :let [run-tests (fn [version]
                            (let [result @(shell true (str "bash -c \"bb native-image-test :dir " (config->test-path config) " :graalvm-version " "provided" "\""))]
                              (if (= 0 (:exit result))
                                (println ">>> Successful test!")
                                (System/exit 1)))
                            (println ">> Running tests for config:" (:name config) "GraalVM version:" version "...Success!"))]]
    (if (and (not (System/getenv "LOCAL")) (not (should-test? config)))
      (println "> Skipping tests for config:" (:name config))
      (do
        (println "> Running tests for config:" (:name config))
        (if (System/getenv "LOCAL")
          (doseq [version (:versions config)]
            (run-tests version))
          (run-tests "provided"))))))

(run-tests!)
