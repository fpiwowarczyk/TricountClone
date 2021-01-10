USE Tricount;


INSERT INTO Room (roomId, name) VALUES (310ec154-511c-11eb-ae93-0242ac130002,'Morze');
INSERT INTO Room (roomId, name) VALUES (36904260-511c-11eb-ae93-0242ac130002,'Wycieczka');

INSERT INTO Users (userId, name,password,roomId) VALUES (7975e012-511c-11eb-ae93-0242ac130002,'Jan','Jan1',310ec154-511c-11eb-ae93-0242ac130002);
INSERT INTO Users (userId, name,password,roomId) VALUES (d3020a16-511c-11eb-ae93-0242ac130002,'Pawel','Pawel1',310ec154-511c-11eb-ae93-0242ac130002);
INSERT INTO Users (userId, name,password,roomId) VALUES (6969e012-511c-11eb-ae93-0242ac130002,'Krzysiu','krzysiu1',310ec154-511c-11eb-ae93-0242ac130002);
INSERT INTO Users (userId, name,password,roomId) VALUES (13370a16-511c-11eb-ae93-0242ac130002,'Jadzia','jadzia1',310ec154-511c-11eb-ae93-0242ac130002);
INSERT INTO Users (userId, name,password,roomId) VALUES (4200e012-511c-11eb-ae93-0242ac130002,'Kordian','kordian1',310ec154-511c-11eb-ae93-0242ac130002);



INSERT INTO roomresult (room,user,userName,money) VALUES (310ec154-511c-11eb-ae93-0242ac130002,7975e012-511c-11eb-ae93-0242ac130002,'Jan',0.0);
INSERT INTO roomresult (room,user,userName,money) VALUES (310ec154-511c-11eb-ae93-0242ac130002,d3020a16-511c-11eb-ae93-0242ac130002,'Pawel',0.0);
INSERT INTO roomresult (room,user,userName,money) VALUES (310ec154-511c-11eb-ae93-0242ac130002,6969e012-511c-11eb-ae93-0242ac130002,'Krzysiu',0.0);
INSERT INTO roomresult (room,user,userName,money) VALUES (310ec154-511c-11eb-ae93-0242ac130002,13370a16-511c-11eb-ae93-0242ac130002,'Jadzia',0.0);
INSERT INTO roomresult (room,user,userName,money) VALUES (310ec154-511c-11eb-ae93-0242ac130002,4200e012-511c-11eb-ae93-0242ac130002,'Kordian',0.0);


