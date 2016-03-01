CREATE TABLE message(
 id INT(11) PRIMARY KEY auto_increment
 ,title VARCHAR(50) NOT NULL
 ,main_text TEXT NOT NULL
 ,category VARCHAR(10) NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 ,user_name VARCHAR(255)
 )
 
alter table message modify title varchar(255)
alter table message modify category varchar(255)
alter table message change main_text message text not null
ALTER TABLE message MODIFY COLUMN category varchar(255)NOT NULL
ALTER TABLE message MODIFY COLUMN title varchar(255)NOT NULL
