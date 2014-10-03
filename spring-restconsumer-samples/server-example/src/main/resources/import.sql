--
-- Sample dataset containing a number of Hotels in various Cities across the world.
--

-- =================================================================================================
-- AUSTRALIA

-- Brisbane
insert into city(country, name, state, map) values ('Australia', 'Brisbane', 'Queensland', '-27.470933, 153.023502')
insert into stadium(city_id, name) values (1, 'Suncorp Stadium')

-- Melbourne
insert into city(country, name, state, map) values ('Australia', 'Melbourne', 'Victoria', '-37.813187, 144.96298')
insert into stadium(city_id, name) values (2, 'Etihad Stadium')
insert into stadium(city_id, name) values (2, 'Docklands Stadium')

-- Sydney
insert into city(country, name, state, map) values ('Australia', 'Sydney', 'New South Wales', '-33.868901, 151.207091')
insert into stadium(city_id, name) values (3, 'Allianz Stadium')

-- =================================================================================================
-- CANADA

-- Montreal
insert into city(country, name, state, map) values ('Canada', 'Montreal', 'Quebec', '45.508889, -73.554167')
insert into stadium(city_id, name) values (4, 'Olympic Stadium')

-- =================================================================================================
-- ISRAEL

-- Tel Aviv
insert into city(country, name, state, map) values ('Israel', 'Tel Aviv', '', '32.066157, 34.777821')
insert into stadium(city_id, name) values (5, 'Bloomfield Stadium')


-- =================================================================================================
-- JAPAN

-- Tokyo
insert into city(country, name, state, map) values ('Japan', 'Tokyo', '', '35.689488, 139.691706')
insert into stadium(city_id, name) values (6, 'National Olympic Stadium')


-- =================================================================================================
-- SPAIN

-- Barcelona
insert into city(country, name, state, map) values ('Spain', 'Barcelona', 'Catalunya', '41.387917, 2.169919')
insert into stadium(city_id, name) values (7, 'Camp Nou')

-- =================================================================================================
-- SWITZERLAND

-- Neuchatel
insert into city(country, name, state, map) values ('Switzerland', 'Neuchatel', '', '46.992979, 6.931933')
insert into stadium(city_id, name) values (8, 'BSA Stadium Neuchâtel')


-- =================================================================================================
-- UNITED KINGDOM

-- Bath
insert into city(country, name, state, map) values ('UK', 'Bath', 'Somerset', '51.381428, -2.357454')
insert into stadium(city_id, name) values (9, 'The Recreation Ground')

-- London
insert into city(country, name, state, map) values ('UK', 'London', '', '51.500152, -0.126236')
insert into stadium(city_id, name) values (10, 'Wembley')
insert into stadium(city_id, name) values (10, 'Twickenham')
insert into stadium(city_id, name) values (10, 'Old Stratford')

-- Southampton
insert into city(country, name, state, map) values ('UK', 'Southampton', 'Hampshire', '50.902571, -1.397238')
insert into stadium(city_id, name) values (11, 'St Mary\'s Stadium')


-- =================================================================================================
-- USA

-- Atlanta
insert into city(country, name, state, map) values ('USA', 'Atlanta', 'GA', '33.748995, -84.387982')
insert into stadium(city_id, name) values (12, 'Clark  Atlanta Stadium')

-- Chicago
insert into city(country, name, state, map) values ('USA', 'Chicago', 'IL', '41.878114, -87.629798')
insert into stadium(city_id, name) values (13, 'Chicago Stadium')

-- Eau Claire
insert into city(country, name, state, map) values ('USA', 'Eau Claire', 'WI', '44.811349, -91.498494')

-- Hollywood
insert into city(country, name, state, map) values ('USA', 'Hollywood', 'FL', '26.011201, -80.14949')

-- Miami
insert into city(country, name, state, map) values ('USA', 'Miami', 'FL', '25.788969, -80.226439')

-- Melbourne
insert into city(country, name, state, map) values ('USA', 'Melbourne', 'FL', '28.083627, -80.608109')

-- New York
insert into city(country, name, state, map) values ('USA', 'New York', 'NY', '40.714353, -74.005973')

-- Palm Bay
insert into city(country, name, state, map) values ('USA', 'Palm Bay', 'FL', '28.034462, -80.588665')

-- San Francisco
insert into city(country, name, state, map) values ('USA', 'San Francisco', 'CA', '37.77493, -122.419415')

-- Washington
insert into city(country, name, state, map) values ('USA', 'Washington', 'DC', '38.895112, -77.036366')
