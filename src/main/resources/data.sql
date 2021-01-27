INSERT INTO authorities (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO authorities (id, name) VALUES (1, 'ROLE_USER');

INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'markoMarkovic@maildrop.cc', true, 'Marko', 'Markovic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'MarkoMarkovic12');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'tamaraSimic@maildrop.cc', true, 'Tamara', 'Simic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'tamaraSimic6');
INSERT INTO user_table  (id, email, activated_account, first_name, last_name, last_password_reset_date, password, username) VALUES (nextval('user_table_id_seq'),'miodrag.lakic@maildrop.cc', true, 'Miodrag', 'Lakic','2017-10-01 21:58:58', '$2y$04$rVVOd4m6GqQexoN6CpiXi.vrXPRqN/sK1jYNBqnvgmGx0pYHRnwbO', 'Miodrag1995');

INSERT INTO user_table_authority (user_id, authority_id) VALUES (1, 2);
INSERT INTO user_table_authority (user_id, authority_id) VALUES (2, 1);

INSERT INTO category (name) VALUES ('Institucija');
INSERT INTO category (name) VALUES ('Kulturno dobro');
INSERT INTO category (name) VALUES ('Sportski objekat');

INSERT INTO category_type (name,category_id) VALUES ('Muzej', 1);
INSERT INTO category_type (name,category_id) VALUES ('Galerija', 1);
INSERT INTO category_type (name,category_id) VALUES ('Pozoriste', 1);
INSERT INTO category_type (name,category_id) VALUES ('Spomenik', 2);
INSERT INTO category_type (name,category_id) VALUES ('Znamenitost', 2);
INSERT INTO category_type (name,category_id) VALUES ('Stadion', 3);

INSERT INTO addresses (id, street_name, street_number, city, country, latitude, longitude) VALUES (nextval('addresses_id_seq'), 'Trg slobode', 1, 'Novi Sad', 'Srbija', 45.2550152701617, 19.844963606446985);
INSERT INTO addresses (id, street_name, street_number, city, country, latitude, longitude) VALUES (nextval('addresses_id_seq'), 'Dunavska', 35, 'Novi Sad', 'Srbija', 45.25645779683133, 19.85155999660492);
INSERT INTO addresses (id, street_name, street_number, city, country, latitude, longitude) VALUES (nextval('addresses_id_seq'), 'Pozorisni Trg', 1, 'Novi Sad', 'Srbija', 45.25524940001801, 19.84311103820801);
INSERT INTO addresses (id, street_name, street_number, city, country, latitude, longitude) VALUES (nextval('addresses_id_seq'), 'Ljutice Bogdana', 1, 'Beograd', 'Srbija', 44.78312200875624, 20.46483635902405);

INSERT INTO offers (id, name, description, address_id, image_path, rating, type_id) VALUES (nextval('offers_id_seq'), 'Spomenik Svetozaru Mileticu', 'Spomenik Svetozaru Miletiću (1826-1901) najznačajnijem vođi Srpskog naroda u Habzburškoj monarhiji, poslaniku, advokatu i gradonačelniku Novog Sada, od bronze, visine 500cm, rad vajara Ivana Meštrovića, postavljen je prvi put 1939. ispred Gradske kuće na Trgu slobode u Novom Sadu.', 1, '', 0, 4);
INSERT INTO offers (id, name, description, address_id, image_path, rating, type_id) VALUES (nextval('offers_id_seq'), 'Muzej Vojvodine', 'Muzej Vojvodine se nalazi u nekadašnjoj zgradi suda. Izgrađena je 1900. godine u stilu klasicizma prema projektu arhitekte Đule Vagnera. Muzej je najveći i najznačajniji u Vojvodini.', 2, '', 0, 1);
INSERT INTO offers (id, name, description, address_id, image_path, rating, type_id) VALUES (nextval('offers_id_seq'), 'Srpsko narodno pozorište', 'Srpsko narodno pozorište u Novom Sadu osnovano je sredinom 1861. godine i ono je prvo pozorište na jezicima Južnoslovenskih naroda', 3, '', 0, 1);
INSERT INTO offers (id, name, description, address_id, image_path, rating, type_id) VALUES (nextval('offers_id_seq'), 'Stadion Rajko Mitic', 'Stadion Rajko Mitić (ranije Stadion Crvena zvezda; popularno poznat i kao Marakana) je fudbalski i atletski stadion u Beogradu na kojem igra FK Crvena zvezda. Na njemu zvanične utakmice igra i fudbalska reprezentacija Srbije.', 4, '', 0, 6);

INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 1', 2, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 2', 3, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 3', 3, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 4', 2, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 5', 3, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 6', 2, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 7', 2, 1, '2020-12-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Ovo je komentar 8', 3, 1, '2020-12-14');

INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Najbolji stadion ikada', 3, 4, '2021-01-14');
INSERT INTO comments (id, text, author_id, offer_id, created_at) VALUES (nextval('comments_id_seq'), 'Mnogo lep stadion', 2, 4, '2021-01-14');