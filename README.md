# Graal Configurations Collection

The repository for distributing the GraalVM `native-image`configurations via simple `deps.edn` subproject recipes.

## How to use

   1. Find the library you would like to use.
   2. Read it's README.md instructions.

## Supported libraries

  | Library name                                           | Config path                     |
  |--------------------------------------------------------|---------------------------------|
  | [taoensso.nippy](https://github.com/ptaoussanis/nippy) | [link](./config/taoensso/nippy) |

## Tested GraalVM versions

   - GraalVM 21.1.0
   - GraalVM 21.2.0
   - GraalVM DEV build

## Contributing

1. Clone the repository.
2. Copy the recipe `cp -R recipe config/organization/library`.
3. Provide `META-INF/organization/library/native-image.properties` with necessary native configurations.

### Tests

Run `bb native-image-test :dir config/com.taoensso/nippy/example :graalvm-version 21.1.0`.
