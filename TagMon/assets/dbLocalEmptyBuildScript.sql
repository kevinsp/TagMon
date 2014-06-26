CREATE TABLE "tagdb_Player"(
"name" varchar(50)
);
CREATE TABLE "tagdb_stats" (
    "id" integer NOT NULL PRIMARY KEY,
    "maxHP" integer NOT NULL,
    "curHP" integer NOT NULL,
    "maxEP" integer NOT NULL,
    "curEP" integer NOT NULL,
    "regEP" integer NOT NULL,
    "lvl" integer NOT NULL,
    "curEXP" integer NOT NULL,
    "defense" integer NOT NULL,
    "monster_id" integer NOT NULL UNIQUE REFERENCES "tagdb_monster" ("id")
);
CREATE TABLE "tagdb_buff" (
    "id" integer NOT NULL PRIMARY KEY,
    "dauer" integer NOT NULL,
    "ziel" integer NOT NULL,
    "bStaerke" integer NOT NULL,
    "bIntelligenz" integer NOT NULL,
    "bKonstitution" integer NOT NULL,
    "faehigkeit_id" integer NOT NULL REFERENCES "tagdb_faehigkeit" ("id")
);
CREATE TABLE "tagdb_faehigkeit" (
    "id" integer NOT NULL PRIMARY KEY,
    "name" varchar(50) NOT NULL,
    "ziel" integer NOT NULL,
    "energiekosten" integer NOT NULL,
    "angriffsziel" integer NOT NULL,
    "angriffswert" integer NOT NULL,
    "heilungsziel" integer NOT NULL,
    "heilungswert" integer NOT NULL,
    "stunziel" integer NOT NULL,
    "stundauer" integer NOT NULL,
    "absorbationsziel" integer NOT NULL,
    "absorbationsdauer" integer NOT NULL,
    "schadensabsorbation" integer NOT NULL,
    "cooldown" integer NOT NULL,
    "image" varchar(100)
);
CREATE TABLE "tagdb_faehigkeit_koerperteile" (
    "id" integer NOT NULL PRIMARY KEY,
    "faehigkeit_id" integer NOT NULL,
    "koerperteile_id" integer NOT NULL REFERENCES "tagdb_koerperteile" ("id"),
    UNIQUE ("faehigkeit_id", "koerperteile_id")
);
CREATE TABLE "tagdb_attributmodifikator" (
    "id" integer NOT NULL PRIMARY KEY,
    "staerke" integer NOT NULL,
    "intelligenz" integer NOT NULL,
    "konstitution" integer NOT NULL,
    "koerperteile_id" integer NOT NULL UNIQUE REFERENCES "tagdb_koerperteile" ("id")
);
CREATE TABLE "tagdb_koerperteile" (
    "id" integer NOT NULL PRIMARY KEY,
    "name" varchar(50) NOT NULL,
    "art" integer NOT NULL,
    "image" varchar(100)
);
CREATE TABLE "tagdb_koerperteile_monster" (
    "id" integer NOT NULL PRIMARY KEY,
    "koerperteile_id" integer NOT NULL,
    "monster_id" integer NOT NULL REFERENCES "tagdb_monster" ("id"),
    UNIQUE ("koerperteile_id", "monster_id")
);
CREATE TABLE "tagdb_attribute" (
    "id" integer NOT NULL PRIMARY KEY,
    "staerke" integer NOT NULL,
    "intelligenz" integer NOT NULL,
    "konstitution" integer NOT NULL,
    "monster_id" integer NOT NULL UNIQUE REFERENCES "tagdb_monster" ("id")
);
CREATE TABLE "tagdb_monster" (
    "id" integer NOT NULL PRIMARY KEY,
    "name" varchar(50) NOT NULL,
    "beschreibung" varchar(1000) NOT NULL,
    "image" varchar(100)
);
CREATE INDEX "tagdb_buff_f3d68923" ON "tagdb_buff" ("faehigkeit_id");
CREATE INDEX "tagdb_faehigkeit_koerperteile_883604fe" ON "tagdb_faehigkeit_koerperteile" ("koerperteile_id");
CREATE INDEX "tagdb_faehigkeit_koerperteile_f3d68923" ON "tagdb_faehigkeit_koerperteile" ("faehigkeit_id");
CREATE INDEX "tagdb_koerperteile_monster_686ce9b2" ON "tagdb_koerperteile_monster" ("monster_id");
CREATE INDEX "tagdb_koerperteile_monster_883604fe" ON "tagdb_koerperteile_monster" ("koerperteile_id");
;
;
;
;
;
COMMIT;
