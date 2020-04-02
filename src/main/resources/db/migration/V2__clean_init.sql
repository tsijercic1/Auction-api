
create sequence hibernate_sequence;

create table categories
(
	id bigint not null
		constraint categories_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	name varchar(255)
);


create table subcategories
(
	id bigint not null
		constraint subcategories_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	name varchar(255),
	category_id bigint
		constraint fkiborb6ptvy1t1n3v6klb56l5s
			references categories
);

create table roles
(
	id bigint not null
		constraint roles_pkey
			primary key,
	name varchar(60)
		constraint uk_nb4h0p6txrmfc0xbrd1kglp9t
			unique
);



create table users
(
	id bigint not null
		constraint users_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	email varchar(320)
		constraint uk6dotkott2kjsp8vw4d0m25fb7
			unique,
	gender varchar(255),
	name varchar(40),
	password varchar(100),
	phone_number varchar(255),
	profile_picture_url varchar,
	surname varchar(40)
);

create table if not exists user_roles
(
    user_id bigint not null
        constraint fkhfh9dx7w3ubf1co1vdev94g3f
            references users,
    role_id bigint not null
        constraint fkh8ciramu9cc9q3qcqiv4ue8a6
            references roles,
    constraint user_roles_pkey
        primary key (user_id, role_id)
);

insert into roles (id, name)
values (1, 'ROLE_USER');


insert into users
values (1, date('2020-02-29 11:07:52.663000'), date('2020-02-29 11:07:52.663000'), 'tarik.sijercic98@gmail.com', null,
        'Tarik', '$2a$10$CWsDtNyZFv1ZY65s7xWzA.f/4v0AyVT7.KgRWkpaBWiQ.xCPxv3lK', null, null, 'Sijerčić');


insert into user_roles
values (1, 1);




create table products
(
	id bigint not null
		constraint products_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	auction_end timestamp,
	auction_start timestamp,
	description varchar(255),
	name varchar(255),
	start_price numeric(19,2),
	seller_id bigint
		constraint fkbgw3lyxhsml3kfqnfr45o0vbj
			references users,
	subcategory_id bigint
		constraint fkappm930ygdfv4qkkhc05pbr5s
			references subcategories
);


create table bids
(
	id bigint not null
		constraint bids_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	amount numeric(19,2),
	bidder_id bigint
		constraint fkmtrc6tnwawlpk1u2km6qnxbha
			references users,
	product_id bigint
		constraint fkhtewr5n8ee2tlu0rj67d6y14p
			references products
);


create table product_pictures
(
	id bigint not null
		constraint product_pictures_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	url varchar(255),
	product_id bigint
		constraint fk6rog63kl5t3e1b3u3vdpkc6n2
			references products
);


create table watches
(
	id bigint not null
		constraint watches_pkey
			primary key,
	created_at timestamp not null,
	updated_at timestamp not null,
	product_id bigint
		constraint fkpkyxjoa14h6a45gu07u8qlsr2
			references products,
	watcher_id bigint
		constraint fk9yirjrmybk1cpybnhu7314tne
			references users
);
