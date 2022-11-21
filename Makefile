
bootJar:
	./gradlew bootJar
run:
	java -jar -Dspring.profiles.active=dev build/libs/mula-0.0.1-SNAPSHOT.jar
image:
	docker build -t mula-backend:latest .
container:
	docker run --name mula-backend -p 8080:8080 mula-backend:latest

.PHONY: bootJar run image container