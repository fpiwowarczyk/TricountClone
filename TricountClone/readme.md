```
USE Test;
CREATE TABLE Nicks (
  nick varchar,
  status varchar,
  PRIMARY KEY (nick)
);
INSERT INTO Nicks (nick) VALUES ('robert');
INSERT INTO Nicks (nick VALUES ('marcin');
INSERT INTO Nicks (nick) VALUES ('maciej');
INSERT INTO Nicks (nick) VALUES ('filip');
```
```
TABLE Pokoj (
    roomId UUID,
    name varchar,
    PRIMARY KEY (roomId)
    )
TABLE Rozliczenie (
    rooom UUID,
    user uuid,
    money double
)
    
TABLE Users (
    userId UUID,
    name varchar,
    roomId uuid[],
    PRIMARY KEY (name, userId)
)
TABLE Paymants (
    paymenyId UUID,
    room UUID,
    amount dobule,
    whoPaid uuid,
    whoGet uuid
    PRIMARY KEY (room, uuid)
)

```
