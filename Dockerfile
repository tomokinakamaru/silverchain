# Build JAR
FROM openjdk:8-jdk-alpine as builder

COPY . /src

RUN cd /src && \
    sh gradlew --no-daemon --info shadowJar && \
    mv $(find build/libs -name '*.jar') /silverchain.jar


# Main
FROM openjdk:8-jre-alpine

COPY --from=builder /silverchain.jar /silverchain.jar

WORKDIR /workdir

ENTRYPOINT ["java", "-jar", "/silverchain.jar"]
