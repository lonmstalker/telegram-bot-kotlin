CREATE TABLE IF NOT EXISTS user_info
(
    id             BIGINT PRIMARY KEY,
    role           INT       NOT NULL DEFAULT 0,
    created_date   TIMESTAMP NOT NULL DEFAULT now(),
    locale         VARCHAR(2)         DEFAULT 'ru' NOT NULL,
    complete_start BOOLEAN            DEFAULT false NOT NULL
);

CREATE TABLE IF NOT EXISTS migration_info
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(100),
    created_date TIMESTAMP DEFAULT now()
);