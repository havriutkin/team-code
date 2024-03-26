CREATE TABLE "user" (
  user_id SERIAL PRIMARY KEY,
  name VARCHAR(250),
  email VARCHAR(250) UNIQUE NOT NULL,
  password VARCHAR(250) NOT NULL,
  bio TEXT,
  github_link VARCHAR(250),
  experience_id INT,
  FOREIGN KEY (experience_id) REFERENCES experience(experience_id)
);

ALTER TABLE "user" ADD COLUMN created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


INSERT INTO "user" (name, email, password, bio, github_link, experience_id) VALUES
  ('Alex Johnson', 'alex.johnson@example.com', 'securepassword', 'Full-stack developer with a keen interest in microservices.', 'https://github.com/alexjohnson', 3),
  ('Mia Wong', 'mia.wong@example.com', 'password123', 'Data scientist passionate about AI and ML.', 'https://github.com/miawong', 2),
  ('Omar Ahmad', 'omar.ahmad@example.com', 'mypassword', 'Mobile app developer specializing in Android applications.', 'https://github.com/omarahmad', 1),
  ('Elena Gilbert', 'elena.gilbert@example.com', 'elena123', 'Web developer with a passion for front-end technologies.', 'https://github.com/elenagilbert', 2),
  ('Nathan Choi', 'nathan.choi@example.com', 'choipass', 'DevOps engineer focusing on CI/CD and automation.', 'https://github.com/nathanchoi', 3),
  ('Isabella Martinez', 'isabella.martinez@example.com', 'isabellapassword', 'Database administrator with a love for SQL and data modeling.', 'https://github.com/isabellamartinez', 1),
  ('Jayden Smith', 'jayden.smith@example.com', 'jaydensmith', 'Cloud computing expert focused on AWS solutions.', 'https://github.com/jaydensmith', 3),
  ('Sophia Liu', 'sophia.liu@example.com', 'sophialiu', 'Cybersecurity specialist with a focus on network security.', 'https://github.com/sophialiu', 2),
  ('Liam Brown', 'liam.brown@example.com', 'liambrown', 'Software engineer with extensive experience in backend systems.', 'https://github.com/liambrown', 3),
  ('Zoe Kim', 'zoe.kim@example.com', 'zoekim', 'UI/UX designer passionate about creating intuitive user interfaces.', 'https://github.com/zoekim', 1),
  ('Ethan Davis', 'ethan.davis@example.com', 'ethandavis', 'Full-stack developer and open source contributor.', 'https://github.com/ethandavis', 2),
  ('Ava Patel', 'ava.patel@example.com', 'avapatel', 'Project manager with a focus on agile methodologies and team collaboration.', 'https://github.com/avapatel', 1),
  ('Oliver Wilson', 'oliver.wilson@example.com', 'oliverwilson', 'Blockchain developer working on decentralized applications.', 'https://github.com/oliverwilson', 3),
  ('Charlotte Garcia', 'charlotte.garcia@example.com', 'charlottegarcia', 'Machine learning engineer with a focus on neural networks.', 'https://github.com/charlottegarcia', 2),
  ('Noah Lee', 'noah.lee@example.com', 'noahlee', 'Video game developer with a passion for game design and graphics.', 'https://github.com/noahlee', 1);
