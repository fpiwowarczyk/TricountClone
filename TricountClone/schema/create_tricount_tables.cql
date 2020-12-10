CREATE KEYSPACE IF NOT EXISTS Tricount
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE Tricount;

CREATE TABLE Room (
  roomId UUID,
  name varchar,
  PRIMARY KEY (roomId)
);

CREATE TABLE roomResult (
  room UUID,
  user UUID,
  money double,
  PRIMARY KEY (room)
);

CREATE TABLE Users (
  userId UUID,
  name varchar,
  password varchar,
  roomId UUID,
  PRIMARY KEY (name,password, userId, roomId)
);

CREATE TABLE Payments (
  paymentId UUID,
  room UUID,
  amount double,
  payer uuid,
  receiver uuid,
  // date ????
  PRIMARY KEY (room, payer)
);
