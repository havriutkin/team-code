-- Skill Table
CREATE TABLE Skill (
    id SERIAL PRIMARY KEY,
    skill_name VARCHAR(250) NOT NULL UNIQUE
);

-- Project Skill Table
CREATE TABLE Project_Skill (
    project_skill_id SERIAL PRIMARY KEY,
    fk_project INT REFERENCES Project(id),
    fk_skill INT REFERENCES Skill(id)
);

ALTER TABLE Project_Skill ADD CONSTRAINT fk_project_fk_skill_unique UNIQUE (fk_project, fk_skill);
ALTER TABLE Project_Skill ADD CONSTRAINT fk_project_fk_skill_cascade FOREIGN KEY (fk_project) REFERENCES Project(id) ON DELETE CASCADE;
ALTER TABLE Project_Skill ADD CONSTRAINT fk_skill_fk_project_cascade FOREIGN KEY (fk_skill) REFERENCES Skill(id) ON DELETE CASCADE;



-- User Skill Table
CREATE TABLE User_Skill (
    id SERIAL PRIMARY KEY,
    fk_user INT REFERENCES "user"(id),
    fk_skill INT REFERENCES Skill(id)
);

ALTER TABLE User_Skill ADD CONSTRAINT fk_user_fk_skill_cascade FOREIGN KEY (fk_user) REFERENCES "user"(id) ON DELETE CASCADE;
ALTER TABLE User_Skill ADD CONSTRAINT fk_skill_fk_user_cascade FOREIGN KEY (fk_skill) REFERENCES Skill(id) ON DELETE CASCADE;


ALTER TABLE User_Skill ADD CONSTRAINT fk_user_fk_skill_unique UNIQUE (fk_user, fk_skill);

INSERT INTO Skill (skill_name) VALUES
    ('JavaScript'),
    ('Python'),
    ('Java'),
    ('C#'),
    ('PHP'),
    ('Ruby'),
    ('Swift'),
    ('Kotlin'),
    ('Go'),
    ('Rust'),
    ('TypeScript'),
    ('SQL'),
    ('NoSQL'),
    ('HTML'),
    ('CSS'),
    ('React'),
    ('Angular'),
    ('Vue.js'),
    ('Node.js'),
    ('Django'),
    ('Flask'),
    ('Spring Boot'),
    ('ASP.NET'),
    ('Ruby on Rails'),
    ('Laravel'),
    ('Kubernetes'),
    ('Docker'),
    ('AWS'),
    ('Azure'),
    ('Google Cloud Platform'),
    ('Git'),
    ('Machine Learning'),
    ('Deep Learning'),
    ('Data Analysis'),
    ('Data Visualization'),
    ('Blockchain'),
    ('Cybersecurity'),
    ('IoT (Internet of Things)'),
    ('Mobile Development'),
    ('Web Development'),
    ('Agile Methodologies'),
    ('Scrum'),
    ('DevOps'),
    ('UI/UX Design'),
    ('Graphic Design'),
    ('SEO Optimization'),
    ('Project Management'),
    ('Digital Marketing');

INSERT INTO skill (skill_name) VALUES
    ('Salesforce');

INSERT INTO skill (skill_name) VALUES
    ('SAP');

-- Project 1
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (1, 1),
    (1, 2),
    (1, 3);

-- Project 2
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (2, 4),
    (2, 5),
    (2, 6),
    (2, 7);

-- Project 3
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (3, 8),
    (3, 9),
    (3, 10),
    (3, 11),
    (3, 12);

-- Project 4
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (4, 13),
    (4, 14),
    (4, 15),
    (4, 16);

-- Project 5
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (5, 17),
    (5, 18),
    (5, 19),
    (5, 20),
    (5, 21);

-- Project 6
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (6, 22),
    (6, 23),
    (6, 24),
    (6, 25);

-- Project 7
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (7, 26),
    (7, 27),
    (7, 28),
    (7, 29),
    (7, 30);

-- Project 8
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (8, 31),
    (8, 32),
    (8, 33),
    (8, 34),
    (8, 35);

-- Project 9
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (9, 36),
    (9, 37),
    (9, 38),
    (9, 39);

-- Project 10
INSERT INTO Project_Skill (fk_project, fk_skill) VALUES
    (10, 40),
    (10, 41),
    (10, 42),
    (10, 43),
    (10, 44);


-- User 1
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (1, 1),
    (1, 11),
    (1, 21);

-- User 2
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (2, 2),
    (2, 12),
    (2, 22),
    (2, 32);

-- User 3
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (3, 3),
    (3, 13),
    (3, 23),
    (3, 33),
    (3, 43);

-- User 4
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (4, 4),
    (4, 14),
    (4, 24),
    (4, 34);

-- User 5
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (5, 5),
    (5, 15),
    (5, 25),
    (5, 35),
    (5, 45);

-- User 6
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (6, 6),
    (6, 16),
    (6, 26),
    (6, 36);

-- User 7
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (7, 7),
    (7, 17),
    (7, 27),
    (7, 37),
    (7, 47);

-- User 8
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (8, 8),
    (8, 18),
    (8, 28),
    (8, 38);

-- User 9
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (9, 9),
    (9, 19),
    (9, 29),
    (9, 39),
    (9, 49);

-- User 10
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (10, 10),
    (10, 20),
    (10, 30),
    (10, 40),
    (10, 50);

-- User 11
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (11, 6),
    (11, 16),
    (11, 26);

-- User 12
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (12, 7),
    (12, 17),
    (12, 27),
    (12, 37);

-- User 13
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (13, 8),
    (13, 18),
    (13, 38),
    (13, 48);

-- User 14
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (14, 9),
    (14, 29),
    (14, 39),
    (14, 49);

-- User 15
INSERT INTO User_Skill (fk_user, fk_skill) VALUES
    (15, 5),
    (15, 15),
    (15, 25),
    (15, 35);
