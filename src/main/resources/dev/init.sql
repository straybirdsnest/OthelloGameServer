DROP DATABASE IF EXISTS OTHELLOGAMEDB;
CREATE DATABASE OTHELLOGAMEDB;
USE OTHELLOGAMEDB;

CREATE TABLE OTHELLO_IMAGE (
  `image_id`           BIGINT PRIMARY KEY AUTO_INCREMENT,
  `data`               MEDIUMBLOB
);

CREATE TABLE OTHELLO_USER_ONLINE (
  `user_id`            INT PRIMARY KEY AUTO_INCREMENT,
  `online_state`       INT
);

CREATE TABLE OTHELLO_USER_GROUP (
  `user_group_id`      INT PRIMARY KEY AUTO_INCREMENT,
  `user_group_name`    VARCHAR(32)
);

CREATE TABLE OTHELLO_USER (
  `user_id`            INT PRIMARY KEY AUTO_INCREMENT,
  `username`           VARCHAR(32),
  `password`           VARCHAR(255),
  `email_address`      VARCHAR(100),
  `create_time`        TIMESTAMP,
  `is_active`          BOOLEAN,
  `user_group`         INT,
  `socketioid`         VARCHAR(255),
  FOREIGN KEY (`user_group`) REFERENCES OTHELLO_USER_GROUP (user_group_id)
);

CREATE TABLE OTHELLO_USER_INFORMATION (
  `user_id`            INT PRIMARY KEY AUTO_INCREMENT,
  `nickname`           VARCHAR(32),
  `gender`             ENUM('MALE', 'FEMALE', 'SECRET'),
  `birthday`           DATE,
  `game_wins`          INT,
  `game_draws`         INT,
  `game_losts`         INT,
  `rank_points`        INT,
  FOREIGN KEY (`user_id`) REFERENCES OTHELLO_USER (user_id)
);

CREATE TABLE OTHELLO_GAME_TABLE (
  `game_table_id`      INT PRIMARY KEY AUTO_INCREMENT,
  `playera`            INT,
  `playerb`            INT,
  FOREIGN KEY (`playera`) REFERENCES OTHELLO_USER (user_id),
  FOREIGN KEY (`playerb`) REFERENCES OTHELLO_USER (user_id)
);

CREATE TABLE OTHELLO_GAME_RECORD (
  `game_record_id`     INT PRIMARY KEY AUTO_INCREMENT,
  `black_number`       INT,
  `white_number`       INT,
  `game_begin_time`    DATETIME,
  `game_end_time`      DATETIME,
  `playera`            INT,
  `playerb`            INT,
   FOREIGN KEY (`playera`) REFERENCES OTHELLO_USER (user_id),
   FOREIGN KEY (`playerb`) REFERENCES OTHELLO_USER (user_id)
);

CREATE TABLE OTHELLO_MEMBERSHIP (
  `user_group_members` INT PRIMARY KEY,
  `othello_user_group` INT,
  FOREIGN KEY (`othello_user_group`) REFERENCES OTHELLO_USER_GROUP (user_group_id),
  FOREIGN KEY (`user_group_members`) REFERENCES OTHELLO_USER (user_id)
);

# 插入数据
#用户组信息
INSERT INTO `othellogamedb`.`othello_user_group`(`user_group_id`,`user_group_name`) VALUES('1','ROLE_USER');
INSERT INTO `othellogamedb`.`othello_user_group`(`user_group_id`,`user_group_name`) VALUES('2','ROLE_ADMIN');

#用户信息
INSERT INTO `othellogamedb`.`othello_user`
(`user_id`, `create_time`, `email_address`, `is_active`, `password`, `username`, `user_group`, `socketioid`)
VALUES
('1', '2015-01-01 00:00:00', 'test@test.com', '1', 'test', 'test', '1', 'a9d9fb00-9343-47fe-b8fc-8eb8e8991f29');
INSERT INTO `othellogamedb`.`othello_user`
(`user_id`, `create_time`, `email_address`, `is_active`, `password`, `username`, `user_group`, `socketioid`)
VALUES
('2', '2015-01-01 00:00:01', 'black@black.com', '1', 'black', 'black', '2', 'e654e692-f232-4a84-a610-d1621af981c4');

INSERT INTO `othellogamedb`.`othello_user_information` (`user_id`, `nickname`, `gender`, `birthday`, `game_wins`, `game_draws`, `game_losts`, `rank_points`)
VALUES ('1', 'test', 'MALE', '1990-11-11', '0', '0', '0', '0');
INSERT INTO `othellogamedb`.`othello_user_information` (`user_id`, `nickname`, `gender`, `birthday`, `game_wins`, `game_draws`, `game_losts`, `rank_points`)
VALUES ('2', 'black', 'FEMALE', '2000-11-11', '0', '0', '0', '0');

#用户所属的用户组
INSERT INTO `othellogamedb`.`othello_membership`(`othello_user_group`, `user_group_members`) VALUES ('1', '1');
INSERT INTO `othellogamedb`.`othello_membership`(`othello_user_group`, `user_group_members`) VALUES ('2', '2');

#用户在线情况
INSERT INTO `othellogamedb`.`othello_user_online`(`user_id`, `online_state`) VALUES ('1', '199');
INSERT INTO `othellogamedb`.`othello_user_online`(`user_id`, `online_state`) VALUES ('2', '199');