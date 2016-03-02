CREATE VIEW user_message AS
 SELECT
messages.id as message_id
  ,title
  ,text
  ,name
  ,messages.insert_date
 FROM
  users, messages
 WHERE
  users.id = messages.user_id
  