FROM gradle:5.6.2-jdk8 AS builder

ARG appPath

WORKDIR /app
COPY ${appPath} /app
RUN cd /app

RUN gradle clean build

FROM openjdk:8-jdk-alpine

WORKDIR /app
COPY --from=builder /app/build/libs/*.jar /app/service.jar

CMD java -Xmx256M -jar /app/service.jar