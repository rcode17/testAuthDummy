CREATE TABLE IF NOT EXISTS login_log (
    id SERIAL PRIMARY KEY,
    username VARCHAR(100) NOT NULL,
    login_time TIMESTAMP NOT NULL,
    access_token TEXT NOT NULL,
    refresh_token TEXT NOT NULL
);
