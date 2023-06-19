--Accounts can have multiple orders, orders can have multiple products

CREATE TABLE Accounts (
    account_id BIGSERIAL PRIMARY KEY,
    account_email VARCHAR(25) NOT NULL,
    account_username VARCHAR(25) NOT NULL,
    account_pw VARCHAR(25) NOT NULL
    CONSTRAINT uc_username UNIQUE (account_username);
);


CREATE TABLE Products (
    product_id BIGSERIAL PRIMARY KEY,
    product_name VARCHAR(25) NOT NULL,
    product_price DECIMAL(10,2)
);

CREATE TABLE Orders (
    order_id BIGSERIAL PRIMARY KEY,
    order_date_created SET DEFAULT CURRENT_DATE,
    order_total_cost DECIMAL(10,2),
    order_account_id INT,
    FOREIGN KEY (order_account_id) REFERENCES Accounts(account_id) ON DELETE CASCADE
);

--junction table: One Order has multiple Products
CREATE TABLE OrderItems (
    order_items_id BIGSERIAL PRIMARY KEY,
    order_items_product_id INT,
    order_items_order_id INT,
    FOREIGN KEY (order_items_product_id) REFERENCES Products(product_id) ON DELETE RESTRICT,
    FOREIGN KEY (order_items_order_id) REFERENCES Orders(order_id) ON DELETE CASCADE,
    order_items_quantity INT
);

