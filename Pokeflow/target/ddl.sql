alter table post drop constraint FK3498A0A294E069;
drop table member if exists;
drop table post if exists;
create table member (id bigint generated by default as identity, version bigint not null, birthday timestamp not null, email varchar(255) not null, first_name varchar(255) not null, last_name varchar(255) not null, nickname varchar(255) not null, password varchar(255) not null, photo varchar(255) not null, score integer not null, primary key (id));
create table post (id bigint generated by default as identity, version bigint not null, created timestamp not null, text varchar(255) not null, class varchar(255) not null, modified timestamp, title varchar(255), post_id bigint, validate boolean, primary key (id));
alter table post add constraint FK3498A0A294E069 foreign key (post_id) references post;
