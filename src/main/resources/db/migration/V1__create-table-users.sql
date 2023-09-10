create table users(

    id bigint                                       not null                auto_increment,
    full_name             varchar(100)              not null,
    cpf                   varchar(100)              not null                unique,
    email                 varchar(200)              not null                unique,
    password              varchar(200)              not null,
    user_type             varchar(90)               not null,
    active_status         tinyint                   not null,


    primary key(id)

);