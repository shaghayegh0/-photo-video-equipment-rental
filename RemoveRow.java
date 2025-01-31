import java.sql.*;
import javax.swing.*;

public class RemoveRow {

    public static void remove(String tableName, String searchColumn, String searchValue) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            // SQL statement for deleting a row
            String sql = "DELETE FROM " + tableName + " WHERE " + searchColumn + " = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Set the search value in the query
                stmt.setString(1, searchValue);

                // Execute the delete query
                int rowsDeleted = stmt.executeUpdate();

                // Check if the delete was successful
                if (rowsDeleted > 0) {
                    JOptionPane.showMessageDialog(null, "Row removed successfully!", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "No matching row found to remove.", "Remove Row", JOptionPane.ERROR_MESSAGE);
                }

            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error executing delete: " + e.getMessage(), "Remove Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Query execution error: " + e.getMessage());
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database connection error: " + e.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
