-- Notification Table
CREATE TABLE Notification (
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    is_viewed BOOLEAN DEFAULT FALSE,
    creation_date DATE,
    user_id INT REFERENCES "user"(id) ON DELETE CASCADE
);


INSERT INTO Notification (message, is_viewed, creation_date, user_id) VALUES
    ('Your request to join Project EcoFinder has been approved. Welcome to the team!', FALSE, '2024-03-02', 10),
    ('Your request to join Project CodeCollab has been rejected. Thank you for your interest.', FALSE, '2024-03-06', 2),
    ('Your request to join Project HealthTrack has been approved. Welcome to the team!', FALSE, '2024-03-11', 7),
    ('Your request to join Project Artificial Tutor has been rejected. Thank you for your interest.', FALSE, '2024-03-16', 9),
    ('Your request to join Project SafeRoutes has been approved. Welcome to the team!', FALSE, '2024-03-21', 6),
    ('Your request to join Project CryptoWatch has been approved. Welcome to the team!', FALSE, '2024-03-26', 5),
    ('Your request to join Project FreelanceHub has been rejected. Thank you for your interest.', FALSE, '2024-03-31', 8),
    ('Your request to join Project EduConnect has been approved. Welcome to the team!', FALSE, '2024-04-01', 2),
    ('Your request to join Project GreenTech Innovations has been approved. Welcome to the team!', FALSE, '2024-04-06', 4),
    ('Your request to join Project FinPlanner has been rejected. Thank you for your interest.', FALSE, '2024-04-11', 3);
