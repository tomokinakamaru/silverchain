# Silverchain: Fluent API generator

[![CircleCI](https://circleci.com/gh/tomokinakamaru/silverchain.svg?style=shield)](https://circleci.com/gh/tomokinakamaru/silverchain)
[![Test Coverage](https://api.codeclimate.com/v1/badges/4d1d4a6e304a8c3bc707/test_coverage)](https://codeclimate.com/github/tomokinakamaru/silverchain/test_coverage)
[![Maintainability](https://api.codeclimate.com/v1/badges/4d1d4a6e304a8c3bc707/maintainability)](https://codeclimate.com/github/tomokinakamaru/silverchain/maintainability)
[![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg)](https://hub.docker.com/r/tomokinakamaru/silverchain)

## What is Silverchain for?

Suppose you are creating a library for writing SQL statements in the method-chaining style:

```java
// SELECT name FROM users WHERE id = 1
Result r = new SQL().select("name").from("users").where("id = 1").execute();
```

The simplest implementation of that library is as follows:

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

However, this simple implementation allows its users to write invalid SQL statements, for example:

```java
new SQL().select("name").where("id = 1").execute(); // Missing `from(...)`
```

Such invalid statements can be prevented *by setting the return type of each method based on what the users can chain next*. In the example case, an invalid statement comes to cause a compile-time error by defining the classes/methods as follows:

```java
class SQL {
  SQL() { ... }
  SQL1 select() { ... }
}
class SQL1 {
  SQL2 from() { ... }
}
class SQL2 {
  SQL3 where() { ... }
  Result execute() { ... }
}
class SQL3 {
  Result execute() { ... }
}

// Invalid statement causes compile-time error
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

**The problem is that, this *safe* implementation increases the development cost of a library.** You need to define many classes and carefully put methods in each class. Since the example library is small, the development cost is not that large. However, imagine that you create a library that also supports insert/update/delete statements. The development of such a library is too tedious and you would give up the safe implementation.

**Silverchain is a tool that solves the problem!** It generates class/method definitions from the code that defines valid chains. For example, Silverchain generates the four classes (`SQL`, `SQL1`, `SQL2`, and `SQL3`) from the following chain definition:

```
SQL: select(String columns) from(String table) where(String expression)? execute() Results;
```

![doc/method-completion.gif](doc/method-completion.gif)

## Run with Docker

```sh
docker run -v $(pwd):/workdir --rm -it tomokinakamaru/silverchain:latest
```

## Build jar

```sh
./gradlew shadowJar # Creates ./build/libs/silverchain-<version>-all.jar
```

## Build native image

```sh
./gradlew nativeImage # Creates ./build/graal/silverchain
```

## Command line options

```
-h, --help             Show this message and exit
-v, --version          Show version and exit
-i, --input <path>     Input grammar file
-o, --output <path>    Output directory
-l, --language <lang>  Output language
```
