TARGET_JAR:=NULL
GRAALVM_HOME:=NULL

install-graalvm:
ifeq ($(wildcard graal-21.1.0),)
	@echo "Installing GraalVM 21.1.0"
	@mkdir -p graal-21.1.0 && curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.1.0/graalvm-ce-java8-linux-amd64-21.1.0.tar.gz | tar -zx -C graal-21.1.0/ --strip-components=1
	@./graal-21.1.0/bin/gu install native-image
	@echo "GraalVM 21.1.0 installed"
endif

ifeq ($(wildcard graal-21.2.0),)
	@echo "Installing GraalVM 21.2.0"
	@mkdir -p graal-21.2.0 && curl -L https://github.com/graalvm/graalvm-ce-builds/releases/download/vm-21.2.0/graalvm-ce-java8-linux-amd64-21.2.0.tar.gz | tar -zx -C graal-21.2.0/ --strip-components=1
	@./graal-21.2.0/bin/gu install native-image
	@echo "GraalVM 21.2.0 installed"
endif

ifeq ($(wildcard graal-21.3.0-dev),)
	@echo "Installing GraalVM 21.3.0-dev-20210914_2058"
	@mkdir -p graal-21.3.0-dev && curl -L https://github.com/graalvm/graalvm-ce-dev-builds/releases/download/21.3.0-dev-20210914_2058/graalvm-ce-java8-linux-amd64-dev.tar.gz | tar -zx -C graal-21.3.0-dev/ --strip-components=1
	@./graal-21.3.0-dev/bin/gu install native-image
	@echo "GraalVM 21.3.0-dev-20210914_2058 installed"
endif

clean-classes:
	@rm -Rf target

compile-21.1.0:
	@clojure -X:uberjar '{:uber-file "output-21.1.0.jar"}'

compile-21.2.0:
	@clojure -X:uberjar '{:uber-file "output-21.2.0.jar"}'

compile-21.3.0-dev:
	@clojure -X:uberjar '{:uber-file "output-21.3.0-dev.jar"}'

native-compile:
	$(GRAALVM_HOME)/bin/native-image -jar output-$(OUTPUT_VERSION).jar \
    --verbose \
	-H:Name=output-$(OUTPUT_VERSION) \
	-H:+ReportExceptionStackTraces \
	--initialize-at-build-time=clojure,example

test-21.1.0:
	@make GRAALVM_HOME=./graal-21.1.0 OUTPUT_VERSION=21.1.0 native-compile
	@[ -f "output-21.1.0" ] && printf "Testing the native-image output-21.1.0\n\n" && ./output-21.1.0

test-21.2.0:
	@GRAALVM_HOME=./graal-21.2.0 clojure -M:native-image "-H:Name=output-21.2.0" --verbose
	@[ -f "output-21.2.0" ] && printf "Testing the native-image output-21.2.0\n\n" && ./output-21.2.0

test-21.3.0-dev:
	@GRAALVM_HOME=./graal-21.3.0-dev clojure -M:native-image "-H:Name=output-21.3.0-dev" --verbose
	@[ -f "output-21.3.0-dev" ] && printf "Testing the native-image output-21.3.0-dev\n\n" && ./output-21.3.0-dev
