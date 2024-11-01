#!/bin/sh
#export LD_LIBRARY_PATH=/usr/lib/oracle/12.1/client64/lib  # Uncomment if needed

sqlplus64 "d1yeung/06145080@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))" <<EOF

-- Drop Triggers
DROP TRIGGER customer_id_trigger;
DROP TRIGGER phone_id_trigger;
DROP TRIGGER email_trigger;
DROP TRIGGER staff_id_trigger;
DROP TRIGGER equipment_id_trigger;
DROP TRIGGER equipment_type_id_trigger;
DROP TRIGGER rental_id_trigger;
DROP TRIGGER rental_history_id_trigger;
DROP TRIGGER overdue_id_trigger;
DROP TRIGGER payment_id_trigger;
DROP TRIGGER payment_history_id_trigger;
DROP TRIGGER reservation_id_trigger;
DROP TRIGGER maintenance_id_trigger;

-- Drop Sequences
DROP SEQUENCE customer_id_seq;
DROP SEQUENCE phone_id_seq;
DROP SEQUENCE email_id_seq;
DROP SEQUENCE staff_id_seq;
DROP SEQUENCE equipment_id_seq;
DROP SEQUENCE equipment_type_id_seq;
DROP SEQUENCE rental_id_seq;
DROP SEQUENCE rental_history_id_seq;
DROP SEQUENCE overdue_id_seq;
DROP SEQUENCE payment_id_seq;
DROP SEQUENCE payment_history_id_seq;
DROP SEQUENCE reservation_id_seq;
DROP SEQUENCE maintenance_id_seq;

EOF
