use hw_1;

drop table if exists Bought;
drop table if exists Listing;
drop table if exists Product;
drop table if exists Seller;
drop table if exists Category;
drop table if exists paymentMethod;
drop table if exists Orders;
drop table if exists Customer;


CREATE TABLE Seller(
	sellerId int,
    fullName varChar(20),
    contactInfo varChar(250),
    primary key (sellerId));



CREATE TABLE Category(
    cname VARCHAR(250),
    primary key (cname)
);


CREATE TABLE Customer (
	customerId int,
    userName varChar(20),
    address varChar(250),
    primary key (customerId)
);


CREATE TABLE paymentMethod(
    method varChar(50),
    customerId INT,
    primary key (customerId, method),
    foreign key (customerId) references Customer(customerId)
);

CREATE TABLE Orders(
	orderId int,
    customerId int,
    orderStatus boolean,
	date varChar(8),
    primary key (orderId),
	foreign key (customerId) references Customer(customerId)
);


CREATE TABLE Product(
    productId int,
    cname VARCHAR(250),
    sellerId int,
    pName varChar(50),
    primary key (productId),
    foreign key (cname) references Category(cname)
);


CREATE TABLE Listing(
    listingId int,
    sellerId int,
    productId int,
    price decimal(10, 2),
    stock INT CHECK (stock >= 0),
    primary key (listingId),
    foreign key (sellerId) references Seller(sellerId),
    foreign key (productId) references Product(productId) 
);


CREATE TABLE Bought(
	purchaseId int,
    productId int,
    customerId int,
    quantity int CHECK(quantity<=3),
    primary key (purchaseId),
    foreign key (productId) references Product(productId),
    foreign key (customerId) references Customer(customerId)
);