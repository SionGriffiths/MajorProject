
INSERT INTO experiment (id, experiment_code, status) VALUES (1, 'testCode', 'INITIALISED')
INSERT INTO experiment (id, experiment_code) VALUES (2, 'anotherCode')
INSERT INTO experiment (id, experiment_code) VALUES (3, 'experimentCode')
INSERT INTO experiment (id, experiment_code) VALUES (4, 'codeExperiment')



INSERT INTO metadata (id) VALUES (1)
INSERT INTO metadata (id) VALUES (2)
INSERT INTO metadata (id) VALUES (3)
INSERT INTO metadata (id) VALUES (4)
INSERT INTO metadata (id) VALUES (5)
INSERT INTO metadata (id) VALUES (6)
INSERT INTO metadata (id) VALUES (7)
INSERT INTO metadata (id) VALUES (8)
INSERT INTO metadata (id) VALUES (9)
INSERT INTO metadata (id) VALUES (10)


INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc1', 1, 01)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc2', 1, 02)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc3', 1, 03)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc4', 1, 04)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc5', 1, 05)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc6', 1, 06)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc7', 1, 07)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc8', 1, 08)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc9', 1, 09)
INSERT INTO plant (bar_code, experiment_id, plant_meta_data_id) VALUES ('bc10', 1, 10)

INSERT INTO tag_data (id,tag_content) VALUES (01,'content 1')
INSERT INTO tag_data (id,tag_content) VALUES (02,'content 2')
INSERT INTO tag_data (id,tag_content) VALUES (03,'content 3')
INSERT INTO tag_data (id,tag_content) VALUES (04,'content 4')
INSERT INTO tag_data (id,tag_content) VALUES (05,'content 5')


INSERT INTO plant_tag (plant_id, tag_id) VALUES (10, 01)
INSERT INTO plant_tag (plant_id, tag_id) VALUES (10, 03)
INSERT INTO plant_tag (plant_id, tag_id) VALUES (09, 02)

INSERT INTO metadata (id) VALUES (101)
INSERT INTO metadata (id) VALUES (202)
INSERT INTO metadata (id) VALUES (303)
INSERT INTO metadata (id) VALUES (404)
INSERT INTO metadata (id) VALUES (505)
INSERT INTO metadata (id) VALUES (606)
INSERT INTO metadata (id) VALUES (707)
INSERT INTO metadata (id) VALUES (808)
INSERT INTO metadata (id) VALUES (909)
INSERT INTO metadata (id) VALUES (110)

INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (1,'2016-02-02', 101, 01)
INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (2,'2016-04-05', 202, 01)
INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (3,'2016-04-05', 303, 01)
INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (4,'2016-04-05', 404, 01)
INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (5,'2016-04-05', 505, 01)
INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (6,'2016-04-05', 606, 01)

INSERT INTO plant_day (id, date, plant_day_meta_data_id, plant_id) VALUES (7,'2016-04-05', 707, 02)

INSERT INTO plantday_tag (plantday_id, tag_id) VALUES (1, 03)
INSERT INTO plantday_tag (plantday_id, tag_id) VALUES (1, 04)
INSERT INTO plantday_tag (plantday_id, tag_id) VALUES (5, 04)

insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (606, 'attr1' , 'val1')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (505, 'attr1' , 'val2')


insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (8, 'test' , '1')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (9, 'test' , '2')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (10, 'test' , '3')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (8, 'test2' , 'a')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (9, 'test2' , 'b')
insert into metadata_data_attributes (metadata, data_attributes_key, data_attributes) values (10, 'test2' , 'c')