CREATE TABLE pixel_request
(
    id    INTEGER PRIMARY KEY AUTOINCREMENT, -- The primary key with auto-increment
    x     INTEGER NOT NULL,                  -- The x coordinate
    y     INTEGER NOT NULL,                  -- The y coordinate
    color TEXT,                              -- The color field (TEXT type in SQLite)
    token INTEGER NOT NULL                   -- The token field
);

