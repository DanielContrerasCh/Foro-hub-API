ALTER TABLE topicos ADD COLUMN curso_id bigint not null;

ALTER TABLE topicos ADD COLUMN usuario_id bigint not null;

ALTER TABLE topicos ADD CONSTRAINT fk_curso FOREIGN KEY (curso_id) REFERENCES cursos(id);

ALTER TABLE topicos ADD CONSTRAINT fk_usuario FOREIGN KEY (usuario_id) REFERENCES usuarios(id);
