create table MISSION (
    id int primary key not null generated  always as identity,
    name varchar(50),
    difficulty int,
    details varchar(50),
    location varchar(50)
);