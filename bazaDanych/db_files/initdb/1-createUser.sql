CREATE USER labirynt_user WITH
    LOGIN
    PASSWORD 'passlabapp'
    NOSUPERUSER
    INHERIT
    NOCREATEDB
    NOCREATEROLE;

CREATE DATABASE labirynt_database WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    TABLESPACE = pg_default
    LC_CTYPE = 'pl_PL.UTF-8'
    LC_COLLATE = 'pl_PL.UTF-8'
    CONNECTION LIMIT = -1;

GRANT CONNECT ON DATABASE labirynt_database TO labirynt_user;
