#!/bin/sh
sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

-- 1. Create the sequences
CREATE SEQUENCE customer_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE phone_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE email_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE staff_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE equipment_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE equipment_type_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE rental_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE rental_history_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE overdue_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE payment_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE payment_history_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE reservation_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE maintenance_id_seq START WITH 1 INCREMENT BY 1;

-- 2. Create the triggers
CREATE OR REPLACE TRIGGER customer_id_trigger
BEFORE INSERT ON Customer
FOR EACH ROW
BEGIN
    :new.Customer_ID := customer_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER phone_id_trigger
BEFORE INSERT ON Customer_Phone
FOR EACH ROW
BEGIN
    :new.Phone_ID := phone_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER email_trigger
BEFORE INSERT ON Customer_Email
FOR EACH ROW
BEGIN
    :new.Email_ID := email_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER staff_id_trigger
BEFORE INSERT ON Staff
FOR EACH ROW
BEGIN
    :new.Staff_ID := staff_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER equipment_id_trigger
BEFORE INSERT ON Equipment
FOR EACH ROW
BEGIN
    :new.Equipment_ID := equipment_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER equipment_type_id_trigger
BEFORE INSERT ON Equipment_Type
FOR EACH ROW
BEGIN
    :new.Equipment_Type_ID := equipment_type_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER rental_id_trigger
BEFORE INSERT ON Rental
FOR EACH ROW
BEGIN
    :new.Rental_ID := rental_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER rental_history_id_trigger
BEFORE INSERT ON Rental_History
FOR EACH ROW
BEGIN
    :new.Rental_History_ID := rental_history_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER overdue_id_trigger
BEFORE INSERT ON Overdue
FOR EACH ROW
BEGIN
    :new.Overdue_ID := overdue_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER payment_id_trigger
BEFORE INSERT ON Payment
FOR EACH ROW
BEGIN
    :new.Payment_ID := payment_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER payment_history_id_trigger
BEFORE INSERT ON Payment_History
FOR EACH ROW
BEGIN
    :new.Payment_History_ID := payment_history_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER reservation_id_trigger
BEFORE INSERT ON Reservation
FOR EACH ROW
BEGIN
    :new.Reservation_ID := reservation_id_seq.NEXTVAL;
END;
/

CREATE OR REPLACE TRIGGER maintenance_id_trigger
BEFORE INSERT ON Maintenance
FOR EACH ROW
BEGIN
    :new.Maintenance_ID := maintenance_id_seq.NEXTVAL;
END;
/

EOF

