CREATE TABLE movies
(
    id integer NOT NULL,
    title character varying(200) NOT NULL,
    part character varying(255),
    year integer,
    duration integer NOT NULL,
    director character varying,
    stars character varying(255),
    poster character varying(255),
    rating character varying(10),
    discription text,
    CONSTRAINT movies_pkey PRIMARY KEY (id),
    CONSTRAINT uk_govm2dombrdnujupo3o7hvtep UNIQUE (title)
);

CREATE TABLE sessions
(
    id integer NOT NULL,
    movie_id integer NOT NULL,
    begin_date timestamp without time zone NOT NULL,
    CONSTRAINT sessions_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6yiardtvml9bvef97ay6vm8xg UNIQUE (begin_date),
    CONSTRAINT fkmfu1pgfp4c0bhre1hm7308oh FOREIGN KEY (movie_id) REFERENCES movies (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE users
(
    id integer NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email)
);

CREATE TABLE roles
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id integer NOT NULL,
    role_id bigint NOT NULL,
    CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
    CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES roles (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tickets
(
    id integer NOT NULL,
    session_id integer NOT NULL,
    row integer NOT NULL,
    place integer NOT NULL,
    placestatus integer NOT NULL,
    email character varying(255) COLLATE pg_catalog."default",
    price double precision,
    CONSTRAINT tickets_pkey PRIMARY KEY (id),
    CONSTRAINT fkgu5xxsadbdouq1gabs56duq8k FOREIGN KEY (session_id) REFERENCES sessions (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
)