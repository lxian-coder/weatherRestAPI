DROP TABLE IF EXISTS "weather_data";
CREATE TABLE "weather_data"(
                           "id" SERIAL PRIMARY KEY,
                           "city_name" VARCHAR ,
                           "country_name" VARCHAR,
                           "weather_description" VARCHAR,
);
