INSERT INTO team (id_team,budget,team_name) values (1,25000000.0,'Mercedes');
INSERT INTO team (id_team,budget,team_name) values (2,100000000.0,'Red Bull');

INSERT INTO driver (id_driver,first_name,last_name,salary,championships,id_team) values (1,'Alexander','Zhivkov',1000000.0,8,1);
INSERT INTO driver (id_driver,first_name,last_name,salary,championships,id_team) values (2,'Vladi','Andreev',5000000.0,5,1);
INSERT INTO driver (id_driver,first_name,last_name,salary,championships,id_team) values (3,'Hakan','Nihat',8000000.0,4,2);
INSERT INTO driver (id_driver,first_name,last_name,salary,championships,id_team) values (4,'Preslav','Zlatinov',12000000.0,2,2);


INSERT INTO race (id_race,circuit_name,distance_per_lap,laps,latitude,longitude,race_date,id_driver) values (1,'Spa',5.0,10,10.0,12.0,'2022-12-01',1);
INSERT INTO race (id_race,circuit_name,distance_per_lap,laps,latitude,longitude,race_date,id_driver) values (2,'Silverstone',4.0,20,10.0,19.0,'2022-07-12',2);
INSERT INTO race (id_race,circuit_name,distance_per_lap,laps,latitude,longitude,race_date,id_driver) values (3,'Monaco',2.5,15,92.0,14.0,'2022-09-16',2);