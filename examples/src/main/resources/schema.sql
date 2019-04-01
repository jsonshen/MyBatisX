create table if not exists no_pk (
    qq_num int not null,
    real_name varchar(50),
    nickname varchar(50),
    password varchar(50)
);

create table if not exists single_col_pk (
    qq_num int not null,
    real_name varchar(50),
    nickname varchar(50),
    password varchar(50),
    primary key (qq_num)
);

create table if not exists multi_col_pk (
    qq_num int not null,
    real_name varchar(50),
    nickname varchar(50),
    password varchar(50),
    primary key (qq_num, real_name)
);
