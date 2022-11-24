FROM --platform=linux/amd64 amazoncorretto:19-alpine-jdk

COPY ./build/libs/mula-0.0.1-SNAPSHOT.jar ./build/libs/mula-0.0.1-SNAPSHOT.jar
COPY .env .
COPY ./startBootJar.sh .

EXPOSE 8080
CMD ./startBootJar.sh