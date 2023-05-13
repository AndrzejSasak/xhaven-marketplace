-- liquibase formatted sql

create sequence _user_seq
    increment by 50;

alter sequence _user_seq owner to xhaven;

create sequence auction_seq
    increment by 50;

alter sequence auction_seq owner to xhaven;

create sequence category_seq
    increment by 50;

alter sequence category_seq owner to xhaven;

create sequence image_seq
    increment by 50;

alter sequence image_seq owner to xhaven;

create sequence role_seq
    increment by 50;

alter sequence role_seq owner to xhaven;

create sequence token_seq
    increment by 50;

alter sequence token_seq owner to xhaven;

create table _user
(
    id_user      bigint not null
        primary key,
    email        varchar(255),
    name         varchar(255),
    password     varchar(255),
    phone_number varchar(255),
    surname      varchar(255)
);

alter table _user
    owner to xhaven;

create table category
(
    category_id   bigint not null
        primary key,
    category_name varchar(255),
    parent_id     bigint
        constraint fk2y94svpmqttx80mshyny85wqr
            references category
);

alter table category
    owner to xhaven;

create table auction
(
    id_auction           bigint not null
        primary key,
    contact_information  varchar(255),
    description          varchar(255),
    is_active            boolean,
    phone_number         varchar(255),
    posted_at            timestamp(6),
    price                numeric(38, 2),
    title                varchar(255),
    category_category_id bigint
        constraint fkqbww5fg4d17ebvm1lsvdq2ucl
            references category,
    id_user              bigint
        constraint fkmid2wyc7c1nnbyt98jx2drjlj
            references _user
);

alter table auction
    owner to xhaven;

create table _user_favorite_auctions
(
    user_id_user                 bigint not null
        constraint fk708xcb7uh8ox91uvvpav8hbfs
            references _user,
    favorite_auctions_id_auction bigint not null
        constraint fkm1ynrutv54dh4ydk5suvdrb7m
            references auction
);

alter table _user_favorite_auctions
    owner to xhaven;

create table image
(
    id_image   bigint not null
        primary key,
    image_name varchar(255),
    id_auction bigint
        constraint fk2kcjr4uke828qk21dn01cjkmg
            references auction
);

alter table image
    owner to xhaven;

create table role
(
    id   bigint not null
        primary key,
    name varchar(255)
);

alter table role
    owner to xhaven;

create table token
(
    id         integer not null
        primary key,
    expired    boolean not null,
    revoked    boolean not null,
    value      varchar(255)
        constraint uk_1ijv0rgb0nkfb1suvfxcewa0y
            unique,
    token_type varchar(255),
    id_user    bigint
        constraint fk187fptntktlmcq5ivagwtjj8b
            references _user
);

alter table token
    owner to xhaven;

create table user_role
(
    id_user bigint not null
        constraint fkdynwvl1dt9s7kallgcqwwih1f
            references _user,
    id_role bigint not null
        constraint fk2aam9nt2tv8vcfymi3jo9c314
            references role,
    primary key (id_user, id_role)
);

alter table user_role
    owner to xhaven;

