# Silverchain: Fluent API generator

[![CircleCI](https://circleci.com/gh/tomokinakamaru/silverchain.svg?style=shield)](https://circleci.com/gh/tomokinakamaru/silverchain)
[![Test Coverage](https://api.codeclimate.com/v1/badges/4d1d4a6e304a8c3bc707/test_coverage)](https://codeclimate.com/github/tomokinakamaru/silverchain/test_coverage)
[![Maintainability](https://api.codeclimate.com/v1/badges/4d1d4a6e304a8c3bc707/maintainability)](https://codeclimate.com/github/tomokinakamaru/silverchain/maintainability)
[![Docker Hub](https://img.shields.io/badge/docker-ready-blue.svg)](https://hub.docker.com/r/tomokinakamaru/silverchain)

## Run with Docker

```sh
docker run --rm -it tomokinakamaru/silverchain:latest
```

## Build jar

```sh
./gradlew shadowJar # Creates ./build/libs/silverchain-<version>-all.jar
```

## Build native image

```sh
./gradlew nativeImage # Creates ./build/graal/silverchain
```

## Usage

```
Usage: silverchain [options]

Options:
  -h, --help             Show this message and exit
  -v, --version          Show version and exit
  -i, --input <path>     Input grammar file
  -o, --output <path>    Output directory
  -l, --language <lang>  Output language
```
