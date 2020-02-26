FROM maven:3.6.3-jdk-8-openj9 as build

WORKDIR /build

COPY ./ /build

RUN mvn package

FROM openjdk:8

WORKDIR /app

COPY --from=build /build/target/maven-exm-1.0-SNAPSHOT.jar /app

ENTRYPOINT ["java","-jar","maven-exm-1.0-SNAPSHOT.jar"]
