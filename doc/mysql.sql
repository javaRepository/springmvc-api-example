CREATE DATABASE example DEFAULT CHARACTER SET utf8;

CREATE TABLE user (
  id int(11) NOT NULL AUTO_INCREMENT,
  username varchar(50) NOT NULL comment 'name',
  userpwd varchar(50) NOT NULL comment 'password',
  addtime timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
   PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;
