CREATE TABLE comments(
id INT(11) PRIMARY KEY auto_increment
 ,user_id INT(11) NOT NULL
 ,message_id INT(11) NOT NULL
 ,comment_text TEXT NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 )
 