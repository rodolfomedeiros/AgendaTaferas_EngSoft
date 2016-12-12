create database taskprimedb;

user taskprimedb;

create table users(
	id int primary key auto_increment,
	name varchar(256) not null,
	email varchar(256) not null,
	login varchar(256) not null,
	password varchar(256) not null
);

create table tasks(
	id int primary key auto_increment,
	name varchar(256) not null,
	description varchar(256) not null default "sem descrição...",
	date_finished date not null,
	time_finished time not null,
	finished bit(1) not null default 0,
	group_task int not null,
	id_user int not null
);

create table groups(
	id int primary key auto_increment,
	name varchar(256) not null,
	id_user int not null
);
