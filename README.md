# Tricount Clone Work in progress
Clone of popular application Tricount. Implemented as console app using Java and
Cassandra as Database.

## How it works 
You as a user and group of friends make room where each of users can put their 
payment if they bought someone anything. App will calculate final debts and tell
users who have to return money to whom.It makes easier counting money on trips. 
Usually you have pay only one person instead of few in the end. 

## Schema
``` CQL 
CREATE TABLE Pokoj (
    roomId UUID,
    name varchar,
    PRIMARY KEY (roomId)
    ); // Dane pokoju rozliczeniowego 
    
TABLE roomResult (
    room UUID,
    user uuid,
    money double
    PRIMARY KEY (room)
    ); // Przetrzymywanie aktualnego wyniki rozliczenia danego pokoju dla
       //kazdego uzytkownika osobno
    
TABLE Users (
    userId UUID,
    name varchar,
    roomId uuid,
    PRIMARY KEY (name, userId)
    ); // Aktualne dane o uzytkowniku 
    
TABLE Paymants (
    paymenyId UUID,
    room UUID,
    amount dobule,
    whoPaid uuid,
    whoGet uuid
    ??? Date ??????
    PRIMARY KEY (room, uuid)
    ); // Zapis historii dla wszystkich tranzakcji 
```

## How it works

### Start
1. User log in with name and password. It let's app to identify user. 
2. Query for room for given user and show them. 
3. User select room he would like to see.
4. Query for all roomResult for given room. 

### Add transaction

1. User add his transaction 
2. Inert payment into payments
3. Update/Add new roomResult

### Request for room transactions history 

1. User select that option
2. Query for all payments for room
3. Print all transactions for that room. 
