(require '[babashka.process :as p])

(defn- shell
  [inherit? cmd & args]
  (p/process (into (p/tokenize cmd) (remove nil? args)) {:inherit inherit?}))

(def versions<->home
  {"21.3.0-dev" "~/graal-21.3.0-dev"
   "21.1.0"     "~/graal-21.1.0"
   "21.2.0"     "~/graal-21.2.0"})

(def configs
  [{:name "com.taoensso/nippy"
    :versions ["21.3.0-dev"]}])

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
  (doseq [config configs]
    (if-not (should-test? config)
      (println "> Skipping tests for config:" (:name config))
      (do
        (println "> Running tests for config:" (:name config))
        (doseq [version (:versions config)]
          (println ">> Running tests for config:" (:name config) "GraalVM version:" version)
          @(shell true (str "bash -c \"export GRAALVM_HOME=" (versions<->home version) "; bb native-image-test :dir " (config->test-path config) " :graalvm-version " (versions<->home version) "\""))
          (println ">> Running tests for config:" (:name config) "GraalVM version:" version "...Success!"))))))

(run-tests!)
