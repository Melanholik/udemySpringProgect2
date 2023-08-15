/*SQL PERSON*/
DROP TABLE IF EXISTS person CASCADE;

CREATE TABLE IF NOT EXISTS person
(
    id            INT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name          VARCHAR UNIQUE NOT NULL,
    birthday_year INT            NOT NULL
);

SELECT *
FROM person;

INSERT INTO person (name, birthday_year)
VALUES ('Иванов Иван Иванович', '1970'),
       ('Петров Петр Петрович', '1960'),
       ('Алексеев Алексей Алексеевич', '1989'),
       ('Познер Владимир Владимирович', '1944'),
       ('Федоров Мирон Янович', '1985');

/*SQL BOOK*/
DROP TABLE IF EXISTS book CASCADE;

CREATE TABLE IF NOT EXISTS book
(
    id           int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name         VARCHAR      NOT NULL,
    author       VARCHAR(100) NOT NULL,
    release_year INT          NOT NULL,
    person_id    INT          REFERENCES person (id) ON DELETE SET NULL
);

SELECT *
FROM book;

INSERT INTO book (name, author, release_year, person_id)
VALUES ('1984', 'Джордж Оруэлл', '1949', 1),
       ('Гарри Поттер и философский камень', 'Джоан Роулинг', '1997', 2),
       ('Великий Гэтсби', 'Фицджеральд, Фрэнсис Скотт', '1925', 1),
       ('Убить пересмешника', 'Харпер Ли', '1960', null),
       ('Властелин колец: Братство кольца', 'Дж. Р. Толкин', '1954', 1);
