CREATE KEYSPACE IF NOT EXISTS Tricount
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

USE Tricount;

CREATE TABLE Room (
  roomId UUID,
  name varchar,
  PRIMARY KEY (roomId,name)
);

CREATE TABLE roomResult (
  room UUID,
  user UUID,
  userName varchar,
  money double,
  PRIMARY KEY (room,user,userName)
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
  PRIMARY KEY (room, payer,paymentId)
);
