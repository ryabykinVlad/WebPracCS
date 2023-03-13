DROP TABLE IF EXISTS Clients CASCADE;
DROP TABLE IF EXISTS Tickets CASCADE;
DROP TABLE IF EXISTS Flights CASCADE;
DROP TABLE IF EXISTS Airports CASCADE;
DROP TABLE IF EXISTS Airlines CASCADE;


CREATE TABLE IF NOT EXISTS Clients(
    client_id SERIAL PRIMARY KEY,
    full_name TEXT NOT NULL,
	phone TEXT NOT NULL,
    email TEXT,
    preferred_city TEXT
);

CREATE TABLE IF NOT EXISTS Airports(
    airport_id SERIAL PRIMARY KEY,
	iata_code TEXT NOT NULL,
    airport_name TEXT NOT NULL,
    airport_country TEXT NOT NULL,
	airport_city TEXT NOT NULL
);


CREATE TABLE IF NOT EXISTS Airlines(
    airline_id SERIAL PRIMARY KEY,
    airline_name TEXT NOT NULL,
	registration_country TEXT NOT NULL,
    airline_phone TEXT,
    airline_email TEXT,
	airline_website TEXT
);

CREATE TABLE IF NOT EXISTS Flights(
    flight_id SERIAL PRIMARY KEY,
    airline_id INTEGER REFERENCES Airlines ON DELETE CASCADE NOT NULL,
    departure_airport_id INTEGER REFERENCES Airports ON DELETE CASCADE NOT NULL,
    arrival_airport_id INTEGER REFERENCES Airports ON DELETE CASCADE NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    current_price NUMERIC NOT NULL CHECK(current_price >= 0),
	total_seats INTEGER NOT NULL CHECK(total_seats > 0),
	available_seats INTEGER NOT NULL CHECK(available_seats >= 0),
	CONSTRAINT time_arrival_ge_departure CHECK(arrival_time >= departure_time)
);

CREATE TABLE IF NOT EXISTS Tickets(
    ticket_id SERIAL PRIMARY KEY,
	client_id INTEGER REFERENCES Clients ON DELETE CASCADE NOT NULL,
    flight_id INTEGER REFERENCES Flights ON DELETE CASCADE NOT NULL,
    purchase_price NUMERIC NOT NULL CHECK(purchase_price >= 0),
	purchase_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP CHECK(purchase_time <= CURRENT_TIMESTAMP)	
);








