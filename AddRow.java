import java.sql.*;

public class AddRow {

    public static void add(String tableName, String columnName, String value) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            String sql = "INSERT INTO " + tableName + " (" + columnName + ") VALUES (?)";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, value);
                int rowsInserted = stmt.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Row added successfully!");
                } else {
                    System.out.println("Failed to add the row.");
                }
            }

        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
        }
    }
}
