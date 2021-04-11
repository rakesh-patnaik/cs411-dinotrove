## Spring boot CRUD

## Run project
mvn spring-boot:run -Dmaven.test.skip=true

## Build
mvn clean install -Dmaven.test.skip=true

## Run MySQL local
```
cd dinotrove
mkdir mysqldata
#FOR M1 use mysql/mysql-server image 
docker run -v "$PWD/mysqldata":/var/lib/mysql --name dinotrove-mysql -e MYSQL_ROOT_PASSWORD=dinotrove -d -p 3306:3306 mysql:8.0.23 
docker exec -it dinotrove-mysql /bin/bash
mysql -u root -p
update mysql.user set host="%" where user="root";
FLUSH PRIVILEGES;
```

## access site
http://localhost:8080/dinosaur/listing