-- Deletion sequence

DROP TABLE activity_person;
DROP TABLE activity_interest;
DROP TABLE activity;
DROP TABLE person_interest;
DROP TABLE interest;
DROP TABLE person;
DROP TABLE post;
DROP TABLE town;

-- Creation sequence

CREATE TABLE town(town_id INT NOT NULL,
                    town_name VARCHAR(30) NOT NULL,
                    PRIMARY KEY(town_id));

INSERT INTO town VALUES(1, 'Trondheim');
INSERT INTO town VALUES(2, 'Melhus');
INSERT INTO town VALUES(3, 'Klæbu');
INSERT INTO town VALUES(57, 'Bergen');
INSERT INTO town VALUES(43, 'Oslo');
INSERT INTO town VALUES(80, 'Tromsø');

CREATE TABLE post (post_id INT NOT NULL,
                    post_address VARCHAR(30) NOT NULL,
                    town_id INT NOT NULL,
                    PRIMARY KEY(post_id));

ALTER TABLE post
ADD FOREIGN KEY(town_id)
REFERENCES town(town_id);

INSERT INTO post VALUES(7227, 'Gimse', 2);
INSERT INTO post VALUES(7050, 'Trondheim', 1);
INSERT INTO post VALUES(7051, 'Trondheim', 1);
INSERT INTO post VALUES(7052, 'Trondheim', 1);
INSERT INTO post VALUES(7023, 'Trondheim', 1);
INSERT INTO post VALUES(7015, 'Trondheim', 1);
INSERT INTO post VALUES(7014, 'Trondheim', 1);
INSERT INTO post VALUES(7145, 'Klæbuveien', 3);
INSERT INTO post VALUES(7234, 'Ler', 2);
INSERT INTO post VALUES(5000, 'Fiskebrygga', 57);
INSERT INTO post VALUES(0000, 'Norgenullpunktet', 43);
INSERT INTO post VALUES(1000, 'Osloveien', 43);
INSERT INTO post VALUES(1001, 'OsloveienLight', 43);

CREATE TABLE person(person_id INT NOT NULL,
                    last_name VARCHAR(50) NOT NULL,
                    first_name VARCHAR(70) NOT NULL,
                    age INT,
                    address VARCHAR(50),
                    post_id INT,
                    PRIMARY KEY(person_id));

ALTER TABLE person
ADD FOREIGN KEY(post_id)
REFERENCES post(post_id);

INSERT INTO person VALUES(1, 'Torbjørn', 'Langland', 26, 'Moholt Allé 03-32 H0302', 7050);
INSERT INTO person VALUES(2, 'Tove', 'Johansen', 61, 'Moholt Allé 555-555-555', 7050);
INSERT INTO person VALUES(3, 'Bjørn', 'Johansen', 63, 'Birkenveien', 7015);
INSERT INTO person VALUES(4, 'Trine Katrine', 'Larsen', 72, 'Moholt Alle 03-32 H0302', 7052);
INSERT INTO person VALUES(5, 'Ludvig', 'Hansen', 56, 'Lersveien', 7234);
INSERT INTO person VALUES(6, 'Stine Kristine', 'Stinesen', 69, 'Røsslyngveien 7', 7227);
INSERT INTO person VALUES(7, 'Hans Hansen', 'Hansensen', 77, 'Måssåveien 15', 7227);
INSERT INTO person VALUES(8, 'Anonym', 'Anonymious', NULL, NULL, NULL);         -- For testing med null-verdier
INSERT INTO person VALUES(9, 'Ingkongnito', 'Anonymious', NULL, NULL, 7050);    -- For testing med null-verdier
INSERT INTO person VALUES(10, 'U-Known', 'Anonymious', 38, NULL, NULL);         -- For testing med null-verdier
INSERT INTO person VALUES(11, 'Tove', 'Larsen', 65, 'Klæbuveien 5', 7145);
INSERT INTO person VALUES(12, 'Sigrid', 'Svansen', 66, 'Moholtveien 5', 7052);
INSERT INTO person VALUES(13, 'Lise', 'Karslen', 66, 'Moholtveien 6', 7052);
INSERT INTO person VALUES(14, 'Lars', 'Larsen', 66, 'Moholtveien 7', 7052);
INSERT INTO person VALUES(15, 'Jens', 'Stoltenjern', 66, 'Moholtveien 8', 7052);
INSERT INTO person VALUES(16, 'User', 'User', NULL, NULL, NULL); -- Chosen test user for the Java application

CREATE TABLE interest(interest_id INT NOT NULL,
                        interest_name VARCHAR(40) UNIQUE NOT NULL,
                        interest_description VARCHAR(150),
                        PRIMARY KEY(interest_id));

INSERT INTO interest VALUES(1, 'Teater og kultur', 
'Forestillinger på teater, besøk på museum, informasjon og ordninger til kulturarrangement.');
INSERT INTO interest VALUES(2, 'Tur ut i naturen', 'For deg som er glad i tur på fjell, skog og mark');
INSERT INTO interest VALUES(3, 'Ski og snø', 'For deg som liker å gå på ski');
INSERT INTO interest VALUES(4, 'NINTNEDO', 'For den som liker å spille på Nintendo i fritiden');
INSERT INTO interest VALUES(5, 'Vaffelkos', 'For den som er glad i å samles for å lage vaffler');
INSERT INTO interest VALUES(6, 'Sang og musikk', 'Alt fra småsamlinger til arrangementer for de sangglade');
INSERT INTO interest VALUES(7, 'Politikk', 'For de samfunnsinteresserte som diskuterer hvor samfunnet går i dag');
INSERT INTO interest VALUES(8, 'Media og internett', 
'For de som har interesse for medie og internett. Alt fra brukergrupper til kurs');
INSERT INTO interest VALUES(9, 'The secret interest with no description', NULL);

CREATE TABLE person_interest(person_id INT NOT NULL,
                                interest_id INT NOT NULL,
                                PRIMARY KEY(person_id, interest_id));

ALTER TABLE person_interest
ADD FOREIGN KEY(interest_id)
REFERENCES interest(interest_id);

ALTER TABLE person_interest
ADD FOREIGN KEY(person_id)
REFERENCES person(person_id);

-- Will skip person number 4 and 6
INSERT INTO person_interest VALUES(1, 1);
INSERT INTO person_interest VALUES(1, 2); 
INSERT INTO person_interest VALUES(1, 3);
INSERT INTO person_interest VALUES(1, 4);
INSERT INTO person_interest VALUES(1, 5);
INSERT INTO person_interest VALUES(1, 6);
INSERT INTO person_interest VALUES(1, 7);
INSERT INTO person_interest VALUES(1, 8);
INSERT INTO person_interest VALUES(1, 9);
INSERT INTO person_interest VALUES(2, 5);
INSERT INTO person_interest VALUES(2, 6);
INSERT INTO person_interest VALUES(2, 1);
INSERT INTO person_interest VALUES(2, 2);
INSERT INTO person_interest VALUES(2, 3);
INSERT INTO person_interest VALUES(3, 1);
INSERT INTO person_interest VALUES(3, 2);
INSERT INTO person_interest VALUES(3, 3);
INSERT INTO person_interest VALUES(3, 5);
INSERT INTO person_interest VALUES(3, 6);
INSERT INTO person_interest VALUES(3, 7);
INSERT INTO person_interest VALUES(8, 4);
INSERT INTO person_interest VALUES(10, 4);
INSERT INTO person_interest VALUES(10, 8);
INSERT INTO person_interest VALUES(10, 9);
INSERT INTO person_interest VALUES(5, 1);
INSERT INTO person_interest VALUES(5, 2);
INSERT INTO person_interest VALUES(5, 3);
INSERT INTO person_interest VALUES(5, 5);
INSERT INTO person_interest VALUES(5, 6);
INSERT INTO person_interest VALUES(7, 7);
INSERT INTO person_interest VALUES(7, 8);
INSERT INTO person_interest VALUES(11, 5);
INSERT INTO person_interest VALUES(11, 6);
INSERT INTO person_interest VALUES(12, 1);
INSERT INTO person_interest VALUES(12, 2);
INSERT INTO person_interest VALUES(12, 3);
INSERT INTO person_interest VALUES(12, 5);
INSERT INTO person_interest VALUES(12, 6);
INSERT INTO person_interest VALUES(12, 7);
INSERT INTO person_interest VALUES(13, 1);
INSERT INTO person_interest VALUES(13, 2);
INSERT INTO person_interest VALUES(13, 3);
INSERT INTO person_interest VALUES(13, 4);
INSERT INTO person_interest VALUES(13, 5);
INSERT INTO person_interest VALUES(13, 6);
INSERT INTO person_interest VALUES(13, 7);
INSERT INTO person_interest VALUES(14, 1);
INSERT INTO person_interest VALUES(14, 2);
INSERT INTO person_interest VALUES(14, 3);
INSERT INTO person_interest VALUES(14, 5);
INSERT INTO person_interest VALUES(14, 6);
INSERT INTO person_interest VALUES(15, 1);
INSERT INTO person_interest VALUES(15, 5);
INSERT INTO person_interest VALUES(15, 6);
INSERT INTO person_interest VALUES(15, 7);

CREATE TABLE activity(activity_id INT NOT NULL,
                        activity_name VARCHAR(60) NOT NULL,
                        activity_description VARCHAR(250),
                        town_id INT,
                        activity_date DATE NOT NULL, -- Format: YYYY-MM-DD
                        -- IMAGE
                        PRIMARY KEY(activity_id));

ALTER TABLE activity
ADD FOREIGN KEY(town_id)
REFERENCES town(town_id);

INSERT INTO activity VALUES(1, 'Teater: "Kristin Lavransdatter"', 
'Forestilling med "Kristing Lavransdatter" på Trøndelag Teater. Oppmøte kl 18:00', 1, '2014-04-09');
INSERT INTO activity VALUES(2, 'Museumsbesøk Trondheim Kunstmuseum', 
'Besøk på Trondheim Kunstmuseum. Oppmøte 15:30.',1, '2014-05-01');
INSERT INTO activity VALUES(3, 'Politisk diskusjon', 'Diskutere det kommende året politisk. 
Det blir hjemme hos Alfhild. Ring 90 69 54 17 for å melde transportbehov', 1, '2014-01-01'); -- Testing for date-filtering
INSERT INTO activity VALUES(4, 'Politisk diskusjon', 'Diskutere utfallet og konsekvensene 
av reservasjonsdebatten. Det blir hjemme hos Hans Hansen Hansensen på Gimse. 
Transport fra Melhus sentrum 17:00.', 2, '2014-05-13'); -- Testing with another town_id
INSERT INTO activity VALUES(5, 'Teater: "De Miserable"', 
'Forestilling med det franske stykket "De Miserable" på Trøndelag Teater. Oppmøte kl 19:00', 1, '2014-05-13');
INSERT INTO activity VALUES(6, 'Fjelltur på Vassfjellet', 
'Ring 90 69 54 17 for påmelding. Elever fra Nille Videregående Skole stiller opp', 1, '2014-07-02');
INSERT INTO activity VALUES(7, 'Skitur på Gråkallen', 
'Ring 90 69 54 17 for påmelding. Elever fra Rimi Videregående Skole stiller opp', 1, '2014-04-02');
INSERT INTO activity VALUES(8, 'Skitur i Flåmarka', 
'Ring 90 69 54 17 for påmelding. Elever fra Jernia Videregående Skole stiller opp', 1, '2014-04-05');
INSERT INTO activity VALUES(9, 'Kveld med Nintendo', 
'Torbjørn Langland arrangerer spillkveld for de med interesse for Nintendo. Adressen er Moholt Alle 03 - 32 H0302.', 
1, '2014-07-01');
INSERT INTO activity VALUES(10, 'Vaffelfest', 'Vaffelfest på Trondheim Rådhus. Dørene åpner 17:30', 1, '2014-06-06');
INSERT INTO activity VALUES(11, 'Kino: "Hobbiten 3"', 'Kinoforestilling på Nova med Hobbiten 3. Oppmøte 17:30.', 1, '2014-05-21');
INSERT INTO activity VALUES(12, 'Vaffel og sang', 'Trine Katrine Larsen arrangerer sangkveld med vaffel.
Stedet er Moholtsalen, adressen er Moholtstien 13. Tid er kl 18:00', 1, '2014-04-17');
INSERT INTO activity VALUES(13, 'Foo', NULL, NULL, '2014-06-01'); -- Testing with no connected persons or interests
INSERT INTO activity VALUES(14, 'Hemmelig møte for de hemmelige', NULL, NULL, '2014-06-01'); -- Testing NULL-values in general

CREATE TABLE activity_interest(activity_id INT NOT NULL,
                                interest_id INT NOT NULL,
                                PRIMARY KEY(activity_id, interest_id));

ALTER TABLE activity_interest
ADD FOREIGN KEY(activity_id)
REFERENCES activity(activity_id);

ALTER TABLE activity_interest
ADD FOREIGN KEY(interest_id)
REFERENCES interest(interest_id);

INSERT INTO activity_interest VALUES (1, 1);
INSERT INTO activity_interest VALUES (2, 1);
INSERT INTO activity_interest VALUES (3, 7);
INSERT INTO activity_interest VALUES (4, 7);
INSERT INTO activity_interest VALUES (5, 1);
INSERT INTO activity_interest VALUES (6, 2);
INSERT INTO activity_interest VALUES (7, 2);
INSERT INTO activity_interest VALUES (7, 3);
INSERT INTO activity_interest VALUES (8, 2);
INSERT INTO activity_interest VALUES (8, 3);
INSERT INTO activity_interest VALUES (9, 4);
INSERT INTO activity_interest VALUES (10, 5);
INSERT INTO activity_interest VALUES (11, 1);
INSERT INTO activity_interest VALUES (12, 5);
INSERT INTO activity_interest VALUES (12, 6);

CREATE TABLE activity_person(activity_id INT NOT NULL,
                                person_id INT NOT NULL,
                                PRIMARY KEY(activity_id, person_id));

ALTER TABLE activity_person
ADD FOREIGN KEY(activity_id)
REFERENCES activity(activity_id);

ALTER TABLE activity_person
ADD FOREIGN KEY(person_id)
REFERENCES person(person_id);

INSERT INTO activity_person VALUES (1, 1);
INSERT INTO activity_person VALUES (1, 2);
INSERT INTO activity_person VALUES (1, 3);
INSERT INTO activity_person VALUES (1, 4);
INSERT INTO activity_person VALUES (1, 5);
INSERT INTO activity_person VALUES (1, 6);
INSERT INTO activity_person VALUES (1, 7);
INSERT INTO activity_person VALUES (1, 11);
INSERT INTO activity_person VALUES (1, 12);
INSERT INTO activity_person VALUES (1, 13);
INSERT INTO activity_person VALUES (1, 14);
INSERT INTO activity_person VALUES (1, 15);
INSERT INTO activity_person VALUES (2, 2);
INSERT INTO activity_person VALUES (2, 3);
INSERT INTO activity_person VALUES (2, 4);
INSERT INTO activity_person VALUES (2, 12);
INSERT INTO activity_person VALUES (2, 13);
INSERT INTO activity_person VALUES (2, 15);
INSERT INTO activity_person VALUES (3, 12);
INSERT INTO activity_person VALUES (3, 13);
INSERT INTO activity_person VALUES (3, 15);
INSERT INTO activity_person VALUES (4, 12);
INSERT INTO activity_person VALUES (4, 13);
INSERT INTO activity_person VALUES (4, 15);
INSERT INTO activity_person VALUES (5, 1);
INSERT INTO activity_person VALUES (5, 2);
INSERT INTO activity_person VALUES (5, 3);
INSERT INTO activity_person VALUES (5, 4);
INSERT INTO activity_person VALUES (6, 3);
INSERT INTO activity_person VALUES (6, 4);
INSERT INTO activity_person VALUES (7, 3);
INSERT INTO activity_person VALUES (7, 4);
INSERT INTO activity_person VALUES (8, 1);
INSERT INTO activity_person VALUES (8, 2);
INSERT INTO activity_person VALUES (8, 3);
INSERT INTO activity_person VALUES (8, 4);
INSERT INTO activity_person VALUES (9, 1);
INSERT INTO activity_person VALUES (9, 8);
INSERT INTO activity_person VALUES (9, 10);
INSERT INTO activity_person VALUES (9, 13); --TODO: Sett inn testdata for aktiviteter 10-13?

-- INSERT INTO person VALUES(1, 'Torbjørn', 'Langland', 26, 'Moholt Allé 03-32 H0302', 7050);
-- INSERT INTO person VALUES(2, 'Tove', 'Johansen', 61, 'Moholt Allé 555-555-555', 7050);
-- INSERT INTO person VALUES(3, 'Bjørn', 'Johansen', 63, 'Birkenveien', 7015);
-- INSERT INTO person VALUES(4, 'Trine Katrine', 'Larsen', 72, 'Moholt Alle 03-32 H0302', 7052);
-- INSERT INTO person VALUES(5, 'Ludvig', 'Hansen', 56, 'Lersveien', 7234);
-- INSERT INTO person VALUES(6, 'Stine Kristine', 'Stinesen', 69, 'Røsslyngveien 7', 7227);
-- INSERT INTO person VALUES(7, 'Hans Hansen', 'Hansensen', 77, 'Måssåveien 15', 7227);
-- INSERT INTO person VALUES(8, 'Anonym', 'Anonymious', NULL, NULL, NULL);         -- For testing med null-verdier
-- INSERT INTO person VALUES(9, 'Ingkongnito', 'Anonymious', NULL, NULL, 7050);    -- For testing med null-verdier
-- INSERT INTO person VALUES(10, 'U-Known', 'Anonymious', 38, NULL, NULL);         -- For testing med null-verdier
-- INSERT INTO person VALUES(11, 'Tove', 'Larsen', 65, 'Klæbuveien 5', 7145);
-- INSERT INTO person VALUES(12, 'Sigrid', 'Svansen', 66, 'Moholtveien 5', 7052);
-- INSERT INTO person VALUES(13, 'Lise', 'Karslen', 66, 'Moholtveien 6', 7052);
-- INSERT INTO person VALUES(14, 'Lars', 'Larsen', 66, 'Moholtveien 7', 7052);
-- INSERT INTO person VALUES(15, 'Jens', 'Stoltenjern', 66, 'Moholtveien 8', 7052);
-- INSERT INTO person VALUES(16, 'User', 'User', NULL, NULL, NULL); -- Chosen test user for the Java application