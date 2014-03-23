create table AGENT (
    id bigint primary key not null generated  always as identity,
    name varchar(50),
    born date,
    active boolean,
    rank int,
    notes varchar(50)
);