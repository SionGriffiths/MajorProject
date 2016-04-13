
INSERT INTO experiment (id, experiment_code) VALUES (0001, 'testCode')



INSERT INTO metadata (id) VALUES (01)
INSERT INTO metadata (id) VALUES (02)
INSERT INTO metadata (id) VALUES (03)
INSERT INTO metadata (id) VALUES (04)
INSERT INTO metadata (id) VALUES (05)
INSERT INTO metadata (id) VALUES (06)
INSERT INTO metadata (id) VALUES (07)
INSERT INTO metadata (id) VALUES (08)
INSERT INTO metadata (id) VALUES (09)
INSERT INTO metadata (id) VALUES (10)


insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc1', 0001, 01)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc2', 0001, 02)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc3', 0001, 03)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc4', 0001, 04)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc5', 0001, 05)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc6', 0001, 06)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc7', 0001, 07)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc8', 0001, 08)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc9', 0001, 09)
insert into plant (bar_code, experiment_id, plant_meta_data_id) values ('bc10', 0001, 10)

insert into tag_data (id,tag_content) values (01,'content 1')
insert into tag_data (id,tag_content) values (02,'content 2')
insert into tag_data (id,tag_content) values (03,'content 3')
insert into tag_data (id,tag_content) values (04,'content 4')
insert into tag_data (id,tag_content) values (05,'content 5')


insert into plant_tag (plant_id, tag_id) values (10, 01)
insert into plant_tag (plant_id, tag_id) values (10, 03)
insert into plant_tag (plant_id, tag_id) values (09, 02)