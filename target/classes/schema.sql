CREATE TABLE IF NOT EXISTS wells(
    id integer PRIMARY KEY,
    name varchar(32) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS equipment(
    id integer PRIMARY KEY,
    name varchar(32) UNIQUE NOT NULL,
    well_id integer REFERENCES wells (id)
);
