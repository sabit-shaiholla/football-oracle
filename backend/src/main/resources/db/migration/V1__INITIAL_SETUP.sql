CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    password TEXT NOT NULL
);

CREATE TABLE players (
    player_id BIGSERIAL PRIMARY KEY,
    player_name TEXT NOT NULL UNIQUE,
    player_position TEXT,
    player_age INTEGER,
    birthday TEXT,
    team text
);

CREATE TABLE player_reports (
    report_id BIGSERIAL PRIMARY KEY,
    player_id INTEGER UNIQUE REFERENCES players(player_id),
    player_strengths TEXT,
    player_weaknesses TEXT,
    player_summary TEXT
);

CREATE TABLE user_player_reviews (
    review_id BIGSERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    player_id INTEGER REFERENCES players(player_id),
    report_id INTEGER REFERENCES player_reports(report_id),
    review TEXT,
    rating INTEGER CHECK (rating >= 1 AND rating <= 10)
);