CREATE DATABASE HW_2;

USE HW_2;

CREATE TABLE User (
    userId INT PRIMARY KEY,
    name VARCHAR(255),
    type VARCHAR(50),
    address VARCHAR(255)
);

CREATE TABLE Category (
    categoryId INT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE Product (
    productId INT PRIMARY KEY,
    name VARCHAR(255)
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