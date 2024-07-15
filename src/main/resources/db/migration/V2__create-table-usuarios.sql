create table usuarios (
    id bigint not null auto_increment primary key,
    nombre varchar(255) not null,
    email varchar(255) not null,
    password varchar(255) not null
);