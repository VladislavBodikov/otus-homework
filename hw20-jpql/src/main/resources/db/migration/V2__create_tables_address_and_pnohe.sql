create sequence address_seq start with 1 increment by 50;
create sequence phone_seq start with 1 increment by 50;

create table address
(
    id   bigserial not null primary key,
    street text,
    client_id bigint
);

create table phone
(
    id   bigserial not null primary key,
    number text,
    client_id bigint
);
