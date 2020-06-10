FROM openjdk:8-jre-alpine

RUN mkdir /src

COPY . /src

RUN apk update && \
    apk add --virtual .build-deps --update --no-cache openssl ca-certificates openjdk8 && \
    update-ca-certificates && \
    cd /src && \
    sh gradlew --no-daemon --info shadowJar && \
    mv $(find build/libs -name '*.jar') /silverchain.jar && \
    rm -rf /src /tmp /root/.gradle && \
    apk del .build-deps

WORKDIR /workdir

ENTRYPOINT ["java", "-jar", "/silverchain.jar"]
