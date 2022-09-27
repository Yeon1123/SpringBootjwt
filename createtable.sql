create table member_table (
	member_id SERIAL,
	member_email varchar(255) PRIMARY KEY,
	member_password varchar(255) not null,
	member_name varchar(255) not null
);