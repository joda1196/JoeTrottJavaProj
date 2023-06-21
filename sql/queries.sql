--view products
SELECT * FROM Products;

--sort products by price
SELECT *
FROM Products
ORDER BY price ASC;

SELECT *
FROM Products
ORDER BY price DESC;

--view recent orders
SELECT Orders.order_id, Orders.order_date_created, Orders.order_total_cost, Products.product_name, Products.product_price, OrderItems.order_item_quantity
FROM OrderItems
INNER JOIN Orders  ON Orders.orders_id = OrderItems.order_items_orders_id
INNER JOIN Products ON Products.product_id = OrderItems.order_items_product_id
WHERE Orders.order_account_id = loggedInUser
ORDER BY order_date_created DESC;

--view account
SELECT *
FROM Accounts
WHERE account_id = loggedInUserID;

--add account
INSERT INTO Accounts (account_email, account_username, account_pw) VALUES (?, ?, ?);

--find account
SELECT account_id, account_username, account_email, account_pw FROM Accounts WHERE account_username = ? AND account_pw = ?;

--find product
SELECT product_id, product_name, product_price FROM Products WHERE product_name = ? LIMIT 1;");

--add order
INSERT INTO Orders (order_total_cost, order_account_id) VALUES (?, ?);

--find most recent order
SELECT * FROM Orders ORDER BY order_id DESC LIMIT 1;

--add orderitems/junction table
INSERT INTO OrderItems (order_items_product_id, order_items_order_id, order_items_quantity) VALUES (?, ?, ?);