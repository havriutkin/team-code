-- Request Table
CREATE TABLE Request (
    request_id SERIAL PRIMARY KEY,
    status_id INT,
    request_date DATE,
    message VARCHAR(250),
    user_id INT,
    project_id INT,
    FOREIGN KEY (status_id) REFERENCES Request_Status(status_id),
    FOREIGN KEY (user_id) REFERENCES "user"(user_id),
    FOREIGN KEY (project_id) REFERENCES Project(project_id)
);

-- Add on delete cascade to user_id and project_id
ALTER TABLE Request ADD FOREIGN KEY (user_id) REFERENCES "user"(user_id) ON DELETE CASCADE;
ALTER TABLE Request ADD FOREIGN KEY (project_id) REFERENCES Project(project_id) ON DELETE CASCADE;

INSERT INTO Request (status_id, request_date, message, user_id, project_id) VALUES
    (3, '2024-03-01', 'Excited to contribute to this eco-friendly project. Can I join?', 10, 1),
    (1, '2024-03-05', 'I have experience in financial apps and would love to help.', 4, 4),
    (2, '2024-03-10', 'Looking to apply my fitness app development experience here.', 7, 3),
    (3, '2024-03-15', 'I am passionate about education and would like to contribute.', 2, 9),
    (1, '2024-03-20', 'I am a blockchain enthusiast keen on developing decentralized apps.', 6, 7),
    (2, '2024-03-25', 'As a cybersecurity expert, I can enhance the security features of your app.', 9, 5),
    (3, '2024-03-30', 'Interested in sustainable technologies and would love to get involved.', 5, 10);

