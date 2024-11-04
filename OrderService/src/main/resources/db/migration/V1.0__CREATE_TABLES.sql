CREATE TABLE IF NOT EXISTS items
(
    id       VARCHAR(255) NOT NULL,
    ITEM_ID VARCHAR(255) NOT NULL,
    order_id VARCHAR(255),
    quantity INT          NOT NULL,
    CONSTRAINT pk_items PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS orders
(
    id         VARCHAR(255) NOT NULL,
    status     VARCHAR(255),
    order_date TIMESTAMP,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE items
    ADD CONSTRAINT FK_ITEMS_ON_ORDER FOREIGN KEY (order_id) REFERENCES orders (id);
CREATE INDEX IND_ITEMS_ON_ORDER_ID on items (order_id);