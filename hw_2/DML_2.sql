USE HW_2;

INSERT INTO User  VALUES (1, 'Seller1', 'seller', 'Seller1 Address');
INSERT INTO User  VALUES (2, 'Customer1', 'customer', 'Customer1 Address');

INSERT INTO Category (categoryId, name) VALUES (1, 'Electronics');
INSERT INTO Category (categoryId, name) VALUES (2, 'Clothing');

INSERT INTO Product (productId, name) VALUES (1, 'Laptop');
INSERT INTO Product (productId, name) VALUES (2, 'T-Shirt');

INSERT INTO Listing (listingId, sellerId, productId, price, stock) VALUES (1, 1, 1, 1000, 10);
INSERT INTO Listing (listingId, sellerId, productId, price, stock) VALUES (2, 1, 2, 20, 50);


INSERT INTO Orders (orderId, listingId, userId, date) VALUES (2, 2, 2, '2023-03-01');
INSERT INTO Orders (orderId, listingId, userId, date) VALUES (3, 1, 2, '2023-05-01');
INSERT INTO Orders (orderId, listingId, userId, date) VALUES (4, 1, 2, '2023-07-01');
INSERT INTO Orders (orderId, listingId, userId, date) VALUES (5, 2, 2, '2023-08-01');


INSERT INTO CategoryStats (categoryId, averagePrice) VALUES (1, 800);
INSERT INTO CategoryStats (categoryId, averagePrice) VALUES (2, 25);

SELECT * FROM  Orders;








