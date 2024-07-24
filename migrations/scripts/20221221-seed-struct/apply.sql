-- Create Tables

CREATE TABLE "person"
(
    "person_id" SERIAL4 NOT NULL,
    "email"     TEXT,
    "forenames" TEXT,
    "surname"   TEXT,
    PRIMARY KEY ("person_id"),
    UNIQUE ("email")
);

CREATE TABLE "skill"
(
    "skill_id"   SERIAL4 NOT NULL,
    "skill_name" TEXT    NOT NULL,
    PRIMARY KEY ("skill_id"),
    UNIQUE ("skill_name")
);

CREATE TABLE "skill_level"
(
    "skill_level_id"          SERIAL4 NOT NULL,
    "skill_level_order"       INTEGER NOT NULL,
    "skill_level_description" TEXT    NOT NULL,
    PRIMARY KEY ("skill_level_id")
);

CREATE TABLE "skill_ownership"
(
    "skill_ownership_id" SERIAL4 NOT NULL,
    "person_id"          INTEGER NOT NULL,
    "skill_id"           INTEGER NOT NULL,
    "skill_level_id"     INTEGER NOT NULL,
    PRIMARY KEY ("skill_ownership_id"),
    FOREIGN KEY("person_id") REFERENCES person("person_id") ON DELETE CASCADE,
    FOREIGN KEY("skill_id") REFERENCES skill("skill_id") ON DELETE CASCADE,
    FOREIGN KEY("skill_level_id") REFERENCES skill_level("skill_level_id") ON DELETE CASCADE,
    UNIQUE ("person_id", "skill_id")
);

