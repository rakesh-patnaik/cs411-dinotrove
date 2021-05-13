CREATE DATABASE IF NOT EXISTS dinosaur_trove DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;

USE dinosaur_trove;

DROP VIEW IF EXISTS dinosaur_video_summary;
DROP TABLE IF EXISTS video_dinosaurs;
DROP TABLE IF EXISTS video_ratings;
DROP TABLE IF EXISTS rating;
DROP TABLE IF EXISTS rating_type;
DROP TABLE IF EXISTS toy_dinosaurs;
DROP TABLE IF EXISTS dinosaurs;
DROP TABLE IF EXISTS videos;
DROP TABLE IF EXISTS toys;
DROP TABLE IF EXISTS user_clicks;
DROP TABLE IF EXISTS sellers;
DROP TABLE IF EXISTS user_activities;
DROP TABLE IF EXISTS users;


CREATE TABLE dinosaurs (
	dinosaur_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    dinosaur_type VARCHAR(255) NOT NULL,
    size_height FLOAT(14,2),
    size_length FLOAT(14,2),
    size_weight FLOAT(14,2),
    description VARCHAR(2048) DEFAULT NULL,
    all_facts_document_id  INT NOT NULL,
	CONSTRAINT pk_dinosaur PRIMARY KEY (dinosaur_id)
);

CREATE TABLE users (
	user_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
    age INT NOT NULL,
	CONSTRAINT pk_user PRIMARY KEY (user_id)
);

CREATE TABLE rating_type (
	rating_type_id INT NOT NULL AUTO_INCREMENT,
	scale_max INT(2) DEFAULT 5,
	scale_min INT(2) DEFAULT 0,
	name VARCHAR(32) NOT NULL,
	description VARCHAR(128),
	CONSTRAINT pk_rating_type PRIMARY KEY (rating_type_id)
);
ALTER TABLE rating_type ADD CONSTRAINT uk_rate_type_name UNIQUE(name);

CREATE TABLE rating (
	rating_id INT NOT NULL AUTO_INCREMENT,
	rating_type_id INT NOT NULL,
	rate_value INT(2) DEFAULT NULL,
	active BOOLEAN DEFAULT 1,
	CONSTRAINT pk_rating PRIMARY KEY (rating_id)
);
ALTER TABLE rating ADD CONSTRAINT fk_rate_rate_type FOREIGN KEY (rating_type_id) REFERENCES rating_type(rating_type_id);

CREATE TABLE videos (
	video_id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(255) NOT NULL,
	video_url VARCHAR(2048) NOT NULL,
    video_title VARCHAR(2048) NOT NULL,
    video_length FLOAT(14,2),
	CONSTRAINT pk_video PRIMARY KEY (video_id)
);

CREATE TABLE video_ratings (
	video_rating_id INT NOT NULL AUTO_INCREMENT,
	rating_id INT NOT NULL,
    video_id INT NOT NULL,
	CONSTRAINT pk_video_ratings PRIMARY KEY (video_rating_id)
);
ALTER TABLE video_ratings ADD CONSTRAINT fk_vid_rat_rating FOREIGN KEY (rating_id) REFERENCES rating(rating_id);
ALTER TABLE video_ratings ADD CONSTRAINT fk_vid_rat_video FOREIGN KEY (video_id) REFERENCES videos(video_id);

CREATE TABLE video_dinosaurs (
	video_dinosaur_id INT NOT NULL AUTO_INCREMENT,
	video_id INT NOT NULL,
	dinosaur_id INT NOT NULL,
	CONSTRAINT pk_video_dinosaur PRIMARY KEY (video_dinosaur_id)
);
ALTER TABLE video_dinosaurs ADD CONSTRAINT fk_video_dinosaur_videos FOREIGN KEY (video_id) REFERENCES videos(video_id);
ALTER TABLE video_dinosaurs ADD CONSTRAINT fk_video_dinosaur_dinosaurs FOREIGN KEY (dinosaur_id) REFERENCES dinosaurs(dinosaur_id);

CREATE TABLE sellers (
	seller_id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    seller_base_url VARCHAR(2048) NOT NULL,
	CONSTRAINT pk_sellers PRIMARY KEY (seller_id)
);

CREATE TABLE toys (
	toy_id INT NOT NULL AUTO_INCREMENT,
    seller_id INT NOT NULL,
    toy_url VARCHAR(2048) NOT NULL,
    toy_description VARCHAR(2048) NOT NULL,
	name VARCHAR(255) NOT NULL,
	CONSTRAINT pk_toy PRIMARY KEY (toy_id)
);
ALTER TABLE toys ADD CONSTRAINT fk_toys_sellers FOREIGN KEY (seller_id) REFERENCES sellers(seller_id);


CREATE TABLE toy_dinosaurs (
	toy_dinosaur_id INT NOT NULL AUTO_INCREMENT,
	toy_id INT NOT NULL,
	dinosaur_id INT NOT NULL,
	CONSTRAINT pk_toy_dinosaur PRIMARY KEY (toy_dinosaur_id)
);
ALTER TABLE toy_dinosaurs ADD CONSTRAINT fk_toy_dinosaur_videos FOREIGN KEY (toy_id) REFERENCES toys(toy_id);
ALTER TABLE toy_dinosaurs ADD CONSTRAINT fk_toy_dinosaur_dinosaurs FOREIGN KEY (dinosaur_id) REFERENCES dinosaurs(dinosaur_id);

CREATE TABLE user_activities (
	user_activity_id INT NOT NULL AUTO_INCREMENT,
    user_id INT,
    activity_url VARCHAR(2048) NOT NULL,
	CONSTRAINT user_activities PRIMARY KEY (user_activity_id)
);
ALTER TABLE user_activities ADD CONSTRAINT fk_usr_act_user FOREIGN KEY (user_id) REFERENCES users(user_id);

CREATE VIEW dinosaur_video_summary AS
SELECT  d.dinosaur_id AS dinosaur_id,
        d.name AS dinosaur_name, 
        d.dinosaur_type AS dinosaur_type, 
		SUM(v.video_length) AS total_video_length, 
		MIN(v.video_length) AS min_video_length, 
		MAX(v.video_length) AS max_video_length
FROM dinosaurs d, video_dinosaurs vd, videos v
WHERE d.dinosaur_id = vd.dinosaur_id
AND v.video_id = vd.video_id
GROUP BY d.dinosaur_id