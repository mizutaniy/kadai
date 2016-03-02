CREATE VIEW user_comment AS
 SELECT
  text
  ,name
  ,comments.insert_date
 FROM
  users, comments
 WHERE
  users.id = comments.user_id