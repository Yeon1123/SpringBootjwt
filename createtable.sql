create table member_table (
	member_id SERIAL PRIMARY KEY,
	member_email varchar(255) not null,
	member_password varchar(255) not null,
	member_name varchar(255) not null
);