# Build JAR
FROM eclipse-temurin:11-jdk-alpine as builder

COPY . /src

RUN cd /src && \
    sh gradlew --no-daemon --info shadowJar && \
    mv $(find build/libs -name '*.jar') /silverchain.jar


# Main
FROM eclipse-temurin:11-jre-alpine

COPY --from=builder /silverchain.jar /silverchain.jar

WORKDIR /workdir

ENTRYPOINT ["java", "-jar", "/silverchain.jar"]
