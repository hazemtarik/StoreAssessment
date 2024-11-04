CREATE TABLE IF NOT EXISTS ITEMS (
    id varchar2(36) primary key,
    name varchar2(100) not null,
    quantity number not null check ( quantity >= 0)
);