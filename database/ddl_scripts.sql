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

CREATE TABLE IF NOT EXISTS player (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    team_id INTEGER NOT NULL,
    nationality TEXT,
    age INTEGER,
    primary_position TEXT NOT NULL,
    other_positions TEXT,                     -- Vesszővel elválasztva (pl. "CAM, RW")
    preferred_foot VARCHAR(1),                -- 'R' (Jobb), 'L' (Bal), 'B' (Mindkét lábas)
    shirt_number INTEGER NOT NULL DEFAULT 1 CHECK (shirt_number BETWEEN 1 AND 99),

    -- Képességek / Erőnlét (0-100)
    overall INTEGER CHECK (overall BETWEEN 0 AND 100),
    big_chance_finishing INTEGER CHECK (big_chance_finishing BETWEEN 0 AND 100),
    small_chance_finishing INTEGER CHECK (small_chance_finishing BETWEEN 0 AND 100),
    energy INTEGER DEFAULT 100 CHECK (energy BETWEEN 0 AND 100),

    -- Sérülés és eltiltás
    injured_for INTEGER DEFAULT 0,
    excluded_for INTEGER DEFAULT 0,

    -- Szezonbeli statisztikák
    matches_played INTEGER DEFAULT 0,
    number_of_goals INTEGER DEFAULT 0,
    clean_sheets INTEGER DEFAULT 0,
    number_of_yellow_cards INTEGER DEFAULT 0,
    number_of_red_cards INTEGER DEFAULT 0,

    -- Kapcsolat a team táblával
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS match_lineup (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fixture_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    position TEXT NOT NULL,                  -- Az adott meccsen betöltött pozíció (pl. "GK", "CB", "ST")

    -- Idegen kulcsok és törlési szabályok
    FOREIGN KEY (fixture_id) REFERENCES fixture(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE,

    -- Egy játékos egy adott meccsen csak egyszer szerepelhet a keretben
    UNIQUE (fixture_id, player_id)
);

-- 1. GÓLOK TÁBLA
CREATE TABLE IF NOT EXISTS goal (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fixture_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,               -- Melyik csapat szerezte
    player_id INTEGER NOT NULL,             -- Gólszerző játékos
    FOREIGN KEY (fixture_id) REFERENCES fixture(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);

-- 2. SÁRGA LAPOK TÁBLA
CREATE TABLE IF NOT EXISTS yellow_card (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fixture_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    FOREIGN KEY (fixture_id) REFERENCES fixture(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);

-- 3. PIROS LAPOK TÁBLA
CREATE TABLE IF NOT EXISTS red_card (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fixture_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    FOREIGN KEY (fixture_id) REFERENCES fixture(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);

-- 4. SÉRÜLÉSEK TÁBLA
CREATE TABLE IF NOT EXISTS injury (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    fixture_id INTEGER NOT NULL,
    team_id INTEGER NOT NULL,
    player_id INTEGER NOT NULL,
    missed_matches INTEGER NOT NULL DEFAULT 1, -- Hány meccset kell kihagynia
    FOREIGN KEY (fixture_id) REFERENCES fixture(id) ON DELETE CASCADE,
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE,
    FOREIGN KEY (player_id) REFERENCES player(id) ON DELETE CASCADE
);