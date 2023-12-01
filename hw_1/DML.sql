use hw_1;    
    
insert into Seller values (1, 'Ege Oruc', 'ege@mail.com');
insert into Seller values (2, 'W. White', 'white.com');
insert into Seller values (3, 'Gustavo Fring', "Los Pollos Hermanos");
insert into Seller values (4, "Tuco Salamanca", "+012 345 678");

insert into Category values ("Health");
insert into Category values ("Personal Care");
insert into Category values ("Furniture");	
insert into Category values ("Electronics");

insert into Customer values (23 , "ilymom", "Cekmekoy" );
insert into Customer values (25 , "cmylmz", "Besiktas" );
insert into Customer values (3 , "ayberkuman", "Antalya" );
insert into Customer values (1, "egeruc", "Eskisehir");

insert into paymentMethod values ('Crypto', 1);
insert into paymentMethod values ('MasterCard', 25);
insert into paymentMethod values ('Visa', 3);

insert into Orders values ( 28, 3, 1,"21.03.23");
insert into Orders values ( 23, 25, 0,"27.05.23");
insert into Orders values ( 2, 3, 1,"21.03.23");

insert into Product values ( 236, "Electronics", 2, "Smart Phone");
insert into Product values ( 23, "Health", 2, "Plaster");
insert into Product values ( 6, "Personal Care", 4, "Q-tips");

insert into Listing values ( 1, 3, 236, 999.00, 5);
insert into Listing values ( 2, 4, 23, 2.5, 6);
insert into Listing values ( 3, 3 , 6, 1.0, 8);

insert into Bought values (1, 236, 3, 2);
insert into Bought values (2, 23, 25, 1);
insert into Bought values (3, 6, 1, 3);

update Listing set price = 89.99 where listingId = 1;

update Customer set address = 'Dorm6' where customerId = 1;

delete from Product where productId = 1;

delete from Seller where sellerId = 1;

delete from Listing where listingId = 1;

delete from Customer where customerId = 7;

delete from Orders where orderId = 1;

insert into paymentMethod  values ('on Delivery Cash', 23);

select * from Product where cname = 'Electronics';

select * from Seller;

select COUNT(*) from Orders;

select * from Customer 
where customerId in (select customerId from Orders where date = '23.11.12');

select * from Orders where customerId = 3;

select * from Product where productId = 6;

select * from Product where pName = 'Q-tips';

select SUM(Listing.price) as TotalPrice
from Bought
join Product on Bought.productId = Product.productId
join Listing on Product.productId = Listing.productId
where Product.productId = 6;


select Product.pName, COUNT(*) as QuantitySold
from Bought JOIN Product on Bought.productId = Product.productId
group by Product.pName
order by QuantitySold desc;

SELECT * FROM Product WHERE productId NOT IN (SELECT productId FROM Bought) OR productId IN (SELECT productId FROM Bought GROUP BY productId HAVING COUNT(*) < 5);

SELECT * FROM Product WHERE sellerId = 3;

select c.cname, avg(p.pName) as AveragePrice
from Product p
join Category c on p.cname = c.cname
group by c.cname;