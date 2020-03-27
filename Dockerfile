FROM openjdk:8-jre-alpine

RUN apk update && \
    apk add --virtual .build-deps --update --no-cache openssl ca-certificates && \
    update-ca-certificates && \
    apk del .build-deps

RUN mkdir /src

COPY . /src

RUN apk add --virtual .build-deps --no-cache openjdk8 && \
    cd /src && \
    sh gradlew --no-daemon --info shadowJar && \
    mv $(find build/libs -name '*.jar') /silverchain.jar && \
    rm -rf /src /tmp /root/.gradle && \
    apk del .build-deps

WORKDIR /workdir

ENTRYPOINT ["java", "-jar", "/silverchain.jar"]
