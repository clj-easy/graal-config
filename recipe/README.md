# Compatibility matrix

| GraalVM 21.1.0 | GraalVM 21.2.0 | GraalVM DEV 21.3.0-dev-20210914_2058 |
|----------------|----------------|--------------------------------------|
| :x:            | :x:            |:x:                                   |

# Usage
Add following dependency to your `deps.edn`

``` clojure
com.github.clj-easy/graal-config  {:git/sha "<latest-sha>"
                                   :deps/root "config/<organization>/<library_name>"}
```


**Works best in conjunction with clj-easy/graal-build-time**

``` clojure
com.github.clj-easy/graal-build-time {:mvn/version "<latest_release>"}
```
