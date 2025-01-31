import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet; 
import java.sql.ResultSetMetaData; 



public class PopulateTables {

    public static void populate() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            Statement stmt = conn.createStatement();

            // Array of SQL statements for populating tables
            String[] sqlStatements = {
                // Insert data into Equipment_Type
                "INSERT INTO Equipment_Type (Equipment_Type_ID, Equipment_Type_Name) VALUES (1, 'Camera')",
                "INSERT INTO Equipment_Type (Equipment_Type_ID, Equipment_Type_Name) VALUES (2, 'Tripod')",
            
                // Insert data into Customer (no email or phone here)
                "INSERT INTO Customer (Customer_ID, Name, Address) VALUES (1, 'John Doe', '123 Main St, Toronto, ON')",
                "INSERT INTO Customer (Customer_ID, Name, Address) VALUES (2, 'Jane Smith', '456 Oak Ave, Toronto, ON')",
            
                // Insert data into Staff
                "INSERT INTO Staff (Staff_ID, Name, Position) VALUES (1, 'Alice Johnson', 'Manager')",
                "INSERT INTO Staff (Staff_ID, Name, Position) VALUES (2, 'Bob Brown', 'Clerk')",
            
                // Insert data into Equipment
                "INSERT INTO Equipment (Equipment_ID, Name, Equipment_Type_ID, Status, Condition, Purchase_Date, Equipment_Price) " +
                "VALUES (1, 'Canon EOS R5', 1, 'Available', 'New', TO_DATE('2023-01-15', 'YYYY-MM-DD'), 3999.99)",
                "INSERT INTO Equipment (Equipment_ID, Name, Equipment_Type_ID, Status, Condition, Purchase_Date, Equipment_Price) " +
                "VALUES (2, 'Manfrotto Tripod', 2, 'Available', 'New', TO_DATE('2023-02-20', 'YYYY-MM-DD'), 249.99)",
            
                // Insert data into Customer_Phone
                "INSERT INTO Customer_Phone (Phone_ID, Customer_ID, Customer_Phone_Number) VALUES (1, 1, '123-456-7890')",
                "INSERT INTO Customer_Phone (Phone_ID, Customer_ID, Customer_Phone_Number) VALUES (2, 2, '987-654-3210')",
            
                // Insert data into Customer_Email
                "INSERT INTO Customer_Email (Email_ID, Customer_ID, Customer_Email) VALUES (1, 1, 'john.doe@example.com')",
                "INSERT INTO Customer_Email (Email_ID, Customer_ID, Customer_Email) VALUES (2, 2, 'jane.smith@example.com')",
            
                // Insert data into Rental
                "INSERT INTO Rental (Rental_ID, Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Estimated_Return_Date, Estimated_Duration_Days, Rental_Price, Status) " +
                "VALUES (1, 1, 1, 1, TO_DATE('2023-05-10', 'YYYY-MM-DD'), TO_DATE('2023-05-12', 'YYYY-MM-DD'), 2, 100.00, 'Ongoing')",
                "INSERT INTO Rental (Rental_ID, Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Estimated_Return_Date, Estimated_Duration_Days, Rental_Price, Status) " +
                "VALUES (2, 2, 2, 2, TO_DATE('2023-06-20', 'YYYY-MM-DD'), TO_DATE('2023-06-23', 'YYYY-MM-DD'), 3, 150.00, 'Ongoing')",
            
                // Insert data into Rental_History
                "INSERT INTO Rental_History (Rental_History_ID, Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Duration, Rental_Return, Price) " +
                "VALUES (1, 101, 201, 301, TO_DATE('2023-05-10', 'YYYY-MM-DD'), 2, TO_DATE('2023-05-12', 'YYYY-MM-DD'), 100.00)",

                "INSERT INTO Rental_History (Rental_History_ID, Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Duration, Rental_Return, Price) " +
                "VALUES (2, 102, 202, 302, TO_DATE('2023-06-20', 'YYYY-MM-DD'), 3, TO_DATE('2023-06-23', 'YYYY-MM-DD'), 150.00)",

                "INSERT INTO Rental_History (Rental_History_ID, Staff_ID, Customer_ID, Equipment_ID, Rental_Date, Duration, Rental_Return, Price) " +
                "VALUES (3, 103, 203, 303, TO_DATE('2023-07-01', 'YYYY-MM-DD'), 5, TO_DATE('2023-07-06', 'YYYY-MM-DD'), 250.00)",

                // Insert data into Overdue
                "INSERT INTO Overdue (Overdue_ID, Rental_ID, Overdue_Date, Amount, Payment_Method, Status) " +
                "VALUES (1, 1, TO_DATE('2023-07-01', 'YYYY-MM-DD'), 50.00, 'Credit Card', 'Paid')",
            
                // Insert data into Payment
                "INSERT INTO Payment (Payment_ID, Rental_ID, Payment_Date, Payment_Amount, Payment_Method) " +
                "VALUES (1, 1, TO_DATE('2023-05-12', 'YYYY-MM-DD'), 100.00, 'Credit Card')",
                "INSERT INTO Payment (Payment_ID, Rental_ID, Payment_Date, Payment_Amount, Payment_Method) " +
                "VALUES (2, 2, TO_DATE('2023-06-23', 'YYYY-MM-DD'), 150.00, 'Credit Card')",
            
                // Insert data into Payment_History
                "INSERT INTO Payment_History (Payment_History_ID, Payment_ID, Payment_Date, Amount, Payment_Method) " +
                "VALUES (1, 1, TO_DATE('2023-05-12', 'YYYY-MM-DD'), 100.00, 'Credit Card')",
                "INSERT INTO Payment_History (Payment_History_ID, Payment_ID, Payment_Date, Amount, Payment_Method) " +
                "VALUES (2, 2, TO_DATE('2023-06-23', 'YYYY-MM-DD'), 150.00, 'Credit Card')",
            
                // Insert data into Reservation
                "INSERT INTO Reservation (Reservation_ID, Customer_ID, Equipment_ID, Reservation_Date, Start_Date, End_Date, Status) " +
                "VALUES (1, 1, 1, TO_DATE('2023-05-01', 'YYYY-MM-DD'), TO_DATE('2023-05-10', 'YYYY-MM-DD'), TO_DATE('2023-05-12', 'YYYY-MM-DD'), 'Pending')",
                "INSERT INTO Reservation (Reservation_ID, Customer_ID, Equipment_ID, Reservation_Date, Start_Date, End_Date, Status) " +
                "VALUES (2, 2, 2, TO_DATE('2023-06-10', 'YYYY-MM-DD'), TO_DATE('2023-06-20', 'YYYY-MM-DD'), TO_DATE('2023-06-23', 'YYYY-MM-DD'), 'Pending')",
            
                // Insert data into Maintenance
                "INSERT INTO Maintenance (Maintenance_ID, Equipment_ID, Maintenance_Date, Description, Maintenance_Cost, Status) " +
                "VALUES (1, 1, TO_DATE('2023-07-10', 'YYYY-MM-DD'), 'General Maintenance', 50.00, 'Scheduled')",
                "INSERT INTO Maintenance (Maintenance_ID, Equipment_ID, Maintenance_Date, Description, Maintenance_Cost, Status) " +
                "VALUES (2, 2, TO_DATE('2023-08-01', 'YYYY-MM-DD'), 'Repair', 30.00, 'Scheduled')",
            
                // Insert data into Rental_Customer
                "INSERT INTO Rental_Customer (Rental_Customer_ID, Rental_ID, Customer_ID) VALUES (1, 1, 1)",
                "INSERT INTO Rental_Customer (Rental_Customer_ID, Rental_ID, Customer_ID) VALUES (2, 2, 2)",
            
                // Insert data into Payment_Rental_Customer
                "INSERT INTO Payment_Rental_Customer (Payment_Rental_Customer_ID, Payment_ID, Rental_Customer_ID) VALUES (1, 1, 1)",
                "INSERT INTO Payment_Rental_Customer (Payment_Rental_Customer_ID, Payment_ID, Rental_Customer_ID) VALUES (2, 2, 2)"
            };
            

            // Execute each statement
            for (String sql : sqlStatements) {
                stmt.executeUpdate(sql);
            }

            
            
            // Display all tables with their structure and row counts
            System.out.println("\nFetching tables with columns and rows...");

            try {
                // Fetch all table names
                ResultSet tablesRs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES ORDER BY TABLE_NAME");
                while (tablesRs.next()) {
                    String tableName = tablesRs.getString("TABLE_NAME");
                    System.out.println("\nTable: " + tableName);

                    // Fetch columns and rows for the table
                    try (PreparedStatement fetchRowsStmt = conn.prepareStatement("SELECT * FROM " + tableName)) {
                        ResultSet rowsRs = fetchRowsStmt.executeQuery();
                        ResultSetMetaData metaData = rowsRs.getMetaData();
                        int columnCount = metaData.getColumnCount();

                        // Print column names as table header
                        for (int i = 1; i <= columnCount; i++) {
                            String columnName = metaData.getColumnName(i);
                            System.out.print(columnName + "\t"); // Tab-separated columns
                        }
                        System.out.println(); // Newline after header

                        // Print rows
                        while (rowsRs.next()) {
                            for (int i = 1; i <= columnCount; i++) {
                                String columnValue = rowsRs.getString(i);
                                System.out.print(columnValue + "\t"); // Tab-separated values
                            }
                            System.out.println(); // Newline after each row
                        }
                        rowsRs.close();
                    }



                    
                }
                tablesRs.close();
            } catch (SQLException e) {
                System.err.println("Error fetching table details: " + e.getMessage());
            }



            
            System.out.println("Tables populated successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        populate();
    }
}
