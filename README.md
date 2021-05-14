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
## Create MySQL schema
- run following sql schema after opening a terminal on mysql using the mysql command.
https://github.com/rakesh-patnaik/cs411-dinotrove/blob/main/db_schema.sql

```
mysql -u root -p
#enter dinotrove as password
# cp/paste schema from https://github.com/rakesh-patnaik/cs411-dinotrove/blob/main/db_schema.sql
```

## Run MongoDB on local
```
docker run -d --name dinotrove-mongodb mongo
```
## Mongodb Index on dinosaurId
```
> show databases
admin        0.000GB
config       0.000GB
dinotrovedb  0.001GB
local        0.000GB
> db.DinoArticle.getIndexes()
[ { "v" : 2, "key" : { "_id" : 1 }, "name" : "_id_" } ]
> db.DinoArticle.createIndex({dinosaurId:1})
{
	"createdCollectionAutomatically" : false,
	"numIndexesBefore" : 1,
	"numIndexesAfter" : 2,
	"ok" : 1
}
> db.DinoArticle.getIndexes()
[
	{
		"v" : 2,
		"key" : {
			"_id" : 1
		},
		"name" : "_id_"
	},
	{
		"v" : 2,
		"key" : {
			"dinosaurId" : 1
		},
		"name" : "dinosaurId_1"
	}
]
```

## access site
http://localhost:8080

## import data using the following curl commands

curl http://localhost:8080/dataScraper/dinosaurs/import/kaggle
curl http://localhost:8080/dataScraper/dinosaurs/import/dinosaurpicturesorg
curl http://localhost:8080/dataScraper/dinosaurs/import/videos
curl http://localhost:8080/dataScraper/dinosaurs/import/videos/generateData
curl http://localhost:8080/dataScraper/dinosaurs/import/articles

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

## Project Stage 5
	- Advanced Techniques
		- Constraints
			- Keys: Unique identifier (Primary keys on Dinosaur, Video tables)
			- Foreign Key Referential integrity: (Foreign keys on join tables dinosaur_videos)
		- Indexes
			- Index on MySQL database table "dinosaurs" for column name : Search dinosaurs uses this index
			- Index on MongoDB collection DinoArticle.dinosaurId : when a specific dinosaur is selected it fetches articles associated with the dinosaur in mongodb using dinosaurId index.
			
		
			
		