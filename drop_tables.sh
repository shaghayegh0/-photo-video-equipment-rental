#!/bin/sh
sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

-- Disable foreign key constraints
ALTER TABLE Overdue DISABLE CONSTRAINT fk_overdue_rental;
ALTER TABLE Overdue DISABLE CONSTRAINT fk_overdue_customer;
ALTER TABLE Payment DISABLE CONSTRAINT fk_payment_rental;
ALTER TABLE Payment DISABLE CONSTRAINT fk_payment_customer;
ALTER TABLE Rental DISABLE CONSTRAINT fk_rental_staff;
ALTER TABLE Rental DISABLE CONSTRAINT fk_rental_customer;
ALTER TABLE Rental DISABLE CONSTRAINT fk_rental_equipment;
ALTER TABLE Equipment DISABLE CONSTRAINT fk_equipment_type;
ALTER TABLE Customer_Phone DISABLE CONSTRAINT fk_customer;
ALTER TABLE Customer_Email DISABLE CONSTRAINT fk_customer_email;
ALTER TABLE Reservation DISABLE CONSTRAINT fk_reservation_customer;
ALTER TABLE Reservation DISABLE CONSTRAINT fk_reservation_equipment;
ALTER TABLE Maintenance DISABLE CONSTRAINT fk_maintenance_equipment;
ALTER TABLE Maintenance DISABLE CONSTRAINT fk_maintenance_staff;

-- Truncate the contents of the tables
TRUNCATE TABLE Payment_History;
TRUNCATE TABLE Payment;
TRUNCATE TABLE Overdue;
TRUNCATE TABLE Rental_History;
TRUNCATE TABLE Rental;
TRUNCATE TABLE Maintenance;
TRUNCATE TABLE Reservation;
TRUNCATE TABLE Equipment;
TRUNCATE TABLE Equipment_Type;
TRUNCATE TABLE Staff;
TRUNCATE TABLE Customer;

-- Enable foreign key constraints again
ALTER TABLE Overdue ENABLE CONSTRAINT fk_overdue_rental;
ALTER TABLE Overdue ENABLE CONSTRAINT fk_overdue_customer;
ALTER TABLE Payment ENABLE CONSTRAINT fk_payment_rental;
ALTER TABLE Payment ENABLE CONSTRAINT fk_payment_customer;
ALTER TABLE Rental ENABLE CONSTRAINT fk_rental_staff;
ALTER TABLE Rental ENABLE CONSTRAINT fk_rental_customer;
ALTER TABLE Rental ENABLE CONSTRAINT fk_rental_equipment;
ALTER TABLE Equipment ENABLE CONSTRAINT fk_equipment_type;
ALTER TABLE Customer_Phone ENABLE CONSTRAINT fk_customer;
ALTER TABLE Customer_Email ENABLE CONSTRAINT fk_customer_email;
ALTER TABLE Reservation ENABLE CONSTRAINT fk_reservation_customer;
ALTER TABLE Reservation ENABLE CONSTRAINT fk_reservation_equipment;
ALTER TABLE Maintenance ENABLE CONSTRAINT fk_maintenance_equipment;
ALTER TABLE Maintenance ENABLE CONSTRAINT fk_maintenance_staff;

EOF

