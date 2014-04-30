create table MISSION (
    id bigint not null generated  always as identity,
    name varchar(50),
    difficulty int,
    details varchar(50),
    location varchar(50),
    primary key (id)
);