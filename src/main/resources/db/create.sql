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
    PRIMARY KEY (id),
    UNIQUE (title)
);

CREATE TABLE sessions
(
    id integer NOT NULL,
    movie_id integer NOT NULL,
    begin_date timestamp without time zone NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (begin_date),
    FOREIGN KEY (movie_id) REFERENCES movies (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE users
(
    id integer NOT NULL,
    email character varying(255) COLLATE pg_catalog."default" NOT NULL,
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    password character varying(255) COLLATE pg_catalog."default" NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (email)
);

CREATE TABLE roles
(
    id bigint NOT NULL,
    name character varying(255) COLLATE pg_catalog."default",
    PRIMARY KEY (id)
);

CREATE TABLE user_roles
(
    user_id integer NOT NULL,
    role_id bigint NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (role_id) REFERENCES roles (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (user_id) REFERENCES users (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);

CREATE TABLE tickets
(
    id integer NOT NULL,
    session_id integer NOT NULL,
    row integer NOT NULL,
    place integer NOT NULL,
    placestatus integer NOT NULL,
    user_email character varying(255) NOT NULL,
    price double precision,
    PRIMARY KEY (id),
    FOREIGN KEY (session_id) REFERENCES sessions (id) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION,
    FOREIGN KEY (user_email) REFERENCES users (email) MATCH SIMPLE ON UPDATE NO ACTION ON DELETE NO ACTION
);
