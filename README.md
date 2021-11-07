# Silverchain: Fluent API generator

[![Maven Central](https://img.shields.io/maven-central/v/com.github.bannmann.maven.silverchain/silverchain-maven-plugin?label=maven%20central&color=informational)](https://maven-badges.herokuapp.com/maven-central/io.github.tomokinakamaru.silverchain/silverchain)
[![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg)](https://hub.docker.com/r/tomokinakamaru/silverchain)

[![Join the chat at https://gitter.im/tomokinakamaru/silverchain](https://badges.gitter.im/tomokinakamaru/silverchain.svg)](https://gitter.im/tomokinakamaru/silverchain?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)

[![Java CI with Gradle](https://github.com/tomokinakamaru/silverchain/actions/workflows/java-ci-with-gradle.yml/badge.svg)](https://github.com/tomokinakamaru/silverchain/actions/workflows/java-ci-with-gradle.yml)
[![CodeQL](https://github.com/tomokinakamaru/silverchain/actions/workflows/codeql-analysis.yml/badge.svg)](https://github.com/tomokinakamaru/silverchain/actions/workflows/codeql-analysis.yml)
[![Test Coverage](https://api.codeclimate.com/v1/badges/66d803605f5b2de0c000/test_coverage)](https://codeclimate.com/github/tomokinakamaru/silverchain/test_coverage)
[![Maintainability](https://api.codeclimate.com/v1/badges/66d803605f5b2de0c000/maintainability)](https://codeclimate.com/github/tomokinakamaru/silverchain/maintainability)

## What is Silverchain for?

Consider creating a library for writing SQL statements in the following style:

```java
// SELECT name FROM users WHERE id = 1
Result r = new SQL().select("name").from("users").where("id = 1").execute();
```

The simplest way to create such a library is to define a class and put all the methods in that class:

```java
class SQL {
  SQL() { ... }
  SQL select(String columns) { ... ; return this; }
  SQL from(String table) { ... ; return this; }
  SQL where(String expression) { ... ; return this; }
  Result execute() { ... }
}

Result r = new SQL()
  .select("name")   // Returns `SQL`
  .from("users")    // Returns `SQL`
  .where("id = 1")  // Returns `SQL`
  .execute();       // Returns `Result`
```

This simple implementation certainly lets the users write SQL statements as expected. However, it also allows its users to write invalid SQL statements, for example:

```java
new SQL().select("name").where("id = 1").execute(); // Missing `from(...)`
```

Can we prevent the users from writing such an invalid statement? Yes! If the return type of each method is chosen appropriately based on what the users can invoke next, an invalid chaining of method invocations causes a compile error. In our case, an invalid SQL statement comes to cause an error by defining classes/methods as follows:

```java
class SQL {
  SQL() { ... }
  SQL1 select(String columns) { ... }
}
class SQL1 {
  SQL2 from(String table) { ... }
}
class SQL2 {
  SQL3 where(String expression) { ... }
  Result execute() { ... }
}
class SQL3 {
  Result execute() { ... }
}

// Invalid statement causes compile error
new SQL()
  .select("name")  // Returns `SQL1`
  .where("id = 1") // `SQL1` does not have `where(...)` → Type error!

// Valid statement causes no error
Result r = new SQL()
  .select("name")   // Returns `SQL1`
  .from("users")    // Returns `SQL2`
  .where("id = 1")  // Returns `SQL3`
  .execute();       // Returns `Result`
```

Now, the library users must be happy because they will never accidentally write invalid SQL statements. *This implementation is user-friendly, but how about from the developers' viewpoint?* It must be tedious to define many classes and carefully put methods in each class. Imagine that you create a library that also supports insert/update/delete statements. The development of such a library is too tedious and you would give up the user-friendly implementation.

**Silverchain is a tool that significantly reduces the cost of the user-friendly implementation!** Silverchain generates class/method definitions from the code that defines valid chains. For example, it generates the four classes (`SQL`, `SQL1`, `SQL2`, and `SQL3`) from the following chain definition:

```
SQL {
  Result select(String columns) from(String table) where(String expression)? execute();
}
```

To learn how to write the input, see this [reference](./doc/ag-reference.md).

## Not only preventing invalid chains!

A Silverchain-generated library (i.e. library implemented in the user-friendly way) cooperates well with method completion system and lets the library users write code faster.

![completion](https://github.com/tomokinakamaru/silverchain/raw/main/doc/completion.gif)

When a library is implemented in the user-friendly way, the completion system shows only methods that library users can chain next (see the left of the GIF animation). On the other hand, the completion system shows all the methods including the ones that cannot be chained when a library is implemented in the simplest way (see the right of the animation).

## Tutorial

See [here](https://github.com/tomokinakamaru/silverchain/blob/main/doc/tutorial.md). We show how to create a library for melody composition that can be used as demonstrated in [this YouTube video](https://youtu.be/3fOn8cbhFZU).

## Run with Docker

```sh
docker run -v $(pwd):/workdir --rm -it tomokinakamaru/silverchain:latest
```


## Run with Maven

[![Maven Central](https://img.shields.io/maven-metadata/v.svg?color=informational&label=silverchain-maven-plugin%20%E2%99%A6%20latest&metadataUrl=https%3A%2F%2Frepo1.maven.org%2Fmaven2%2Fcom%2Fgithub%2Fbannmann%2Fmaven%2Fsilverchain%2Fsilverchain-maven-plugin%2Fmaven-metadata.xml)](https://maven-badges.herokuapp.com/maven-central/com.github.bannmann.maven.silverchain/silverchain-maven-plugin)

Add the plugin to your `pom.xml` as follows, setting `<version>` and `<inputFile>` as desired.

Output directory and Javadoc source directory are set to the default Maven locations. See the [Mojo source code](https://github.com/bannmann/silverchain-maven-plugin/blob/main/src/main/java/com/github/bannmann/maven/silverchain/GenerateMojo.java) for how to override them.

```xml
<plugin>
    <groupId>com.github.bannmann.maven.silverchain</groupId>
    <artifactId>silverchain-maven-plugin</artifactId>
    <version>PUT_VERSION_HERE</version>
    <configuration>
        <inputFile>src/main/silverchain/my-example-api.ag</inputFile>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>generate</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

## Build jar

```sh
./gradlew shadowJar # Creates ./build/libs/silverchain-<version>-all.jar
                    # Run it with `java -jar ...`
```

## Command line options

```
  -h, --help                Show this message and exit
  -v, --version             Show version and exit
  -i, --input <path>        Input grammar file
  -o, --output <path>       Output directory
  -j, --javadoc <path>      Javadoc source directory
  -m, --max-file-count <n>  Max number of generated files
```

[This page](./doc/javadoc.md) describes the use of `--javadoc`.

## Contributing

Bug reports, feature requests, and pull requests are welcome on GitHub at https://github.com/tomokinakamaru/silverchain.
