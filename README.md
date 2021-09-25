# Graal config

A repository containing GraalVM `native-image` configurations to be used as git
libraries in `deps.edn`.

## How to use

- Include this library as a git dependency in your native image classpath.
- Set `:deps/root` to `"config/<org>/<lib>"`
- In addition to this, include `com.github.clj-easy/graal-build-time` which automatically configures `--initialize-at-build-time` with clojure packages.
- Then build your native image!

Full example for both `cheshire/cheshire` and `com.taoensso/nippy` in one build:
(update versions as necessary):

``` clojure
{:deps
 {cheshire/cheshire                         {:mvn/version "5.10.0"}
  com.taoensso/nippy                        {:mvn/version "3.1.1"}
  com.github.clj-easy/graal-config-cheshire {:git/url   "https://github.com/clj-easy/graal-config"
                                             :git/sha   "b06e33694d2c878169958f7317ea01d9c0353ab4"
                                             :deps/root "config/cheshire/cheshire"}
  com.github.clj-easy/graal-config-nippy    {:git/url   "https://github.com/clj-easy/graal-config"
                                             :git/sha   "b06e33694d2c878169958f7317ea01d9c0353ab4"
                                             :deps/root "config/com.taoensso/nippy"}}}
```

## Supported libraries

  | Library name                                                                   | Config path                                        | Additional steps                         |
  |--------------------------------------------------------------------------------|----------------------------------------------------|------------------------------------------|
  | [com.taoensso/nippy](https://github.com/ptaoussanis/nippy)                     | [link](./config/com.taoensso/nippy)                | None                                     |
  | [cheshire/cheshire](https://github.com/dakrone/cheshire)                       | [link](./config/cheshire/cheshire)                 | None                                     |
  | [com.h2database/h2](https://github.com/h2database/h2database)                  | [link](./config/com.h2database/h2)                 | `"--allow-incomplete-classpath"` *(1\*)* |
  | [com.github.seancorfield/next.jdbc](https://github.com/seancorfield/next-jdbc) | [link](./config/com.github.seancorfield/next.jdbc) | None                                     |
  | [org.slf4j/slf4j-simple](https://github.com/qos-ch/slf4j/tree/master/slf4j-simple) | [link](./config/org.slf4j/slf4j-simple) | None                                     |

1) Vote [here](https://github.com/oracle/graal/issues/1664), so that we can `allow-incomplete-classpath` for only the specific cases.

## Tested GraalVM versions

   - GraalVM 21.1.0
   - GraalVM 21.2.0
   - GraalVM DEV build

## Contributing

1. Clone the repository.
2. Run `bb recipe :org <org> :lib <lib>`
3. Provide config in
   `META-INF/native-image/organization/library/native-image.properties` +
   additional JSON files if necessary.

### Tests

Run `bb native-image-test :dir config/<org>/<lib>/example :graalvm-version <graalvm-version>`.
E.g:

``` clojure
$ bb native-image-test :dir config/com.taoensso/nippy/example :graalvm-version 21.1.2`.
```
