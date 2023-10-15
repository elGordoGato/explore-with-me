CREATE TABLE IF NOT EXISTS category
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY,
    name VARCHAR(50) NOT NULL,
    CONSTRAINT pk_category
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS compilation
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY,
    pinned BOOLEAN     NOT NULL,
    title  VARCHAR(50) NOT NULL,
    CONSTRAINT pk_compilation
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS location
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY,
    lat DOUBLE PRECISION NOT NULL,
    lon DOUBLE PRECISION NOT NULL,
    CONSTRAINT pk_location
        PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS area
(
    id          BIGINT GENERATED BY DEFAULT AS IDENTITY,
    radius      REAL         NOT NULL,
    title       VARCHAR(225) NOT NULL,
    location_id BIGINT       NOT NULL,
    CONSTRAINT area_pkey
        PRIMARY KEY (id),
    CONSTRAINT fk_area_on_location
        FOREIGN KEY (location_id) REFERENCES location
);

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY,
    email VARCHAR(254) NOT NULL,
    name  VARCHAR(250) NOT NULL,
    CONSTRAINT users_pkey
        PRIMARY KEY (id),
    CONSTRAINT uc_users_email
        UNIQUE (email),
    CONSTRAINT uc_users_name
        UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS EVENT
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY,
    initiator_id       BIGINT        NOT NULL,
    location_id        BIGINT        NOT NULL,
    annotation         VARCHAR(2000) NOT NULL,
    category_id        BIGINT        NOT NULL,
    created_on         TIMESTAMP,
    description        VARCHAR(7000) NOT NULL,
    event_date         TIMESTAMP     NOT NULL,
    paid               BOOLEAN       NOT NULL,
    participant_limit  INTEGER       NOT NULL,
    published_on       TIMESTAMP,
    request_moderation BOOLEAN       NOT NULL,
    state              VARCHAR(50)   NOT NULL,
    title              VARCHAR(120),
    CONSTRAINT pk_event
        PRIMARY KEY (id),
    CONSTRAINT fk_event_on_category
        FOREIGN KEY (category_id) REFERENCES CATEGORY,
    CONSTRAINT fk_event_on_initiator
        FOREIGN KEY (initiator_id) REFERENCES users,
    CONSTRAINT fk_event_on_location
        FOREIGN KEY (location_id) REFERENCES LOCATION
);

CREATE TABLE IF NOT EXISTS compilation_events
(
    compilation_entity_id BIGINT NOT NULL,
    events_id             BIGINT NOT NULL,
    CONSTRAINT uc_compilation_events_events
        UNIQUE (events_id),
    CONSTRAINT fk_comeve_on_compilation_entity
        FOREIGN KEY (compilation_entity_id) REFERENCES compilation,
    CONSTRAINT fk_comeve_on_event_entity
        FOREIGN KEY (events_id) REFERENCES EVENT
);

CREATE TABLE IF NOT EXISTS request
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY,
    user_id      BIGINT,
    event_id     BIGINT,
    created      TIMESTAMP,
    status       VARCHAR(50) NOT NULL,
    requester_id BIGINT,
    CONSTRAINT pk_request
        PRIMARY KEY (id),
    CONSTRAINT uniquerequesterforevent
        UNIQUE (requester_id, event_ID),
    CONSTRAINT fk_request_on_event
        FOREIGN KEY (event_id) REFERENCES EVENT,
    CONSTRAINT fk_request_on_user
        FOREIGN KEY (requester_id) REFERENCES users
);


CREATE OR REPLACE FUNCTION distance(lat1 float, lon1 float, lat2 float, lon2 float) RETURNS float
    LANGUAGE plpgsql
AS
'
    DECLARE
        dist      float = 0;
        rad_lat1  float;
        rad_lat2  float;
        theta     float;
        rad_theta float;
    BEGIN
        IF lat1 = lat2 AND lon1 = lon2
        THEN
            RETURN dist;
        ELSE
            -- ПЕРЕВОДИМ ГРАДУСЫ ШИРОТЫ В РАДИАНЫ
            rad_lat1 = pi() * lat1 / 180;
            -- ПЕРЕВОДИМ ГРАДУСЫ ДОЛГОТЫ В РАДИАНЫ
            rad_lat2 = pi() * lat2 / 180;
            -- НАХОДИМ РАЗНОСТЬ ДОЛГОТ
            theta = lon1 - lon2;
            -- ПЕРЕВОДИМ ГРАДУСЫ В РАДИАНЫ
            rad_theta = pi() * theta / 180;
            -- НАХОДИМ ДЛИНУ ОРТОДРОМИИ
            dist = sin(rad_lat1) * sin(rad_lat2) + cos(rad_lat1) * cos(rad_lat2) * cos(rad_theta);

            IF dist > 1
            THEN
                dist = 1;
            END IF;

            dist = acos(dist);
            -- ПЕРЕВОДИМ РАДИАНЫ В ГРАДУСЫ
            dist = dist * 180 / pi();
            -- ПЕРЕВОДИМ ГРАДУСЫ В КИЛОМЕТРЫ
            dist = dist * 60 * 1.8524;

            RETURN dist;
        END IF;
    END;
';
