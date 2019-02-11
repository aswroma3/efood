FROM openjdk:8u191-jre-alpine3.8
RUN apk --no-cache add curl
COPY build/libs/restaurant-service.jar .
CMD java ${JAVA_OPTS} -jar restaurant-service.jar
HEALTHCHECK --start-period=30s --interval=5s CMD curl -f http://localhost:8080/actuator/health || exit 1
