# Compatibility matrix

| GraalVM 21.1.0 | GraalVM 21.2.0 | GraalVM DEV 21.3.0-dev-20210914_2058 |
|----------------|----------------|--------------------------------------|
| :x:            | :x:            |:white_check_mark:                    |

# Usage
Add following dependency to your `deps.edn`

``` clojure
com.github.clj-easy/graal-config  {:git/sha "<latest-sha>"
                                   :deps/root "config/com.taoensso/nippy"}
```

