create table respuestas (
    id bigint not null auto_increment primary key,
    topico_id bigint not null,
    usuario_id bigint not null,
    mensaje varchar(255) not null,
    fecha datetime not null,

    foreign key (topico_id) references topicos(id),
    foreign key (usuario_id) references usuarios(id)
);