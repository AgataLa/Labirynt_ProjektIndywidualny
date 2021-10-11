\c labirynt_database

CREATE TABLE Easy( id serial
                , nickname varchar(30) not null
                , time time not null);
CREATE TABLE Medium( id serial
                , nickname varchar(30) not null
                , time time not null);
CREATE TABLE Hard( id serial
                , nickname varchar(30) not null
                , time time not null);

CREATE OR REPLACE FUNCTION easyMaxSize() RETURNS trigger AS $easyMaxSize$
    DECLARE numberOfRows integer;
    BEGIN
        SELECT COUNT(ID) INTO numberOfRows FROM Easy;
        IF numberOfRows > 20 THEN
            DELETE FROM Easy WHERE id = ( SELECT id FROM Easy ORDER BY time DESC LIMIT 1);
        END IF;
        RETURN NEW;
    END;
$easyMaxSize$ LANGUAGE plpgsql;

CREATE TRIGGER easyMaxSizeCheck AFTER INSERT
    ON Easy
    EXECUTE PROCEDURE easyMaxSize();

CREATE OR REPLACE FUNCTION mediumMaxSize() RETURNS trigger AS $mediumMaxSize$
    DECLARE numberOfRows integer;
    BEGIN
        SELECT COUNT(ID) INTO numberOfRows FROM Medium;
        IF numberOfRows > 20 THEN
            DELETE FROM Medium WHERE id = ( SELECT id FROM Medium ORDER BY time DESC LIMIT 1);
        END IF;
        RETURN NEW;
    END;
$mediumMaxSize$ LANGUAGE plpgsql;

CREATE TRIGGER mediumMaxSizeCheck AFTER INSERT
    ON Medium
    EXECUTE PROCEDURE mediumMaxSize();

CREATE OR REPLACE FUNCTION hardMaxSize() RETURNS trigger AS $hardMaxSize$
    DECLARE numberOfRows integer;
    BEGIN
        SELECT COUNT(ID) INTO numberOfRows FROM Hard;
        IF numberOfRows > 20 THEN
            DELETE FROM Hard WHERE id = ( SELECT id FROM Hard ORDER BY time DESC LIMIT 1);
        END IF;
        RETURN NEW;
    END;
$hardMaxSize$ LANGUAGE plpgsql;

CREATE TRIGGER hardMaxSizeCheck AFTER INSERT
    ON Hard
    EXECUTE PROCEDURE hardMaxSize();

GRANT SELECT, INSERT, DELETE ON ALL TABLES IN SCHEMA public TO labirynt_user;
GRANT SELECT, USAGE ON ALL SEQUENCES IN SCHEMA public TO labirynt_user;