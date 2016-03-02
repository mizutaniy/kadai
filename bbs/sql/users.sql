CREATE TABLE users(
 id INT(11) PRIMARY KEY auto_increment
 ,login_id VARCHAR(255) UNIQUE NOT NULL
 ,password VARCHAR(255) NOT NULL
 ,user_name VARCHAR(255) NOT NULL
 ,branch_id INT(11) NOT NULL
 ,department_id INT(11) NOT NULL
 ,user_state INT(11) DEFAULT 0
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 )
