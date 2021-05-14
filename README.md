## DinoTrove
UIUC CS411 team members:
	- Sheena Albert
	- Rakesh Patnaik
	- David John

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
		- Document DB to store Dino articles (text and image)

## Project Stage 5
	- Advanced Techniques
		- Constraints
			- Keys: Unique identifier (Primary keys on Dinosaur, Video tables)
			- Foreign Key Referential integrity: (Foreign keys on join tables dinosaur_videos)
		- Indexes
			- Index on MySQL database table "dinosaurs" for column name : Search dinosaurs uses this index
			- Index on MongoDB collection DinoArticle.dinosaurId : when a specific dinosaur is selected it fetches articles associated with the dinosaur in mongodb using dinosaurId index.
		- View
			- A view summarizing Dinosaur Videos - For all dinosaurs, what is the length of videos in total, min video length, max video length
		- Compound Statement
			- Dinosaur top 100 reports. Query that shows videos that feature in top 100 in terms of dinosaur count that they have OR top 100 in terms of video length
		- Transaction
			- Dinosaur create
			- Dinosaur update

		- Stored Procedures
			- operational tasks such as cleaning indexes using store proc DeleteIndexsIfExists
			
## database counts
	- MongoDB
```
> db.DinoArticle.count();
53137
```
	- MySQL
```
mysql> select count(*) from dinosaurs;
+----------+
| count(*) |
+----------+
|    52820 |
+----------+
1 row in set (0.07 sec)

mysql> select count(*) from videos;
+----------+
| count(*) |
+----------+
|   114822 |
+----------+
1 row in set (0.07 sec)

mysql> select count(*) from video_dinosaurs;
+----------+
| count(*) |
+----------+
|   114822 |
+----------+
1 row in set (0.06 sec)
```			
## mysql explain plan for index
```
mysql> explain select
    ->         t.*
    ->     from
    ->         dinosaurs t
    ->     where
    ->         t.name LIKE 'judiceratops%'
    ->         OR t.description LIKE 'judiceratops%' LIMIT 1000;
+----+-------------+-------+------------+------+------------------+------+---------+------+------+----------+-------------+
| id | select_type | table | partitions | type | possible_keys    | key  | key_len | ref  | rows | filtered | Extra       |
+----+-------------+-------+------------+------+------------------+------+---------+------+------+----------+-------------+
|  1 | SIMPLE      | t     | NULL       | ALL  | idx_dinosaurname | NULL | NULL    | NULL | 1146 |    20.99 | Using where |
+----+-------------+-------+------------+------+------------------+------+---------+------+------+----------+-------------+
1 row in set, 1 warning (0.00 sec)
```
```
mysql> show index from dinosaurs;
+-----------+------------+------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+---------+------------+
| Table     | Non_unique | Key_name         | Seq_in_index | Column_name | Collation | Cardinality | Sub_part | Packed | Null | Index_type | Comment | Index_comment | Visible | Expression |
+-----------+------------+------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+---------+------------+
| dinosaurs |          0 | PRIMARY          |            1 | dinosaur_id | A         |        1144 |     NULL |   NULL |      | BTREE      |         |               | YES     | NULL       |
| dinosaurs |          1 | idx_dinosaurname |            1 | name        | A         |        1144 |     NULL |   NULL |      | BTREE      |         |               | YES     | NULL       |
+-----------+------------+------------------+--------------+-------------+-----------+-------------+----------+--------+------+------------+---------+---------------+---------+------------+
2 rows in set (0.01 sec)
```

## mysql constraints
```
mysql> select COLUMN_NAME, CONSTRAINT_NAME, REFERENCED_COLUMN_NAME, REFERENCED_TABLE_NAME
    -> from information_schema.KEY_COLUMN_USAGE
    -> where TABLE_NAME IN ('dinosaurs', 'videos', 'video_dinosaurs');
+-------------------+-----------------------------+------------------------+-----------------------+
| COLUMN_NAME       | CONSTRAINT_NAME             | REFERENCED_COLUMN_NAME | REFERENCED_TABLE_NAME |
+-------------------+-----------------------------+------------------------+-----------------------+
| dinosaur_id       | PRIMARY                     | NULL                   | NULL                  |
| video_dinosaur_id | PRIMARY                     | NULL                   | NULL                  |
| dinosaur_id       | fk_video_dinosaur_dinosaurs | dinosaur_id            | dinosaurs             |
| video_id          | fk_video_dinosaur_videos    | video_id               | videos                |
| video_id          | PRIMARY                     | NULL                   | NULL                  |
+-------------------+-----------------------------+------------------------+-----------------------+
5 rows in set (0.03 sec)
```
## other commands
```
update dinosaur set name=LOWER(name);
CREATE INDEX idx_dinosaurname ON dinosaurs(name);
CREATE INDEX idx_dino_video_dinoid ON video_dinosaurs(dinosaur_id);

show index from dinosaurs;
```
		
			
		
