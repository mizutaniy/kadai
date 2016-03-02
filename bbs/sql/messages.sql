CREATE TABLE messages(
 id INT(11) PRIMARY KEY auto_increment
 ,user_id INT(11) NOT NULL
 ,title VARCHAR(255) NOT NULL
 ,message_text TEXT NOT NULL
 ,category VARCHAR(255) NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 )
 