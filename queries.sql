-- 1. Query to show all equipment types and their total count
SELECT et.Equipment_Type_Name, COUNT(e.Equipment_ID) AS Total_Count
FROM Equipment_Type et
LEFT JOIN Equipment e ON et.Equipment_Type_ID = e.Equipment_Type_ID
GROUP BY et.Equipment_Type_Name
ORDER BY Total_Count DESC;

-- 2. Query to show customers who have rented equipment more than once
SELECT c.Customer_ID, c.Name, COUNT(r.Rental_ID) AS Rental_Count
FROM Customer c
JOIN Rental r ON c.Customer_ID = r.Customer_ID
GROUP BY c.Customer_ID, c.Name
HAVING COUNT(r.Rental_ID) > 1
ORDER BY Rental_Count DESC;

-- 3. Query to show the total revenue generated from rentals per equipment type
SELECT et.Equipment_Type_Name, SUM(r.Price) AS Total_Revenue
FROM Equipment_Type et
JOIN Equipment e ON et.Equipment_Type_ID = e.Equipment_Type_ID
JOIN Rental r ON e.Equipment_ID = r.Equipment_ID
GROUP BY et.Equipment_Type_Name
ORDER BY Total_Revenue DESC;

-- 4. Advanced join query to show customers with overdue rentals and the amount owed
SELECT c.Customer_ID, c.Name, r.Rental_ID, e.Name AS Equipment_Name, 
       o.Overdue_Date, o.Amount AS Overdue_Amount
FROM Customer c
JOIN Rental r ON c.Customer_ID = r.Customer_ID
JOIN Equipment e ON r.Equipment_ID = e.Equipment_ID
JOIN Overdue o ON r.Rental_ID = o.Rental_ID
WHERE r.Status = 'Overdue'
ORDER BY o.Amount DESC;

-- 5. Advanced join query to show staff performance based on number of rentals and total revenue
SELECT s.Staff_ID, s.Name, COUNT(r.Rental_ID) AS Total_Rentals, 
       SUM(r.Price) AS Total_Revenue
FROM Staff s
LEFT JOIN Rental r ON s.Staff_ID = r.Staff_ID
GROUP BY s.Staff_ID, s.Name
ORDER BY Total_Revenue DESC;

-- View 1: Equipment availability view
CREATE OR REPLACE VIEW Equipment_Availability AS
SELECT e.Equipment_ID, e.Name, e.Rental_Status, et.Equipment_Type_Name
FROM Equipment e
JOIN Equipment_Type et ON e.Equipment_Type_ID = et.Equipment_Type_ID;

-- View 2: Customer rental history view
CREATE OR REPLACE VIEW Customer_Rental_History AS
SELECT c.Customer_ID, c.Name, r.Rental_ID, e.Name AS Equipment_Name, 
       r.Rental_Date, r.Estimated_Return_Date, r.Status
FROM Customer c
JOIN Rental r ON c.Customer_ID = r.Customer_ID
JOIN Equipment e ON r.Equipment_ID = e.Equipment_ID;

-- View 3: Monthly revenue report view
CREATE OR REPLACE VIEW Monthly_Revenue_Report AS
SELECT TO_CHAR(r.Rental_Date, 'YYYY-MM') AS Month, 
       SUM(r.Price) AS Total_Revenue,
       COUNT(r.Rental_ID) AS Total_Rentals
FROM Rental r
GROUP BY TO_CHAR(r.Rental_Date, 'YYYY-MM')
ORDER BY Month;