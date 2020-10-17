create table user(
userid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
name varchar(255) not null,
pass varchar(255) not null,
status varchar(255) not null
);

create table message(
messageid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
senderid int,
content varchar(16000),
foreign key (senderid) references user(userid)
);

create table message_reciept(
messageid int,
userid int,
isRead int,
receiptid int NOT NULL AUTO_INCREMENT PRIMARY KEY,
foreign key (userid) references user(userid),
foreign key (messageid) references message(messageid)
);
