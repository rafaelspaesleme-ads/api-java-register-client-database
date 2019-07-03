CREATE DATABASE data_center;
CREATE USER postgres WITH PASSWORD $PASS_DATABASE_HOME;
GRANT ALL PRIVILEGES ON DATABASE data_center TO postgres;

connect data_center

CREATE TABLE public.cliente
(
    id serial NOT NULL,
    username_database text NOT NULL,
    password_database text NOT NULL,
    image_docker text,
    name_conteiner text,
    port_conteiner text,
    name_database text NOT NULL,
    active boolean,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT users_username_database_key UNIQUE (username_database),
    CONSTRAINT users_name_conteiner_key UNIQUE (name_conteiner),
    CONSTRAINT users_name_database_key UNIQUE (name_database)

);
ALTER TABLE public.cliente
    OWNER to data_center;