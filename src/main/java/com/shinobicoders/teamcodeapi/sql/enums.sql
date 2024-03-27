-- Experience Table
CREATE TABLE Experience (
    id SERIAL PRIMARY KEY,
    level VARCHAR(50) NOT NULL UNIQUE
);

-- Project Level Table
CREATE TABLE Project_Level (
   id SERIAL PRIMARY KEY,
   level VARCHAR(50) NOT NULL UNIQUE
);

-- Request Status Table
CREATE TABLE Request_Status (
    id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL UNIQUE
);



INSERT INTO Experience (id, level) VALUES (0, 'Beginner');
INSERT INTO Experience (id, level) VALUES (1, 'Intermediate');
INSERT INTO Experience (id, level) VALUES (2, 'Advanced');

INSERT INTO Project_Level (id, level) VALUES (0, 'Easy');
INSERT INTO Project_Level (id, level) VALUES (1, 'Medium');
INSERT INTO Project_Level (id, level) VALUES (2, 'Hard');

INSERT INTO Request_Status (id, status) VALUES (0, 'Pending');
INSERT INTO Request_Status (id, status) VALUES (1, 'Approved');
INSERT INTO Request_Status (id, status) VALUES (2, 'Rejected');