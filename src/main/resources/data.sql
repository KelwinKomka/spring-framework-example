INSERT INTO user (id, name, login, password) VALUES (1, 'Administrator', 'admin', '');
INSERT INTO user (id, name, login, password) VALUES (2, 'Manager 1', 'manager', '');

INSERT INTO authority (id, role) VALUES (1, 'ROLE_ADMIN');
INSERT INTO authority (id, role) VALUES (2, 'ROLE_MANAGER');

INSERT INTO user_authorities (user_id, authorities_id) VALUES (1, 1);
INSERT INTO user_authorities (user_id, authorities_id) VALUES (2, 2);

INSERT INTO task (id, creation_date, description, priority) VALUES (1, '2023-03-18', 'Task 1', 0);