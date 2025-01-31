import java.sql.*;

public class QueryExecutor {

    private static final String URL = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))";
    private static final String USER = "d1yeung";
    private static final String PASSWORD = "06145080";

    public static void query() {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("Connected to the database!");

            executeQuery1(conn);
            executeQuery2(conn);
            executeQuery3(conn);
            executeQuery4(conn);
            executeQuery5(conn);
            executeQuery6(conn);
            executeQuery7(conn);
            executeQuery8(conn);
            executeQuery9(conn);
            executeQuery10(conn);
            executeQuery11(conn);
            executeQuery12(conn);
            executeQuery13(conn);
            executeQuery14(conn);
            executeQuery15(conn);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 1. List all customers with distinct names
    private static void executeQuery1(Connection conn) throws SQLException {
        String sql = "SELECT DISTINCT Name FROM Customer";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("1. Distinct Customer Names:");
            while (rs.next()) {
                System.out.println(rs.getString("Name"));
            }
        }
    }

    // 2. Select all equipment available for rent sorted by rental price per day (descending)
    private static void executeQuery2(Connection conn) throws SQLException {
        String sql = "SELECT Name, EQUIPMENT_PRICE, Condition FROM Equipment WHERE STATUS = 'Available' ORDER BY EQUIPMENT_PRICE DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("2. Available Equipment (Sorted by Price):");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Price: " + rs.getDouble("EQUIPMENT_PRICE") + ", Condition: " + rs.getString("Condition"));
            }
        }
    }

    // 3. Retrieve all overdue payments with status 'Paid'
    private static void executeQuery3(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Overdue WHERE Status = 'Paid'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("3. Overdue Payments (Paid):");
            while (rs.next()) {
                System.out.println("Overdue_ID: " + rs.getInt("Overdue_ID") + ", Amount: " + rs.getDouble("Amount") + ", Status: " + rs.getString("Status"));
            }
        }
    }

    // 4. Count the number of rentals per customer
    private static void executeQuery4(Connection conn) throws SQLException {
        String sql = "SELECT c.Name, COUNT(r.Rental_ID) AS Rental_Count FROM Customer c JOIN Rental r ON c.Customer_ID = r.Customer_ID GROUP BY c.Name ORDER BY Rental_Count DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("4. Rentals Per Customer:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Rental Count: " + rs.getInt("Rental_Count"));
            }
        }
    }

    // 5. Show the top 3 customers with the most equipment reservations
    private static void executeQuery5(Connection conn) throws SQLException {
        String sql = "SELECT * FROM (" +
                 "SELECT c.Name, COUNT(res.Reservation_ID) AS Total_Reservations " +
                 "FROM Customer c " +
                 "JOIN Reservation res ON c.Customer_ID = res.Customer_ID " +
                 "GROUP BY c.Name " +
                 "ORDER BY Total_Reservations DESC" +
                 ") WHERE ROWNUM <= 3";        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("5. Top 3 Customers with Most Reservations:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Reservations: " + rs.getInt("Total_Reservations"));
            }
        }
    }

    // 6. List all staff members and their positions
    private static void executeQuery6(Connection conn) throws SQLException {
        String sql = "SELECT Name, Position FROM Staff ORDER BY Name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("6. Staff Members and Positions:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Position: " + rs.getString("Position"));
            }
        }
    }

    // 7. Select all equipment under maintenance
    private static void executeQuery7(Connection conn) throws SQLException {
        String sql = "SELECT eq.Name, m.Maintenance_Cost FROM Equipment eq JOIN Maintenance m ON eq.Equipment_ID = m.Equipment_ID WHERE m.Status = 'Processing' ORDER BY m.Maintenance_Cost DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("7. Equipment Under Maintenance:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Cost: " + rs.getDouble("Maintenance_Cost"));
            }
        }
    }

    // 8. List distinct equipment types
    private static void executeQuery8(Connection conn) throws SQLException {
        String sql = "SELECT DISTINCT Equipment_Type_Name FROM Equipment_Type";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("8. Distinct Equipment Types:");
            while (rs.next()) {
                System.out.println(rs.getString("Equipment_Type_Name"));
            }
        }
    }

    // 9. Calculate total rental revenue for each staff member
    private static void executeQuery9(Connection conn) throws SQLException {
        String sql = "SELECT s.Name, SUM(r.RENTAL_PRICE) AS Total_Revenue FROM Staff s JOIN Rental r ON s.Staff_ID = r.Staff_ID GROUP BY s.Name ORDER BY Total_Revenue DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("9. Total Rental Revenue Per Staff:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Revenue: " + rs.getDouble("Total_Revenue"));
            }
        }
    }

    // 10. Retrieve payment records with declined payment methods
    private static void executeQuery10(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Payment WHERE Status = 'Declined'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("10. Declined Payments:");
            while (rs.next()) {
                System.out.println("Payment_ID: " + rs.getInt("Payment_ID") + ", Status: " + rs.getString("Status"));
            }
        }
    }

    // 11. Count the total number of overdue payments grouped by customer
    private static void executeQuery11(Connection conn) throws SQLException {
        String sql = "SELECT c.Name AS Customer_Name, COUNT(o.Overdue_ID) AS Total_Overdue FROM Rental_Customer rc JOIN Overdue o ON rc.Rental_ID = o.Rental_ID JOIN Customer c ON rc.Customer_ID = c.Customer_ID GROUP BY c.Name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("11. Total Overdue Payments Per Customer:");
            while (rs.next()) {
                System.out.println("Customer: " + rs.getString("Customer_Name") + ", Overdue: " + rs.getInt("Total_Overdue"));
            }
        }
    }

    // 12. List all equipment purchased in 2023
    private static void executeQuery12(Connection conn) throws SQLException {
        String sql = "SELECT Name, Purchase_Date, Equipment_Price FROM Equipment WHERE EXTRACT(YEAR FROM Purchase_Date) = 2023 ORDER BY Purchase_Date DESC";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("12. Equipment Purchased in 2023:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name") + ", Date: " + rs.getDate("Purchase_Date") + ", Price: " + rs.getDouble("Equipment_Price"));
            }
        }
    }

    // 13. Show average maintenance cost per equipment type
    private static void executeQuery13(Connection conn) throws SQLException {
        String sql = "SELECT et.Equipment_Type_Name, AVG(m.Maintenance_Cost) AS Avg_Maintenance_Cost FROM Equipment_Type et JOIN Equipment eq ON et.Equipment_Type_ID = eq.Equipment_Type_ID JOIN Maintenance m ON eq.Equipment_ID = m.Equipment_ID GROUP BY et.Equipment_Type_Name";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("13. Average Maintenance Cost Per Equipment Type:");
            while (rs.next()) {
                System.out.println("Type: " + rs.getString("Equipment_Type_Name") + ", Avg Cost: " + rs.getDouble("Avg_Maintenance_Cost"));
            }
        }
    }

    // 14. List all rental history for a specific customer (replace 'John Doe' with any customer name)
    private static void executeQuery14(Connection conn) throws SQLException {
        String sql = "SELECT rh.Rental_History_ID, rh.Equipment_ID, rh.Duration FROM Rental_History rh JOIN Customer c ON rh.Customer_ID = c.Customer_ID WHERE c.Name = 'John Doe'";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("14. Rental History for John Doe:");
            while (rs.next()) {
                System.out.println("Rental_History_ID: " + rs.getInt("Rental_History_ID") + ", Equipment_ID: " + rs.getInt("Equipment_ID") + ", Duration: " + rs.getDate("Duration") );
            }
        }
    }



    // 15. Show the equipment that has never been rented
    private static void executeQuery15(Connection conn) throws SQLException {
        String sql = "SELECT eq.Name FROM Equipment eq " +
                    "LEFT JOIN Rental r ON eq.Equipment_ID = r.Equipment_ID " +
                    "WHERE r.Rental_ID IS NULL";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
            System.out.println("15. Show the equipment that has never been rented:");
            while (rs.next()) {
                System.out.println("Name: " + rs.getString("Name"));
            }
        }
    }

}
