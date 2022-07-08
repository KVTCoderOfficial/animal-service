create table users
(
    id         bigserial,
    username   varchar(30) not null unique,
    password   varchar(80) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (id)
);

create table user_attempts
(
    id                      bigserial,
    user_id                 bigint references users (id),
    attempts                int,
    all_attempts_timestamps varchar(1000),
    created_at              timestamp default current_timestamp,
    updated_at              timestamp default current_timestamp,
    primary key (id)
);

create table roles
(
    id         bigserial,
    name       varchar(50) not null,
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp,
    primary key (id)
);

create table users_roles
(
    user_id bigint not null,
    role_id bigint not null,
    primary key (user_id, role_id),
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);

create table species
(
    id         bigserial primary key,
    title      varchar(255),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

create table animals
(
    id         bigserial primary key,
    nickname   varchar(255) not null unique,
    species_id bigint references species (id),
    gender     varchar(30)  not null,
    birthday   date         not null,
    user_id    bigint references users (id),
    created_at timestamp default current_timestamp,
    updated_at timestamp default current_timestamp
);

insert into roles (name)
values ('ROLE_USER');

insert into species (title)
values ('Насекомое'),
       ('Млекопитающее'),
       ('Пресмыкающееся'),
       ('Рыба'),
       ('Земноводное'),
       ('Птица'),
       ('Паукообразное'),
       ('Ракообразное');