-- Experience Table
CREATE TABLE Experience (
    experience_id SERIAL PRIMARY KEY,
    level VARCHAR(50) NOT NULL
);

-- Project Level Table
CREATE TABLE Project_Level (
   project_level_id SERIAL PRIMARY KEY,
   level VARCHAR(50) NOT NULL
);

-- Request Status Table
CREATE TABLE Request_Status (
    status_id SERIAL PRIMARY KEY,
    status VARCHAR(50) NOT NULL
);

INSERT INTO Experience (level) VALUES ('Beginner');
INSERT INTO Experience (level) VALUES ('Intermediate');
INSERT INTO Experience (level) VALUES ('Advanced');

INSERT INTO Project_Level (level) VALUES ('Easy');
INSERT INTO Project_Level (level) VALUES ('Medium');
INSERT INTO Project_Level (level) VALUES ('Hard');

INSERT INTO Request_Status (status) VALUES ('Pending');
INSERT INTO Request_Status (status) VALUES ('Approved');
INSERT INTO Request_Status (status) VALUES ('Rejected');