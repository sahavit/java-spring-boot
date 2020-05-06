INSERT INTO customers (customer_id, name, email) values (501, 'Mark', 'mark@gmail.com');
INSERT INTO customers (customer_id, name, email) values (502, 'Sam', 'sam@gmail.com');
INSERT INTO customers (customer_id, name, email) values (503, 'Joe', 'joe@gmail.com');
INSERT INTO customers (customer_id, name, email) values (504, 'Dan', 'dan@gmail.com');
INSERT INTO customers (customer_id, name, email) values (505, 'Kim', 'kim@gmail.com');

INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (101, 502, 'Television', 8000, 2);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (102, 503, 'Smartphone', 5000, 4);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (103, 501, 'Basketball', 200, 3);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (104, 502, 'Shirt', 500, 6);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (105, 504, 'Smartphone', 5000, 1);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (106, 505, 'Headphones', 850, 3);
INSERT INTO orders (order_id, customer_id, item_name, item_price, quantity) values (107, 504, 'Shoes', 1500, 5);

commit;