drop table if exists authority;

create table authority(
 id varchar(36) NOT NULL,
 role varchar(36) NOT NULL,
 customer_id varchar(36) DEFAULT NULL,
 primary key (id),
 CONSTRAINT foreign key(customer_id) references customer (id)
)ENGINE = InnoDB;

alter table customer add column password varchar(255) not null;
alter table customer add column account_non_expired bit default true;
alter table customer add column account_non_locked bit default true;
alter table customer add column credentials_non_expired bit default true;
alter table customer add column enabled bit default true;
ALTER TABLE customer CHANGE COLUMN `customer_name` `username` VARCHAR(255) NULL DEFAULT NULL ;
alter table customer add column authority_id varchar(255);
alter table customer add constraint cust_auth_fk foreign key(authority_id) references authority (id);

create table customer_authority
(
    customer_id     varchar(36) NOT NULL,
    authority_id varchar(36) NOT NULL,
    primary key (customer_id, authority_id),
    constraint pc_customer_id_fk FOREIGN KEY (customer_id) references customer (id),
    constraint pc_authority_id_fk FOREIGN KEY (authority_id) references authority (id)
) ENGINE = InnoDB;