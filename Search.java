import java.sql.*;

public class Search {

    public static void search(String tableName, String condition) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                "d1yeung", "06145080")) {

            String sql = "SELECT * FROM " + tableName + " WHERE " + condition;
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();

                // Fetch and print results
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();
                boolean hasResults = false;

                while (rs.next()) {
                    hasResults = true;
                    for (int i = 1; i <= columnCount; i++) {
                        System.out.print(metaData.getColumnName(i) + ": " + rs.getString(i) + " | ");
                    }
                    System.out.println();
                }

                if (!hasResults) {
                    System.out.println("No results found for the given condition.");
                }

            } catch (SQLException e) {
                System.err.println("Query execution error: " + e.getMessage());
            }

        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
        }
    }
}
