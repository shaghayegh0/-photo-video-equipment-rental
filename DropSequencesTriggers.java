import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DropSequencesTriggers {

    public static void drop() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            Statement stmt = conn.createStatement();

            // Drop Triggers
            String[] triggers = {
                    "DROP TRIGGER customer_id_trigger",
                    "DROP TRIGGER phone_id_trigger",
                    "DROP TRIGGER email_trigger",
                    "DROP TRIGGER staff_id_trigger",
                    "DROP TRIGGER equipment_id_trigger",
                    "DROP TRIGGER equipment_type_id_trigger",
                    "DROP TRIGGER rental_id_trigger",
                    "DROP TRIGGER rental_history_id_trigger",
                    "DROP TRIGGER overdue_id_trigger",
                    "DROP TRIGGER payment_id_trigger",
                    "DROP TRIGGER payment_history_id_trigger",
                    "DROP TRIGGER reservation_id_trigger",
                    "DROP TRIGGER maintenance_id_trigger"
            };

            for (String trigger : triggers) {
                try {
                    stmt.executeUpdate(trigger);
                    System.out.println(trigger + " executed successfully.");
                } catch (Exception e) {
                    System.err.println("Error dropping trigger: " + trigger + ". " + e.getMessage());
                }
            }

            // Drop Sequences
            String[] sequences = {
                    "DROP SEQUENCE customer_id_seq",
                    "DROP SEQUENCE phone_id_seq",
                    "DROP SEQUENCE email_id_seq",
                    "DROP SEQUENCE staff_id_seq",
                    "DROP SEQUENCE equipment_id_seq",
                    "DROP SEQUENCE equipment_type_id_seq",
                    "DROP SEQUENCE rental_id_seq",
                    "DROP SEQUENCE rental_history_id_seq",
                    "DROP SEQUENCE overdue_id_seq",
                    "DROP SEQUENCE payment_id_seq",
                    "DROP SEQUENCE payment_history_id_seq",
                    "DROP SEQUENCE reservation_id_seq",
                    "DROP SEQUENCE maintenance_id_seq"
            };

            for (String sequence : sequences) {
                try {
                    stmt.executeUpdate(sequence);
                    System.out.println(sequence + " executed successfully.");
                } catch (Exception e) {
                    System.err.println("Error dropping sequence: " + sequence + ". " + e.getMessage());
                }
            }

            System.out.println("Triggers and sequences dropped successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        drop();
    }
}
