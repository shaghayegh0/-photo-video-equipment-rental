import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DeleteTables {

    public static void delete() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            Statement stmt = conn.createStatement();

            // Array of SQL statements for dropping tables and sequences
            String[] sqlStatements = {
                // Drop tables
                "DROP TABLE Customer CASCADE CONSTRAINTS",
                "DROP TABLE Customer_Phone CASCADE CONSTRAINTS",
                "DROP TABLE Customer_Email CASCADE CONSTRAINTS",
                "DROP TABLE Staff CASCADE CONSTRAINTS",
                "DROP TABLE Equipment CASCADE CONSTRAINTS",
                "DROP TABLE Equipment_Type CASCADE CONSTRAINTS",
                "DROP TABLE Rental CASCADE CONSTRAINTS",
                "DROP TABLE Rental_History CASCADE CONSTRAINTS",
                "DROP TABLE Overdue CASCADE CONSTRAINTS",
                "DROP TABLE Payment CASCADE CONSTRAINTS",
                "DROP TABLE Payment_History CASCADE CONSTRAINTS",
                "DROP TABLE Reservation CASCADE CONSTRAINTS",
                "DROP TABLE Maintenance CASCADE CONSTRAINTS",
                "DROP TABLE Rental_Customer CASCADE CONSTRAINTS",
                "DROP TABLE Payment_Rental_Customer CASCADE CONSTRAINTS"
            };

            // Execute each SQL statement individually
            for (String sql : sqlStatements) {
                try {
                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    System.err.println("Error executing: " + sql);
                    e.printStackTrace();
                }
            }

            System.out.println("Tables and sequences deleted successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
