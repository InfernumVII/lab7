CREATE TABLE auth (id SERIAL PRIMARY KEY, username varchar(40) UNIQUE, password varchar(40));

CREATE SEQUENCE dragon_id_seq START 1 INCREMENT 1;

CREATE TYPE color_enum AS ENUM ('YELLOW', 'ORANGE', 'WHITE', 'BROWN');
CREATE TYPE dragon_character_enum AS ENUM ('WISE', 'EVIL', 'CHAOTIC_EVIL', 'FICKLE');
CREATE TYPE dragon_type_enum AS ENUM ('WATER', 'UNDERGROUND', 'AIR', 'FIRE');

CREATE TABLE dragon_heads (
    head_id SERIAL PRIMARY KEY,
    eyes_count FLOAT
);

CREATE TABLE dragons (
    id INTEGER PRIMARY KEY DEFAULT nextval('dragon_id_seq'),
    name VARCHAR(255) NOT NULL CHECK (name <> ''),
    creation_date DATE NOT NULL DEFAULT CURRENT_DATE,
    age BIGINT NOT NULL CHECK (age > 0),
    color color_enum NOT NULL,
    type dragon_type_enum NOT NULL,
    character dragon_character_enum NOT NULL,
    
    -- Ссылки на связанные таблицы с каскадным удалением
    coord_id INTEGER REFERENCES coordinates(coord_id) ON DELETE CASCADE,
    head_id INTEGER REFERENCES dragon_heads(head_id) ON DELETE CASCADE
);