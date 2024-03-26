-- Project Table
CREATE TABLE Project (
    id SERIAL PRIMARY KEY,
    project_name VARCHAR(250) NOT NULL,
    description TEXT,
    status BOOLEAN,
    participants_number INT,
    max_participants_number INT,
    start_date DATE,
    git_repository VARCHAR(250),
    project_level_id INT REFERENCES Project_Level(id),
    owner_id INT REFERENCES "user" (id)
);



INSERT INTO Project (project_name, description, status, participants_number, max_participants_number, start_date, git_repository, project_level_id, owner_id) VALUES
    ('EcoFinder', 'A platform for identifying and categorizing eco-friendly products.', TRUE, 3, 10, '2024-01-15', 'https://github.com/ecofinder', 0, 1),
    ('CodeCollab', 'Collaboration tool for developers to work on projects in real-time.', TRUE, 5, 20, '2024-02-01', 'https://github.com/codecollab', 1, 2),
    ('HealthTrack', 'App for tracking dietary and fitness activities to promote a healthy lifestyle.', FALSE, 2, 5, '2024-03-10', 'https://github.com/healthtrack', 0, 3),
    ('FinPlanner', 'Financial planning application for budgeting, investing, and wealth management.', TRUE, 8, 10, '2024-04-05', 'https://github.com/finplanner', 2, 4),
    ('Artificial Tutor', 'An AI-driven platform for personalized learning experiences.', TRUE, 6, 15, '2024-05-20', 'https://github.com/artificialtutor', 1, 5),
    ('SafeRoutes', 'Application providing safe navigation routes based on real-time crime data.', TRUE, 4, 8, '2024-06-15', 'https://github.com/saferoutes', 0, 6),
    ('CryptoWatch', 'Cryptocurrency tracking and analysis tool for traders.', FALSE, 1, 5, '2024-07-01', 'https://github.com/cryptowatch', 2, 7),
    ('FreelanceHub', 'Marketplace connecting freelancers with projects across various industries.', TRUE, 7, 20, '2024-08-05', 'https://github.com/freelancehub', 1, 8),
    ('EduConnect', 'Platform facilitating collaboration between students and educators.', TRUE, 9, 15, '2024-09-10', 'https://github.com/educonnect', 0, 9),
    ('GreenTech Innovations', 'Initiative for promoting sustainable technologies in energy production.', TRUE, 5, 10, '2024-10-15', 'https://github.com/greentechinnovations', 2, 10);








