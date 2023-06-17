CREATE TABLE todo_user (
    id       SERIAL PRIMARY KEY,
    name     varchar        not null,
    login    varchar unique not null,
    password varchar not null
);

CREATE TABLE tasks (
   id SERIAL PRIMARY KEY,
   user_id int references todo_user(id),
   title TEXT,
   description TEXT,
   created TIMESTAMP,
   done BOOLEAN
);