DROP TABLE IF EXISTS messages;

CREATE TABLE IF NOT EXISTS messages(
    id SERIAL PRIMARY KEY,
    name TEXT,
    message TEXT,
    data TEXT);

 INSERT INTO messages (name, message, data)
    VALUES ('kuba', 'przykładowy wpis 1', '6/22/2013 20:20:01'),
           ('ala', 'przykładowy wpis 2', '2/12/2013 20:20:01'),
           ('lukasz', 'przykładowy wpis 3', '6/22/2013 20:20:01'),
           ('marcin', 'przykładowy wpis 4', '6/22/2013 10:20:01'),
           ('wojtek', 'przykładowy wpis 5', '6/22/2013 20:20:01'),
           ('ola', 'przykładowy wpis 6', '6/22/2013 20:20:01'),
           ('kasia', 'przykładowy wpis 7', '6/22/2013 20:20:01');
