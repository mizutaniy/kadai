CREATE TABLE user(
 id INT(11) PRIMARY KEY auto_increment
 ,login_id VARCHAR(255) UNIQUE NOT NULL
 ,password VARCHAR(255) NOT NULL
 ,name VARCHAR(255) NOT NULL
 ,branch_number INT(11) NOT NULL
 ,post_number INT(11) NOT NULL
 ,user VARCHAR(255) NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 )

ALTER TABLE user modify user int(11)
alter table user alter column user set default 0
