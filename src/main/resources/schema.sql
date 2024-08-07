CREATE TABLE IF NOT EXISTS Person (
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    login varchar(100) unique not null,
    password varchar(100) not null,
    role varchar(100) not null
);

INSERT INTO Person (login, password, role)
VALUES ('user', 'password', 'ROLE_USER')
ON CONFLICT (login) DO NOTHING;

INSERT INTO Person (login, password, role)
VALUES ('user2', 'password2', 'ROLE_USER')
    ON CONFLICT (login) DO NOTHING;

INSERT INTO Person (login, password, role)
VALUES ('admin', 'password', 'ROLE_ADMIN')
    ON CONFLICT (login) DO NOTHING;

CREATE TABLE IF NOT EXISTS Account (
    id SERIAL PRIMARY KEY,
    balance NUMERIC(10, 2),
    status BOOLEAN NOT NULL,
    person_id INT UNIQUE NOT NULL,
    FOREIGN KEY (person_id) REFERENCES Person (id)
);

INSERT INTO Account (balance, status, person_id)
VALUES (100, true, 1)
ON CONFLICT (person_id) DO NOTHING;

INSERT INTO Account (balance, status, person_id)
VALUES (200, true, 2)
    ON CONFLICT (person_id) DO NOTHING;