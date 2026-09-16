CREATE TABLE team (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name VARCHAR(100) NOT NULL,
    matches_played INTEGER DEFAULT 0,
    wins INTEGER DEFAULT 0,
    draws INTEGER DEFAULT 0,
    losses INTEGER DEFAULT 0,
    goals_scored INTEGER DEFAULT 0,
    goals_conceded INTEGER DEFAULT 0,
    points INTEGER DEFAULT 0
);

CREATE TABLE fixture (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    match_week INTEGER NOT NULL,               -- Forduló száma (pl. 1, 2, ..., 38)
    match_number_in_week INTEGER NOT NULL,    -- Sorszám a fordulón belül (pl. 1..10)
    home_team_id INTEGER NOT NULL,
    away_team_id INTEGER NOT NULL,
    match_date TEXT,                          -- Meccs dátuma (ISO format: YYYY-MM-DD HH:MM)

    -- Eredmény
    home_score INTEGER DEFAULT 0,
    away_score INTEGER DEFAULT 0,

    -- Hazai csapat statisztikák
    home_big_chances INTEGER DEFAULT 0,
    home_small_chances INTEGER DEFAULT 0,
    home_yellow_cards INTEGER DEFAULT 0,
    home_red_cards INTEGER DEFAULT 0,

    -- Vendég csapat statisztikák
    away_big_chances INTEGER DEFAULT 0,
    away_small_chances INTEGER DEFAULT 0,
    away_yellow_cards INTEGER DEFAULT 0,
    away_red_cards INTEGER DEFAULT 0,

    -- Státusz flag (0 = még nincs lejátszva, 1 = befejezett)
    is_finished BOOLEAN DEFAULT 0,

    FOREIGN KEY (home_team_id) REFERENCES team(id),
    FOREIGN KEY (away_team_id) REFERENCES team(id)
);