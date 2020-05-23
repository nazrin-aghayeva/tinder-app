create table if not exists messages
(
	messageid serial not null
		constraint tinderam_messages_pk
			primary key,
	senderid integer,
	receiverid integer,
	text varchar
);

alter table messages owner to cpwtvtnlbkxxbn;