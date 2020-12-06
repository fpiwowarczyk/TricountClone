USE Tricount;


INSERT INTO Room (roomId, name) VALUES (5fa78f14-37bd-11eb-adc1-0242ac120002,'Narty');
INSERT INTO Room (roomId, name) VALUES (5fa7918a-37bd-11eb-adc1-0242ac120002,'Gory');

INSERT INTO Users
(userId, name,password,roomId)
  VALUES (
      5fa7928e-37bd-11eb-adc1-0242ac120002,
      'Filip',
      'mojehaslo',
      5fa7918a-37bd-11eb-adc1-0242ac120002);

//INSERT INTO Users
//(userId, name,password,roomId)
//  VALUES (
//    5fa7928e-37bd-11eb-adc1-0242ac120002,
//    'Filip',
//    'mojehaslo',
//    5fa78f14-37bd-11eb-adc1-0242ac120002);

