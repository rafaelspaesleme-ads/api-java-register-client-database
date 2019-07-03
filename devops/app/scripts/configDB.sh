#!/bin/bash

DBA_HOST=${HOSTNAME_DB}
DBA_NAME=${NAME_DB}
DBA_USER=${USERNAME_DB}
DBA_PASSWORD=${PASSWORD_DB}
DBA_PORT=${PORT_DB}

sed -e "s|DBA_HOST|$DBA_HOST|" -i src/main/resources/application.properties
sed -e "s|DBA_NAME|$DBA_NAME|" -i src/main/resources/application.properties
sed -e "s|DBA_USER|$DBA_USER|" -i src/main/resources/application.properties
sed -e "s|DBA_PASSWORD|$DB_PASSWORD|" -i src/main/resources/application.properties
sed -e "s|DBA_PORT|$DBA_PORT|" -i src/main/resources/application.properties
sed -e "s|DBA_PORT|$DBA_PORT|" -i src/main/resources/application.properties

echo "Database configuration set up"