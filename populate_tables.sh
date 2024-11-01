#!/bin/sh
sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

-- Insert statements for Customer table
INSERT INTO Customer (Name, Email, Phone, Address) VALUES 
('Sarah', 'sarah_james@gmail.com', '703-701-9964', '825, rue Saint-Laurent O, Longueuil, Quebec');

INSERT INTO Customer (Name, Email, Phone, Address) VALUES 
('Bob', 'bob@gmail.com', '703-710-8261', '200 County Ct Blvd, Brampton, Ontario');

-- Insert statements for Staff table
INSERT INTO Staff (Name, Position) VALUES 
('Donald', 'Manager');

INSERT INTO Staff (Name, Position) VALUES 
('Kate', 'Employee');

-- Insert statements for Equipment Type table
INSERT INTO Equipment_Type (Equipment_Type_Name) VALUES 
('Action Cameras');

INSERT INTO Equipment_Type (Equipment_Type_Name) VALUES 
('Digital Cameras');

INSERT INTO Equipment_Type (Equipment_Type_Name) VALUES 
('Film Cameras');

-- Insert statements for Equipment table
INSERT INTO Equipment (Name, Equipment_Type_ID, Rental_Status, Rental_Price_PerDay, Condition, Purchase_Date, Equipment_price) VALUES 
('Go Pro', 1, 'Rented', 60, 'Good', TO_DATE('2016-07-06', 'YYYY-MM-DD'), 399);

INSERT INTO Equipment (Name, Equipment_Type_ID, Rental_Status, Rental_Price_PerDay, Condition, Purchase_Date, Equipment_price) VALUES 
('DSLR', 2, 'Available', 100, 'Brand new', TO_DATE('2019-08-03', 'YYYY-MM-DD'), 649.99);

INSERT INTO Equipment (Name, Equipment_Type_ID, Rental_Status, Rental_Price_PerDay, Condition, Purchase_Date, Equipment_price) VALUES 
('Fujifilm Discovery', 3, 'Rented', 50, 'Well used', TO_DATE('2013-08-17', 'YYYY-MM-DD'), 262.8);

-- Insert statements for Rental table
INSERT INTO Rental (Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Estimated_Return_Date, Estimated_Duration_Days, Price, Status) VALUES 
(2, 1, 2, TO_DATE('2019-06-05', 'YYYY-MM-DD'), TO_DATE('2019-06-10', 'YYYY-MM-DD'), 5, 1500, 'Ongoing');

INSERT INTO Rental (Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Estimated_Return_Date, Estimated_Duration_Days, Price, Status) VALUES 
(1, 2, 3, TO_DATE('2022-07-01', 'YYYY-MM-DD'), TO_DATE('2022-07-17', 'YYYY-MM-DD'), 7, 700, 'Ongoing');

-- Insert statements for Rental History table
INSERT INTO Rental_History (Rental_History_ID, Rental_ID, Return_Date) VALUES 
(1, 1, TO_DATE('2019-06-10', 'YYYY-MM-DD'));

-- Insert statements for Overdue table
INSERT INTO Overdue (Rental_ID, Customer_ID, Overdue_Date, Amount, Payment_Method, Status) VALUES 
(1, 1, TO_DATE('2024-09-28', 'YYYY-MM-DD'), 649.99, 'cash', 'Declined');

INSERT INTO Overdue (Rental_ID, Customer_ID, Overdue_Date, Amount, Payment_Method, Status) VALUES 
(2, 2, TO_DATE('2024-08-27', 'YYYY-MM-DD'), 399, 'card', 'Paid');

-- Insert statements for Payment table
INSERT INTO Payment (Rental_ID, Customer_ID, Payment_Date, Amount, Payment_Method, Status) VALUES 
(1, 1, TO_DATE('2024-10-20', 'YYYY-MM-DD'), 10, 'cash', 'Declined');

INSERT INTO Payment (Rental_ID, Customer_ID, Payment_Date, Amount, Payment_Method, Status) VALUES 
(2, 2, TO_DATE('2024-10-20', 'YYYY-MM-DD'), 20, 'card', 'Accepted');

-- Insert statements for Payment History table
INSERT INTO Payment_History (Payment_History_ID, Payment_ID, Rental_ID, Customer_ID, Payment_Date, Amount, Payment_Method) VALUES 
(1, 1, 1, 1, TO_DATE('2024-10-20', 'YYYY-MM-DD'), 30, 'cash');

INSERT INTO Payment_History (Payment_History_ID, Payment_ID, Rental_ID, Customer_ID, Payment_Date, Amount, Payment_Method) VALUES 
(2, 2, 2, 2, TO_DATE('2024-10-20', 'YYYY-MM-DD'), 10, 'card');

-- Insert statements for Reservation table
INSERT INTO Reservation (Reservation_ID, Customer_ID, Equipment_ID, Reservation_Date, Expiration_Date, Status) VALUES 
(1, 1, 1, TO_DATE('2024-10-20', 'YYYY-MM-DD'), TO_DATE('2024-10-27', 'YYYY-MM-DD'), 'Confirmed');

INSERT INTO Reservation (Reservation_ID, Customer_ID, Equipment_ID, Reservation_Date, Expiration_Date, Status) VALUES 
(2, 2, 2, TO_DATE('2024-10-20', 'YYYY-MM-DD'), TO_DATE('2024-10-27', 'YYYY-MM-DD'), 'Canceled');

-- Insert statements for Maintenance table
INSERT INTO Maintenance (Maintenance_ID, Staff_ID, Equipment_ID, Maintenance_Start_Date, Maintenance_Cost, Expected_Completion_Date, Status) VALUES 
(1, 1, 1, TO_DATE('2024-10-20', 'YYYY-MM-DD'), 30, TO_DATE('2024-10-27', 'YYYY-MM-DD'), 'Processing');

INSERT INTO Maintenance (Maintenance_ID, Staff_ID, Equipment_ID, Maintenance_Start_Date, Maintenance_Cost, Expected_Completion_Date, Status) VALUES 
(2, 2, 2, TO_DATE('2024-10-21', 'YYYY-MM-DD'), 45, TO_DATE('2024-10-28', 'YYYY-MM-DD'), 'Completed');

EOF

