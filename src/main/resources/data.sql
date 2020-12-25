INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (1,'markoMarkovic@maildrop.cc', true, 'Marko', 'Markovic','2017-10-01 21:58:58', '$2y$12$O8PNa04LGFUkLN/tVXaDV..laj94q8jKSspKI4dzOM3cVUqy2n0vi ', 'MarkoMarkovic12');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (2,'tamaraSimic@maildrop.cc', true, 'Tamara', 'Simic','2017-10-01 21:58:58', '$2y$12$O8PNa04LGFUkLN/tVXaDV..laj94q8jKSspKI4dzOM3cVUqy2n0vi ', 'tamaraSimic6');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (3,'miodrag.lakic@maildrop.cc', true, 'Miodrag', 'Lakic','2017-10-01 21:58:58', '$2y$12$O8PNa04LGFUkLN/tVXaDV..laj94q8jKSspKI4dzOM3cVUqy2n0vi', 'Miodrag1995');

INSERT INTO category (name) VALUES ('Institucija');
INSERT INTO category (name) VALUES ('Kulturno dobro');

INSERT INTO category_type (name,category_id) VALUES ('Muzej', 1);
INSERT INTO category_type (name,category_id) VALUES ('Galerija', 1);
INSERT INTO category_type (name,category_id) VALUES ('Spomenik', 2);
INSERT INTO category_type (name,category_id) VALUES ('Znamenitost', 2);
