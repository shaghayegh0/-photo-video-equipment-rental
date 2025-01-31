import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet; 
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class CreateTables {

    public static void create() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            Statement stmt = conn.createStatement();

            // Array of SQL statements for creating tables
            String[] sqlStatements = {
                "CREATE TABLE Equipment_Type (" +
                "Equipment_Type_ID INT PRIMARY KEY, " +
                "Equipment_Type_Name VARCHAR(100) NOT NULL UNIQUE)",

                "CREATE TABLE Customer (" +
                "Customer_ID INT PRIMARY KEY, " +
                "Name VARCHAR(100) NOT NULL, " +
                "Address VARCHAR(255))",

                "CREATE TABLE Staff (" +
                "Staff_ID INT PRIMARY KEY, " +
                "Name VARCHAR(100) NOT NULL, " +
                "Position VARCHAR(50) NOT NULL)",
            
                "CREATE TABLE Equipment (" +
                "Equipment_ID INT PRIMARY KEY, " +
                "Name VARCHAR(100) NOT NULL, " +
                "Equipment_Type_ID INT NOT NULL, " +
                "Status VARCHAR(50) DEFAULT 'Available' CHECK (Status IN ('Available', 'Under Maintenance', 'Out of Service')), " +
                "Condition VARCHAR(100) NOT NULL, " +
                "Purchase_Date DATE NOT NULL, " +
                "Equipment_Price DECIMAL(10, 2) NOT NULL, " +
                "FOREIGN KEY (Equipment_Type_ID) REFERENCES Equipment_Type(Equipment_Type_ID))",
            
                "CREATE TABLE Customer_Phone (" +
                "Phone_ID INT PRIMARY KEY, " +
                "Customer_ID INT, " +
                "Customer_Phone_Number VARCHAR(20) NOT NULL UNIQUE, " +
                "CONSTRAINT fk_customer FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID))",
        
                "CREATE TABLE Customer_Email (" +
                "Email_ID INT PRIMARY KEY, " +
                "Customer_ID INT, " +
                "Customer_Email VARCHAR(100) NOT NULL UNIQUE, " +
                "CONSTRAINT fk_customer_email FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID))",
            
                "CREATE TABLE Rental (" +
                "Rental_ID INT PRIMARY KEY, " +
                "Staff_ID INT, " +
                "Customer_ID INT, " +
                "Equipment_ID INT, " +
                "Rental_Date DATE NOT NULL, " +
                "Estimated_Return_Date DATE NOT NULL, " +
                "Estimated_Duration_Days INT, " +
                "Rental_Price DECIMAL(10, 2) NOT NULL, " +
                "Status VARCHAR(50) DEFAULT 'Ongoing' CHECK (Status IN ('Ongoing', 'Completed', 'Overdue')), " +
                "FOREIGN KEY (Staff_ID) REFERENCES Staff(Staff_ID), " +
                "FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID), " +
                "FOREIGN KEY (Equipment_ID) REFERENCES Equipment(Equipment_ID))",
            
                "CREATE TABLE Rental_History (" +
                "Rental_History_ID INT PRIMARY KEY, " +
                "Staff_ID INT, " +
                "Customer_ID INT, " +
                "Equipment_ID INT, " +
                "Rental_Date DATE NOT NULL, " +
                "Duration INT, " +
                "Rental_Return DATE NOT NULL, " +
                "Price DECIMAL(10, 2))",
                
                
                "CREATE TABLE Overdue (" +
                "Overdue_ID INT PRIMARY KEY, " +
                "Rental_ID INT, " +
                "Overdue_Date DATE NOT NULL, " +
                "Amount DECIMAL(10, 2), " +
                "Payment_Method VARCHAR(50), " +
                "Status VARCHAR(50) DEFAULT 'Paid' CHECK (Status IN ('Declined', 'Paid')), " +
                "CONSTRAINT fk_overdue FOREIGN KEY (Rental_ID) REFERENCES Rental(Rental_ID))",
            
                "CREATE TABLE Payment (" +
                "Payment_ID INT PRIMARY KEY, " +
                "Rental_ID INT, " +
                "Payment_Date DATE NOT NULL, " +
                "Payment_Amount DECIMAL(10, 2) NOT NULL, " +
                "Payment_Method VARCHAR(50) NOT NULL, " +
                "Status VARCHAR(50) DEFAULT 'Pending' CHECK (Status IN ('Declined', 'Accepted', 'Pending')), " +
                "CONSTRAINT fk_payment FOREIGN KEY (Rental_ID) REFERENCES Rental(Rental_ID))",
            
                "CREATE TABLE Payment_History (" +
                "Payment_History_ID INT PRIMARY KEY, " +
                "Payment_ID INT, " +
                "Payment_Date DATE NOT NULL, " +
                "Amount DECIMAL(10, 2) NOT NULL, " +
                "Payment_Method VARCHAR(50) NOT NULL, " +
                "CONSTRAINT fk_payment_history FOREIGN KEY (Payment_ID) REFERENCES Payment(Payment_ID))",
            
                "CREATE TABLE Reservation (" +
                "Reservation_ID INT PRIMARY KEY, " +
                "Customer_ID INT, " +
                "Equipment_ID INT, " +
                "Reservation_Date DATE NOT NULL, " +
                "Start_Date DATE NOT NULL, " +
                "End_Date DATE NOT NULL, " +
                "Status VARCHAR(50) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Confirmed', 'Cancelled')), " +
                "FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID), " +
                "FOREIGN KEY (Equipment_ID) REFERENCES Equipment(Equipment_ID))",
            
                "CREATE TABLE Maintenance (" +
                "Maintenance_ID INT PRIMARY KEY, " +
                "Equipment_ID INT, " +
                "Maintenance_Date DATE NOT NULL, " +
                "Description VARCHAR(255) NOT NULL, " +
                "Maintenance_Cost DECIMAL(10, 2) NOT NULL, " +
                "Status VARCHAR(50) DEFAULT 'Scheduled' CHECK (Status IN ('Scheduled', 'In Progress', 'Completed')), " +
                "FOREIGN KEY (Equipment_ID) REFERENCES Equipment(Equipment_ID))",
            
                "CREATE TABLE Rental_Customer (" +
                "Rental_Customer_ID INT PRIMARY KEY, " +
                "Rental_ID INT, " +
                "Customer_ID INT, " +
                "CONSTRAINT fk_rental_customer FOREIGN KEY (Rental_ID) REFERENCES Rental(Rental_ID), " +
                "CONSTRAINT fk_customer_rental FOREIGN KEY (Customer_ID) REFERENCES Customer(Customer_ID))",
            
                "CREATE TABLE Payment_Rental_Customer (" +
                "Payment_Rental_Customer_ID INT PRIMARY KEY, " +
                "Payment_ID INT, " +
                "Rental_Customer_ID INT, " +
                "CONSTRAINT fk_payment_rental FOREIGN KEY (Payment_ID) REFERENCES Payment(Payment_ID), " +
                "CONSTRAINT fk_rental_customer_payment FOREIGN KEY (Rental_Customer_ID) REFERENCES Rental_Customer(Rental_Customer_ID))"
            };
                

            // Execute each SQL statement individually
            for (String sql : sqlStatements) {
                try {
                    stmt.executeUpdate(sql);
                } catch (Exception e) {
                    System.err.println("Error executing SQL: " + sql);
                    e.printStackTrace();
                }
            }




            // Display all tables with their structure and row counts
            System.out.println("\nFetching tables with columns...");

            try {
                // Fetch all table names
                ResultSet tablesRs = stmt.executeQuery("SELECT TABLE_NAME FROM USER_TABLES ORDER BY TABLE_NAME");
                while (tablesRs.next()) {
                    String tableName = tablesRs.getString("TABLE_NAME");
                    System.out.println("\nTable: " + tableName);

                    // Fetch columns for the table
                    try (PreparedStatement columnsStmt = conn.prepareStatement(
                            "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?")) {
                        columnsStmt.setString(1, tableName);
                        ResultSet columnsRs = columnsStmt.executeQuery();
                        System.out.println("Columns:");
                        while (columnsRs.next()) {
                            String columnName = columnsRs.getString("COLUMN_NAME");
                            System.out.println("  - " + columnName );
                        }
                        columnsRs.close();
                    }

                    // Fetch row count for the table
                    try (PreparedStatement rowCountStmt = conn.prepareStatement("SELECT COUNT(*) AS ROW_COUNT FROM " + tableName)) {
                        ResultSet rowCountRs = rowCountStmt.executeQuery();
                        if (rowCountRs.next()) {
                            int rowCount = rowCountRs.getInt("ROW_COUNT");
                            System.out.println("Row Count: " + rowCount);
                        }
                        rowCountRs.close();
                    }
                }
                tablesRs.close();
            } catch (SQLException e) {
                System.err.println("Error fetching table details: " + e.getMessage());
            }
            




            System.out.println("Tables created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}