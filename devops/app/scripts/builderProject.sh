echo "Building Project..."
mvn clean
mvn package -Dmaven.test.skip=true