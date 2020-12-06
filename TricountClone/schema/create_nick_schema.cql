CREATE KEYSPACE IF NOT EXISTS Tricount
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE Tricount;

CREATE TABLE Users (
  name varchar,
  userId uuid,
  PRIMARY KEY (nick)
);

INSERT INTO Nicks (nick) VALUES ('robert');
INSERT INTO Nicks (nick) VALUES ('marcin');
INSERT INTO Nicks (nick) VALUES ('maciej');
INSERT INTO Nicks (nick) VALUES ('filip');
