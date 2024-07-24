CREATE SCHEMA skills AUTHORIZATION postgres;
GRANT
ALL
ON SCHEMA skills TO postgres;
ALTER
ROLE postgres SET search_path TO skills,public;