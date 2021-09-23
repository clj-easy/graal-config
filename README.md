# Graal config

A repository containing GraalVM `native-image` configurations to be used as git
libraries in `deps.edn`.

## How to use

- Include this library as a git dependency in your native image classpath.
- Set `:deps/root` to `"config/<org>/<lib>"`
- In addition to this, include `com.github.clj-easy/graal-build-time` which automatically configures `--initialize-at-build-time` with clojure packages.
- Then build your native image!

Full example for `cheshire/cheshire` (update versions as necessary):

``` clojure
{:deps
 {cheshire/cheshire {:mvn/version "5.10.0"}
  com.github.clj-easy/graal-build-time {:mvn/version "0.1.3"}
  com.github.clj-easy/graal-config {:git/sha "b06e33694d2c878169958f7317ea01d9c0353ab4"
                                    :deps/root "config/cheshire/cheshire"}}}
```


## Supported libraries

  | Library name                                            | Config path                         |
  |---------------------------------------------------------|-------------------------------------|
  | [com.taoensso/nippy](https://github.com/ptaoussanis/nippy)  | [link](./config/com.taoensso/nippy) |
  | [cheshire/cheshire](https://github.com/dakrone/cheshire)    | [link](./config/cheshire/cheshire)  |

## Tested GraalVM versions

   - GraalVM 21.1.0
   - GraalVM 21.2.0
   - GraalVM DEV build

## Contributing

1. Clone the repository.
2. Copy the recipe `cp -R recipe/* config/organization/library`.
3. Provide `META-INF/organization/library/native-image.properties` with necessary native configurations.

### Tests

Run `bb native-image-test :dir config/<org>/<lib>/example :graalvm-version <graalvm-version>`.
E.g:

``` clojure
$ bb native-image-test :dir config/com.taoensso/nippy/example :graalvm-version 21.1.2`.
```
