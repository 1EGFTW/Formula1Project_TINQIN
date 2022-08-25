CREATE table team (
    id_team bigint NOT NULL AUTO_INCREMENT,
    budget DOUBLE NULL,
    team_name VARCHAR(255) NULL,
    PRIMARY KEY (id_team)
);
CREATE TABLE driver (
    id_driver bigint NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(255) NULL,
    last_name VARCHAR(255) NULL,
    salary DOUBLE NULL,
    championships INT NULL,
    id_team bigint NULL ,
    PRIMARY KEY (id_driver),
    FOREIGN KEY (id_team) references team
);
CREATE TABLE race (
    id_race bigint NOT NULL AUTO_INCREMENT,
    circuit_name VARCHAR(255) NULL,
    distance_per_lap DOUBLE NULL,
    laps INT NULL,
    latitude DOUBLE NULL,
    longitude DOUBLE NULL,
    race_date DATE NULL,
    id_driver bigint NULL,
    PRIMARY KEY (id_race),
    FOREIGN KEY (id_driver) references driver
);