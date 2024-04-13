-- Participants Table
CREATE TABLE Participant (
    id SERIAL PRIMARY KEY,
    user_id INT REFERENCES "user"(id),
    project_id INT REFERENCES Project(id)
);


INSERT INTO Participant (user_id, project_id) VALUES
    (4, 4), -- User 4 participating in Project 4 (FinPlanner)
    (7, 3), -- User 7 participating in Project 3 (HealthTrack)
    (6, 7), -- User 6 participating in Project 7 (CryptoWatch)
    (9, 5), -- User 9 participating in Project 5 (Artificial Tutor)
    (5, 10), -- User 5 participating in Project 10 (GreenTech Innovations)
    (2, 9), -- User 2 participating in Project 9 (EduConnect)
    (10, 1); -- User 10 participating in Project 1 (EcoFinder)
