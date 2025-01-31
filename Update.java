import java.sql.*;
import javax.swing.*;

public class Update {

    public static void update(String tableName, String searchColumn, String searchValue, String updateColumn, String newValue) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            // SQL statement for updating a row
            String sql = "UPDATE " + tableName + " SET " + updateColumn + " = ? WHERE " + searchColumn + " = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the new value and search value in the query
                stmt.setString(1, newValue);
                stmt.setString(2, searchValue);

                // Execute the update query
                int rowsUpdated = stmt.executeUpdate();

                // Check if the update was successful
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Row updated successfully!", "Update Row", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No matching row found to update.", "Update Row", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error executing update: " + e.getMessage(), "Update Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Query execution error: " + e.getMessage());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
