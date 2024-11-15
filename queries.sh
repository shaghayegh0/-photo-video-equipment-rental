#!/bin/sh
sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

-- 1. List all customers with distinct names
SELECT DISTINCT Name FROM Customer;

-- 2. Select all equipment available for rent sorted by rental price per day (descending)
SELECT Name, EQUIPMENT_PRICE, Condition FROM Equipment
WHERE STATUS = 'Available'
ORDER BY EQUIPMENT_PRICE DESC;

-- 3. Retrieve all overdue payments with status 'Paid'
SELECT * FROM Overdue
WHERE Status = 'Paid';

-- 4. Count the number of rentals per customer, showing customer name and rental count
SELECT c.Name, COUNT(r.Rental_ID) AS Rental_Count 
FROM Customer c
JOIN Rental r ON c.Customer_ID = r.Customer_ID
GROUP BY c.Name
ORDER BY Rental_Count DESC;

-- 5. Show the top 3 customers with the most equipment reservations
SELECT c.Name, COUNT(res.Reservation_ID) AS Total_Reservations 
FROM Customer c
JOIN Reservation res ON c.Customer_ID = res.Customer_ID
GROUP BY c.Name
ORDER BY Total_Reservations DESC
FETCH FIRST 3 ROWS ONLY;

-- 6. List all staff members and their positions, sorted alphabetically by name
SELECT Name, Position FROM Staff
ORDER BY Name;


-- 7. Select all equipment under maintenance and their respective maintenance cost
SELECT eq.Name, m.Maintenance_Cost 
FROM Equipment eq
JOIN Maintenance m ON eq.Equipment_ID = m.Equipment_ID
WHERE m.Status = 'Processing'
ORDER BY m.Maintenance_Cost DESC;

-- 8. List distinct equipment types in the database
SELECT DISTINCT Equipment_Type_Name FROM Equipment_Type;


-- 9. Calculate the total rental revenue for each staff member
SELECT s.Name, SUM(r.RENTAIL_PRICE) AS Total_Revenue 
FROM Staff s
JOIN Rental r ON s.Staff_ID = r.Staff_ID
GROUP BY s.Name
ORDER BY Total_Revenue DESC;



-- 10. Retrieve all payment records with payment methods that were declined
SELECT * FROM Payment
WHERE Status = 'Declined';

-- 11. Count the total number of overdue payments grouped by customer
SELECT c.Name AS Customer_Name, COUNT(o.Overdue_ID) AS Total_Overdue
FROM Rental_Customer rc
JOIN Overdue o ON rc.Rental_ID = o.Rental_ID
JOIN Customer c ON rc.Customer_ID = c.Customer_ID
GROUP BY c.Name;

-- 12. List all equipment purchased in 2023, sorted by purchase date
SELECT Name, Purchase_Date, Equipment_Price FROM Equipment
WHERE EXTRACT(YEAR FROM Purchase_Date) = 2023
ORDER BY Purchase_Date DESC;

-- 13. Show the average maintenance cost for each equipment type
SELECT et.Equipment_Type_Name, AVG(m.Maintenance_Cost) AS Avg_Maintenance_Cost
FROM Equipment_Type et
JOIN Equipment eq ON et.Equipment_Type_ID = eq.Equipment_Type_ID
JOIN Maintenance m ON eq.Equipment_ID = m.Equipment_ID
GROUP BY et.Equipment_Type_Name;

-- 14. List all rental history records for a specific customer by name
SELECT rh.Rental_History_ID, eq.Name, rh.Rental_Date, rh.Duration, rh.Rental_Return 
FROM Rental_History rh
JOIN Customer c ON rh.Customer_ID = c.Customer_ID
JOIN Equipment eq ON rh.Equipment_ID = eq.Equipment_ID
WHERE c.Name = 'John Doe';

-- 15. Show the equipment that has never been rented
SELECT eq.Name FROM Equipment eq
LEFT JOIN Rental r ON eq.Equipment_ID = r.Equipment_ID
WHERE r.Rental_ID IS NULL;


EOF

