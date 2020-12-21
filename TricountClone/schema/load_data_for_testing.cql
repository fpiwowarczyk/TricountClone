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


INSERT INTO Users
    (userId, name,password,roomId)
  VALUES (
    5fa7928e-37bd-11eb-adc1-0242ac120021,
    'Krzysiek',
    'jegohaslo',
    5fa7918a-37bd-11eb-adc1-0242ac120012);



INSERT INTO payments (paymentId,
                        room,
                        amount,
                        payer,
                        receiver) VALUES
                        (ab036100-3b36-11eb-adc1-0242ac120003,
                        ab036100-3b36-11eb-adc1-0242ac120003,
                        30.0,
                        ab036100-3b36-11eb-adc1-0242ac120004,
                        ab036100-3b36-11eb-adc1-0242ac120005);



INSERT INTO payments (paymentId,
                      room,
                      amount,
                      payer,
                      receiver) VALUES
                      (ab036100-3b36-11eb-adc1-0242ac120004,
                      ab036123-3b36-11eb-adc1-0242ac120003,
                      99.0,
                      ab036999-3b36-11eb-adc1-0242ac120321,
                      ab036100-3b36-11eb-adc1-0242ac120005);

INSERT INTO payments (paymentId,
room,
amount,
payer,
receiver) VALUES
(ab036100-3b36-11eb-adc1-0242ac120011,
ab036100-3b36-11eb-adc1-0242ac120003,
99.0,
ab036999-3b36-11eb-adc1-0242ac120004,
ab036100-3b36-11eb-adc1-0242ac120123);


INSERT INTO payments (paymentId,
room,
amount,
payer,
receiver) VALUES
(ab036100-3b36-11eb-adc1-0242ac120012,
ab036100-3b36-11eb-adc1-0242ac120003,
99.0,
ab036999-3b36-11eb-adc1-0242ac120321,
ab036100-3b36-11eb-adc1-0242ac120123);

INSERT INTO roomresult (room,user,money) VALUES
                        (ab036a88-3b36-11eb-adc1-0242ac120002,
                        ab036a88-3b36-11eb-adc1-0242ac120020,
                        -10.0);

INSERT INTO roomresult (room,user,money) VALUES
                        (ab036a88-3b36-11eb-adc1-0242ac120002,
                        ab036a88-3b36-11eb-adc1-0242ac120034,
                        10.0);

