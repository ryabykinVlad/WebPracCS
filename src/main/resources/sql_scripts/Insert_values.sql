INSERT INTO Clients(full_name, phone, email, preferred_city) VALUES
	('Гришин Михаил Дмитриевич', '89846241738', 'gmd@mail.ru', 'Москва'),
	('Смирнова Василиса Денисовна', '89409655768', 'svden@yandex.ru', 'Воронеж'),
	('Абрамова Анастасия Романовна', '89131019879', NULL, NULL),
	('Кузнецова Алина Александровна', '89199476661', 'kuzalinalex@rambler.ru', 'Новосибирск'),
	('Богданова Вероника Евгеньевна', '89799794197', NULL, 'Москва')
;

INSERT INTO Airports(iata_code, airport_name, airport_country, airport_city) VALUES
	('VKO', 'Международный аэропорт Внуково', 'Россия', 'Москва'),
	('OVB', 'Международный аэропорт Толмачёво', 'Россия', 'Новосибирск'),
	('LED', 'Международный аэропорт Пулково', 'Россия', 'Санкт-Петербург'),
	('DME', 'Международный аэропорт Домодедово', 'Россия', 'Москва'),
	('IST', 'Международный аэропорт Стамбул', 'Турция', 'Стамбул')
;

INSERT INTO Airlines(airline_name, registration_country, airline_phone, airline_email, airline_website) VALUES
	('S7 Airlines', 'Россия', '84957830707', NULL, 'www.s7.ru'),
	('Аэрофлот', 'Россия', '88004445555', NULL, 'www.aeroflot.ru'),
	('Победа', 'Россия', '88095054777', '', 'www.pobeda.aero'),
	('Turkish Airlines', 'Турция', NULL, NULL, 'www.turkishairlines.com'),
	('Emirates', 'ОАЭ', NULL, NULL, 'www.emirates.com')
;

INSERT INTO Flights(airline_id, departure_airport_id, arrival_airport_id, departure_time, arrival_time,
				   current_price, available_seats, total_seats) VALUES
	(1, 4, 2, '2023-02-28 11:15', '2023-02-28 15:05', 8200, 10, 200),
	(3, 3, 1, '2023-03-15 15:35', '2023-03-15 17:20', 4500, 3, 150),
	(4, 5, 4, '2023-04-14 10:00', '2023-04-14 16:30', 35000, 7, 250),
	(1, 2, 4, '2023-02-28 22:10', '2023-03-01 02:20', 9000, 5, 225),
	(2, 5, 3, '2023-03-03 14:45', '2023-03-03 21:35', 30000, 0, 175)
;

INSERT INTO TICKETS(client_id, flight_id, purchase_price, purchase_time) VALUES
	(1, 3, 33500, '2023-02-02 16:04'),
	(2, 2, 4500, '2023-02-26 15:12'),
	(3, 5, 35000, '2022-12-27 11:03'),
	(4, 4, 9000, '2023-01-12 12:17'),
	(5, 1, 8000, '2023-02-03 14:48')
;