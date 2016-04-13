
INSERT INTO experiment (id, experiment_code) VALUES (1, 'testCode')
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


insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc1', 1, 01)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc2', 1, 02)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc3', 1, 03)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc4', 1, 04)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc5', 1, 05)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc6', 1, 06)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc7', 1, 07)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc8', 1, 08)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc9', 1, 09)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc10', 1, 10)

insert into tag_data (id,tag_content) values (01,'content 1')
insert into tag_data (id,tag_content) values (02,'content 2')
insert into tag_data (id,tag_content) values (03,'content 3')
insert into tag_data (id,tag_content) values (04,'content 4')
insert into tag_data (id,tag_content) values (05,'content 5')


insert into plant_tag (plant_id, tag_id) values (10, 01)
insert into plant_tag (plant_id, tag_id) values (10, 03)
insert into plant_tag (plant_id, tag_id) values (09, 02)

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

insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (1,'2016-02-02', 101, 01)
insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (2,'2016-04-05', 202, 01)
insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (3,'2016-04-05', 303, 01)
insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (4,'2016-04-05', 404, 01)
insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (5,'2016-04-05', 505, 01)
insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (6,'2016-04-05', 606, 01)

insert into plant_day (id, date, plant_day_meta_data_id, plant_id) values (7,'2016-04-05', 707, 02)

insert into plantday_tag (plantday_id, tag_id) values (1, 03)
insert into plantday_tag (plantday_id, tag_id) values (1, 04)
insert into plantday_tag (plantday_id, tag_id) values (5, 04)

