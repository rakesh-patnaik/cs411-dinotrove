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

##  Project Stage 4
	- Search Dinosaurs (Dino Dashboard - search and viewing)
	- CRUD dinosaurs (Dino Lab)
	- Scraping Data from External sources for populating Dinosaurs
	- Schema and Source Code
	- Future items
		- Indexing(for search), Prepared Statements(dino reports, clickstream relation to Dinosaurs, Toys, Videos etc)
		- Video scraping and insert
		- Toys scraping and insert
		- Document DB to store Dino articles (text and image)
		- Analytics (click stream and Heat Map)