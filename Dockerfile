FROM amazoncorretto:25-alpine3.22-jdk AS build-image-base

WORKDIR /usr/local/app
RUN ["apk", "add", "maven"]

FROM build-image-base AS build-image-packaging
WORKDIR /usr/local/app
COPY src src
COPY pom.xml .
RUN ["mvn", "package"]


FROM amazoncorretto:25-al2023-headless AS getmi
LABEL authors="Chainie"
ARG version=1.0.0
WORKDIR /usr/local/app
COPY --from=build-image-packaging /usr/local/app/target/getmi-$version.jar .
ENTRYPOINT ["java", "-jar", "getmi-$version.jar"]
CMD ["-h"]