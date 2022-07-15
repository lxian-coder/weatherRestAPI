DROP TABLE IF EXISTS "api_keys";
CREATE TABLE "api_keys"(
                        "id" SERIAL PRIMARY KEY,
                        "key_value" VARCHAR(100),
                        "count_times" NUMBER ,
                        "refresh_time" NUMBER
);

