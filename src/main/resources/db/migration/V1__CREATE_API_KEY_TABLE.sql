DROP TABLE IF EXISTS "APIkey";
CREATE TABLE "APIkey"(
                        "id" SERIAL PRIMARY KEY,
                        "api_key" VARCHAR,
                        "refresh_time" VARCHAR NOT NULL
);