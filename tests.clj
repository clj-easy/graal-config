(require
 '[babashka.process :as p]
 '[clojure.string :as s]
 '[clojure.java.shell :as csh])

(defn- shell
  [inherit? cmd & args]
  (p/process (into (p/tokenize cmd) (remove nil? args)) {:inherit inherit?}))

(def DEV_BUILD "21.3.0-dev")
(def THIRD_LATEST_CE "21.1.0")
(def SECOND_LATEST_CE "21.2.0")
(def LATEST_CE "21.3.0")

(defn github-graalvm-version
  []
  (let [result (s/trim (:out (csh/sh (str (System/getenv "GRAALVM_HOME") "/bin/native-image") "--version")))
        graalvm-version (second (first (re-seq #"GraalVM ([A-Za-z\.0-9-]+)" result)))]
    (println "> GraalVM Version" graalvm-version)
    graalvm-version))

(def versions<->home-local
  {DEV_BUILD        (str "~/.graal-" DEV_BUILD)
   SECOND_LATEST_CE (str "~/.graal-" SECOND_LATEST_CE)
   THIRD_LATEST_CE  (str "~/.graal-" THIRD_LATEST_CE)
   LATEST_CE        (str "~/.graal-" LATEST_CE)})

(def ALL [DEV_BUILD LATEST_CE SECOND_LATEST_CE THIRD_LATEST_CE])
(def STABLE_BUILDS [LATEST_CE SECOND_LATEST_CE THIRD_LATEST_CE])

(def configs
  [{:name     "ring/ring-jetty-adapter"
    :versions ALL}
   {:name     "com.taoensso/nippy"
    :versions (conj [LATEST_CE] DEV_BUILD)}
   {:name     "cheshire/cheshire"
    :versions ALL}
   {:name     "com.h2database/h2"
    :versions ALL}
   {:name     "com.github.seancorfield/next.jdbc"
    :versions ALL}
   {:name     "org.slf4j/slf4j-simple"
    :versions ALL}
   {:name     "clj-http/clj-http"
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
  (doseq [config configs
          :let [run-tests (fn [version]
                            (let [result @(shell true (str "bash -c \"bb native-image-test :dir " (config->test-path config) " :graalvm-version " (or (versions<->home-local version) "provided") "\""))]
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
          (do
            @(shell true (str (System/getenv "GRAALVM_HOME") "/bin/gu") "install" "native-image")
            (let [versions (:versions config)
                  version (github-graalvm-version)]
              (when (or (contains? (set versions) version)
                        (and (s/includes? version "dev")
                             (contains? (set versions) DEV_BUILD)))
                (run-tests "provided")))))))))

(run-tests!)
