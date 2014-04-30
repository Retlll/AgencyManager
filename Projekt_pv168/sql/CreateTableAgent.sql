create table AGENT (
    id bigint not null generated  always as identity,
    name varchar(50),
    born date,
    active boolean,
    rank int,
    notes varchar(50),
    primary key (id)
);