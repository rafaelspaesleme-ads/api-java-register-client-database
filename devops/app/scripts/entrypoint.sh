#!/bin/bash

#java -Dserver.port=8081 -Dspring.datasource.url=jdbc:postgresql://localhost:5432/data_center -Dspring.datasource.username=$DB_USER -Dspring.datasource.password=$DB_PASSWORD -jar webclient-0.0.1-SNAPSHOT.jar

#sed -e "s|DB_HOST|$DB_HOST|" -i src/main/resources/application.properties
#sed -e "s|DB_NAME|$DB_NAME|" -i src/main/resources/application.properties
#sed -e "s|DB_USER|$DB_USER|" -i src/main/resources/application.properties
#sed -e "s|DB_PASSWORD|$DB_PASSWORD|" -i src/main/resources/application.properties
#sed -e "s|DB_PORT|$DB_PORT|" -i src/main/resources/application.properties

echo "Database configuration set up"
mvn clean
mvn package -Dmaven.test.skip=true