INSERT INTO authorities (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (id, name) VALUES (1, 'ROLE_USER');

INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'markoMarkovic@maildrop.cc', true, 'Marko', 'Markovic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'MarkoMarkovic12');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'tamaraSimic@maildrop.cc', true, 'Tamara', 'Simic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'tamaraSimic6');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'miodrag.lakic@maildrop.cc', true, 'Miodrag', 'Lakic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'Miodrag1995');

INSERT INTO user_table_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_table_authority (user_id, authority_id) VALUES (2, 1);

INSERT INTO category (name) VALUES ('Institucija');
INSERT INTO category (name) VALUES ('Kulturno dobro');

INSERT INTO category_type (name,category_id) VALUES ('Muzej', 1);
INSERT INTO category_type (name,category_id) VALUES ('Galerija', 1);
INSERT INTO category_type (name,category_id) VALUES ('Spomenik', 2);
INSERT INTO category_type (name,category_id) VALUES ('Znamenitost', 2);
