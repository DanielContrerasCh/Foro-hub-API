create table topicos (
    id bigint not null auto_increment primary key,
    titulo varchar(255) not null,
    mensaje varchar(255) not null,
    fecha datetime not null,
    status tinyint not null default 1
);