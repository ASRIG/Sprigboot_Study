insert into user (email, name, password, user_id) values ('admin@gmial.com', 'admin', 'admin', 'admin'), ('admin@gmial.com', 'a', 'a', 'a');
insert into question(id, writer_id, title, contents, create_date) values (1, 1, '타이틀 내용', '컨텐츠 내용', CURRENT_TIMESTAMP);
insert into question(id, writer_id, title, contents, create_date) values (2, 2, '내가 쓴 글임용', '냉무', CURRENT_TIMESTAMP);