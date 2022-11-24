FROM --platform=linux/amd64 amazoncorretto:19-alpine-jdk

# Bash terminal -- not necessary
# RUN apk add --no-cache bash

#WORKDIR /mula
#COPY gradlew .
#COPY gradle gradle
#COPY build.gradle.kts .
#COPY settings.gradle.kts .
#COPY src src
COPY ./build/libs/mula-0.0.1-SNAPSHOT.jar .
#COPY Makefile .

# TODO: This takes too long
# Ideally we use a multistage build to
# build and run the code instead of copying it
# https://www.youtube.com/watch?v=p1dwLKAxUxA&list=PLy_6D98if3ULEtXtNSY_2qN21VCKgoQAE&index=23
# RUN ./gradlew bootJar

EXPOSE 8080
#CMD ["java", "-jar", "-Dspring.profiles.active=dev", "build/libs/mula-0.0.1-SNAPSHOT.jar"]
CMD ["java", "-jar", "-Dspring.profiles.active=dev", "mula-0.0.1-SNAPSHOT.jar"]