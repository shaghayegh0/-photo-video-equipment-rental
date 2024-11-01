#!/bin/sh
sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF


-- 1. Query to List Customers Who Have Not Rented Any Equipment

SELECT Name, Email
FROM Customer C
WHERE NOT EXISTS (
    SELECT 1
    FROM Rental R
    WHERE R.Customer_ID = C.Customer_ID
);


-- 2. Query to Count Rentals Grouped by Equipment Condition

SELECT Condition, COUNT(*) AS Total_Rentals
FROM Equipment E
JOIN Rental R ON E.Equipment_ID = R.Equipment_ID
GROUP BY Condition
HAVING COUNT(*) > 5;

-- 3. Query to Retrieve Rentals That Have Overdue Charges Using UNION

SELECT R.Rental_ID, R.Customer_ID, R.Status
FROM Rental R
WHERE R.Status = 'Overdue'
UNION
SELECT O.Rental_ID, O.Customer_ID, 'Overdue Charge' AS Status
FROM Overdue O;

-- 4. Query to Find Equipment That Has Never Been Reserved

SELECT E.Equipment_ID, E.Name
FROM Equipment E
MINUS
SELECT R.Equipment_ID, E.Name
FROM Equipment E
JOIN Reservation R ON E.Equipment_ID = R.Equipment_ID;


-- 5. Query to List Customers with Overdue Rentals but No Payments

SELECT C.Name, C.Email
FROM Customer C
WHERE EXISTS (
    SELECT 1
    FROM Overdue O
    WHERE O.Customer_ID = C.Customer_ID
)
AND NOT EXISTS (
    SELECT 1
    FROM Payment P
    WHERE P.Customer_ID = C.Customer_ID
);

-- 6. Query to Calculate Total Maintenance Cost per Equipment

SELECT E.Name, SUM(M.Maintenance_Cost) AS Total_Cost
FROM Equipment E
JOIN Maintenance M ON E.Equipment_ID = M.Equipment_ID
GROUP BY E.Name
HAVING SUM(M.Maintenance_Cost) > 1000;


EOF
