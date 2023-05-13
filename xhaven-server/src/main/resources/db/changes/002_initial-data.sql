-- liquibase formatted sql

insert into role(id, name) values (1, 'ROLE_USER');
insert into role(id, name) values (2, 'ROLE_MODERATOR');
insert into role(id, name) values (3, 'ROLE_ADMIN');

insert into category(category_id, category_name, parent_id) values (1, 'Vehicles', null);
insert into category(category_id, category_name, parent_id) values (2, 'Real Estate', null);
insert into category(category_id, category_name, parent_id) values (3, 'Electronics', null);
insert into category(category_id, category_name, parent_id) values (4, 'Fashion', null);
insert into category(category_id, category_name, parent_id) values (5, 'Animals', null);
insert into category(category_id, category_name, parent_id) values (6, 'For children', null);
insert into category(category_id, category_name, parent_id) values (7, 'Sports and hobbies', null);
insert into category(category_id, category_name, parent_id) values (8, 'Music', null);
insert into category(category_id, category_name, parent_id) values (9, 'Health', null);
insert into category(category_id, category_name, parent_id) values (10, 'Cars', 1);
insert into category(category_id, category_name, parent_id) values (11, 'Motorcycles', 1);
insert into category(category_id, category_name, parent_id) values (12, 'Trucks', 1);
insert into category(category_id, category_name, parent_id) values (13, 'Vans', 1);
insert into category(category_id, category_name, parent_id) values (14, 'For sale', 2);
insert into category(category_id, category_name, parent_id) values (15, 'For rent', 2);
insert into category(category_id, category_name, parent_id) values (16, 'Laptops', 3);
insert into category(category_id, category_name, parent_id) values (17, 'PCs', 3);
insert into category(category_id, category_name, parent_id) values (18, 'For men', 4);
insert into category(category_id, category_name, parent_id) values (19, 'For women', 4);
insert into category(category_id, category_name, parent_id) values (20, 'Animal foods', 5);
insert into category(category_id, category_name, parent_id) values (21, 'Dogs', 5);
insert into category(category_id, category_name, parent_id) values (22, 'Cats', 5);
insert into category(category_id, category_name, parent_id) values (23, 'Food', 6);
insert into category(category_id, category_name, parent_id) values (24, 'Clothes', 6);
insert into category(category_id, category_name, parent_id) values (25, 'Swimming', 7);
insert into category(category_id, category_name, parent_id) values (26, 'Football', 7);
insert into category(category_id, category_name, parent_id) values (27, 'Guitars', 8);
insert into category(category_id, category_name, parent_id) values (28, 'Pianos', 8);
insert into category(category_id, category_name, parent_id) values (29, 'Supplements', 9);
insert into category(category_id, category_name, parent_id) values (30, 'Medical devices', 9);

insert into _user(id_user, email, name, password, phone_number, surname)
values (1, 'example@gmail.com', 'Andrzej', '$2a$12$m.wj9csFHQ0Y2Ou4Wd7O0OE6tFCoqBJ8pJW81lEzjmBkWOPAGx4BG', '111222333', 'Sasak');

insert into user_role(id_user, id_role) values (1, 1);

insert into auction(id_auction, contact_information, description, is_active, phone_number, posted_at, price, title, category_category_id, id_user)
values (1, 'Andrzej Sasak', 'Some interesting description', true, '111222333', '2023-05-13 12:30:00', '123', 'Title', 10, 1);

insert into image(id_image, image_name, id_auction) values (1, 'initial-img.jpg', 1);