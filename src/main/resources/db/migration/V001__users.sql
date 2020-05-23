create table if not exists users
(
	id serial not null
		constraint tinderam_users_pk
			primary key,
	name varchar,
	surname varchar,
	login varchar,
	password varchar,
	imgurl varchar,
	position varchar,
	gender varchar
);

alter table users owner to cpwtvtnlbkxxbn;