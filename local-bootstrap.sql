CREATE DATABASE schedules;
CREATE USER 'pivotal'@'localhost' IDENTIFIED BY 'dev';
GRANT ALL PRIVILEGES ON schedules.* TO 'pivotal'@'localhost';