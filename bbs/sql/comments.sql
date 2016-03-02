CREATE TABLE comments(
id INT(11) PRIMARY KEY auto_increment
 ,message_id INT(11) NOT NULL
 ,comment_text TEXT NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 ,user_name VARCHAR(255)
 )
 