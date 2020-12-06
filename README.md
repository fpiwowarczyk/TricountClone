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
CREATE TABLE Room (
    roomId UUID,
    name varchar,
    PRIMARY KEY (roomId)
); // Dane pokoju rozliczeniowego 
    
CREATE TABLE roomResult (
    room UUID,
    user UUID,
    money double,
    PRIMARY KEY (room)
); // Przetrzymywanie aktualnego wyniki rozliczenia danego pokoju dla
       //kazdego uzytkownika osobno
    
CREATE TABLE Users (
    userId UUID,
    name varchar,
    password varchar,
    roomId UUID,
    PRIMARY KEY (name,password, userId)
); // Aktualne dane o uzytkowniku 
    
CREATE TABLE Payments (
    paymenyId UUID,
    room UUID,
    amount double,
    payer uuid,
    receiver uuid,
    // date ????
    PRIMARY KEY (room, payer)
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
3. App locally split payment into receivers 
4. Insert individually all payments 
5. Update/Add new roomResult

Example :

Filip pay 10 $ for Kasia and Kuba.

App Split it into 5$ for each Kasia and Kuba.

So we have two payments. 

Result:
* Filip: 10 $
* Kasia: -5$
* Kuba: -5$

Mby add date to history payments and add all payments on one date to show
in history real transactions. In these example to have only one transaction in history
not two of them. 
### Request for room transactions history 

1. User select that option
2. Query for all payments for room
2.5. Reduce all historical transactions with the same date. 
3. Print all transactions for that room. 

## Conclusions 

Will add after finish. 