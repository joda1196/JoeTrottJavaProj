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