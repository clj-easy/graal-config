(require '[babashka.process :as p])

(defn- shell
  [inherit? cmd & args]
  (p/process (into (p/tokenize cmd) (remove nil? args)) {:inherit inherit?}))

(def DEV_BUILD "21.3.0-dev")
(def ONE_RELEASE_BEFORE_LATEST_CE "21.1.0")
(def LATEST_CE "21.2.0")

(def versions<->home
  {DEV_BUILD                    (str "~/.graal-" DEV_BUILD)
   ONE_RELEASE_BEFORE_LATEST_CE (str "~/.graal-" ONE_RELEASE_BEFORE_LATEST_CE)
   LATEST_CE                    (str "~/.graal-" LATEST_CE)})

(def ALL [DEV_BUILD LATEST_CE ONE_RELEASE_BEFORE_LATEST_CE])
(def STABLE_BUILDS [LATEST_CE ONE_RELEASE_BEFORE_LATEST_CE])

(def configs
  [{:name     "com.taoensso/nippy"
    :versions [DEV_BUILD]}
   {:name     "cheshire/cheshire"
    :versions ALL}
   {:name     "com.h2database/h2"
    :versions ALL}
   {:name     "com.github.seancorfield/next.jdbc"
    :versions ALL}
   {:name     "org.slf4j/slf4j-simple"
    :versions ALL}])

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
    (if (and (not (System/getenv "TEST_ALL"))(not (should-test? config)))
      (println "> Skipping tests for config:" (:name config))
      (do
        (println "> Running tests for config:" (:name config))
        (doseq [version (:versions config)]
          (println ">> Running tests for config:" (:name config) "GraalVM version:" version)
          (let [result @(shell true (str "bash -c \"export GRAALVM_HOME=" (versions<->home version) "; bb native-image-test :dir " (config->test-path config) " :graalvm-version " (versions<->home version) "\""))]
            (if (= 0 (:exit result))
              (println ">>> Successful test!")
              (System/exit 1)))
          (println ">> Running tests for config:" (:name config) "GraalVM version:" version "...Success!"))))))

(run-tests!)
