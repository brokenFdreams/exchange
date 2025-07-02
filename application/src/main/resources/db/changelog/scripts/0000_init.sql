--liquibase formatted sql

--changeset account:create_account_table
create table if not exists account (
    id bigint primary key,
    owner_id bigint not null,
    currency varchar(3) not null,
    balance DECIMAL(10,2) not null,
    version int not null
);

create sequence account_id_sequence increment by 1 minvalue 1;
