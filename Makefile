
#MULA_MYSQL_IP := $(shell docker inspect -f '{{range.NetworkSettings.Networks}}{{.IPAddress}}{{end}}' mula-mysql)

bootJar:
	./gradlew bootJar

run-jar:
	java -jar -Dspring.profiles.active=dev build/libs/mula-0.0.1-SNAPSHOT.jar

backend-image:
	docker build -t mula-backend:latest .

#backend-container:
#	docker run --name mula-backend -p 8080:8080 -e DB_HOST="$(MULA_MYSQL_IP)" --expose 8080 mula-backend:latest

backend-container:
	docker run --name mula-backend --network mula-network -p 8080:8080 -e DB_HOST="mula-mysql" --expose 8080 mula-backend:latest

mula-network:
	docker network create mula-network

connect-mula-network:
	docker network connect mula-network mula-mysql

.PHONY: bootJar run backend-image backend-container mula-network connect-mula-network
