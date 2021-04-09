## Spring boot CRUD

## Run project
mvn spring-boot:run -Dmaven.test.skip=true

## Build
mvn clean install -Dmaven.test.skip=true

## Run MySQL local
```
cd dinotrove
mkdir mysqldata
docker run -v "$PWD/mysqldata":/var/lib/mysql --name dinotrove-mysql -e MYSQL_ROOT_PASSWORD=dinotrove -d mysql:8.0.23
```