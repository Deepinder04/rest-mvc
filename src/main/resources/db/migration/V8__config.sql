create table config (
id int not null auto_increment,
type varchar(36) not null,
category varchar(36) not null,
data json not null,
created_at datetime(6),
updated_at datetime(6),
primary key(id)
);