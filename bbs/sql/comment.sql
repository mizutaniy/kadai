CREATE TABLE comment(
 main_text TEXT NOT NULL
 ,insert_date TIMESTAMP NOT NULL
 ,update_date TIMESTAMP NOT NULL
 ,user_name VARCHAR(255)
 )
 
 alter table comment change main_text message text not null
 