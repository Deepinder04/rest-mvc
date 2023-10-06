drop table if exists beer_order;

drop table if exists beer_order_line;

create table beer_order(
    id varchar(36) NOT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    customer_ref varchar(255) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    version BIGINT DEFAULT NULL,
    customer_id varchar(36) DEFAULT NULL,
    primary key(id),
    CONSTRAINT foreign key(customer_id) references customer (id)
) ENGINE = InnoDB;

create table beer_order_line(
    id varchar(36) NOT NULL,
    beer_id varchar(36) DEFAULT NULL,
    created_at DATETIME(6) DEFAULT NULL,
    updated_at DATETIME(6) DEFAULT NULL,
    order_quantity int DEFAULT NULL,
    quantity_allocated int DEFAULT NULL,
    version BIGINT DEFAULT NULL,
    beer_order_id varchar(36) DEFAULT NULL,
    primary key (id),
    CONSTRAINT foreign key (beer_id) references beer (id),
    CONSTRAINT foreign key (beer_order_id) references beer_order(id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;