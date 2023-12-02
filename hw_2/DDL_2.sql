USE HW_2;

DROP TABLE IF EXISTS CategoryStats;
DROP TABLE IF EXISTS Orders;
DROP TABLE IF EXISTS Listing;
DROP TABLE IF EXISTS Product;
DROP TABLE IF EXISTS Category;
DROP TABLE IF EXISTS Payment;
DROP TABLE IF EXISTS User;

    
CREATE TABLE User (
    userId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(50),
    address VARCHAR(255)
);

CREATE TABLE Payment (
    paymentId INT PRIMARY KEY,
    userId INT,
    cardNumber VARCHAR(50),
    FOREIGN KEY (userId) REFERENCES User(userId)
);

CREATE TABLE Category (
    categoryId INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Product (
    productId INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    description VARCHAR(255)
);

CREATE TABLE Listing (
    listingId INT PRIMARY KEY,
    sellerId INT,
    productId INT,
    price INT,
    stock INT,
    FOREIGN KEY (sellerId) REFERENCES User(userId),
    FOREIGN KEY (productId) REFERENCES Product(productId)
);


CREATE TABLE Orders (
    orderId INT PRIMARY KEY,
    listingId INT,
    userId INT,
    date DATE,
    FOREIGN KEY (listingId) REFERENCES Listing(listingId),
    FOREIGN KEY (userId) REFERENCES User(userId)
);

CREATE TABLE CategoryStats (
    categoryId INT PRIMARY KEY,
    averagePrice DOUBLE
);