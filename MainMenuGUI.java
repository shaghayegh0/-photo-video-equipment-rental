import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSetMetaData;



public class MainMenuGUI {

    public static void main(String[] args) {
        // Create the main frame
        JFrame frame = new JFrame("Camera Equipment Rental");
        frame.setSize(800, 600); // Adjusted size for better layout
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout()); // Use BorderLayout for better control

        // Create a panel for the menu at the top
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10)); 
        frame.add(panel, BorderLayout.CENTER); // Place it at the top of the frame


        // Create a label for the header
        JLabel header = new JLabel("Main Menu", SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 16));
        panel.add(header);

        // Buttons for each operation
        JButton createTablesButton = new JButton("1) Create Tables");
        JButton populateTablesButton = new JButton("2) Populate Tables");
        JButton queryTablesButton = new JButton("3) Query Tables");
        JButton deleteTablesButton = new JButton("4) Delete Tables");
        JButton deleteSequencesTriggersButton = new JButton("5) Delete Sequences and Triggers");
        JButton createSequencesTriggersButton = new JButton("6) Create Sequences and Triggers");
       
        JButton addRowButton = new JButton("7) Add a new row");
        JButton removeRowButton = new JButton("8) Remove a row");
        JButton updateRowButton = new JButton("9) Update a row");
        JButton searchButton = new JButton("10) Search");

        JButton forceStopOracleDBButton = new JButton("X) Stop Oracle DB");
        JButton exitButton = new JButton("E) Exit");

        // Add buttons to the panel
        panel.add(createTablesButton);
        panel.add(populateTablesButton);
        panel.add(queryTablesButton);
        panel.add(deleteTablesButton);
        panel.add(deleteSequencesTriggersButton);
        panel.add(createSequencesTriggersButton);
        
        panel.add(addRowButton);
        panel.add(removeRowButton);
        panel.add(updateRowButton);
        panel.add(searchButton);

        panel.add(forceStopOracleDBButton);
        panel.add(exitButton);

        // Result area
        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(550, frame.getHeight()));
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Redirect System.out and System.err to the JTextArea
        PrintStream printStream = new PrintStream(new TextAreaOutputStream(resultArea));
        System.setOut(printStream);
        System.setErr(printStream);

        // Action listeners for buttons
        ActionListener buttonListener = e -> {
            String command = e.getActionCommand();
            switch (command) {
                case "1) Create Tables":
                    System.out.println("Create Tables selected.");
                    CreateTables.create();
                    break;
                case "2) Populate Tables":
                    System.out.println("Populate Tables selected.");
                    PopulateTables.populate();
                    break;
                case "3) Query Tables":
                    System.out.println("Query Tables selected.");
                    QueryExecutor.query();
                    break;
                case "4) Delete Tables":
                    System.out.println("Delete Tables selected.");
                    DeleteTables.delete();
                    break;
                case "5) Delete Sequences and Triggers":
                    System.out.println("Delete Sequences and Triggers selected.");
                    DropSequencesTriggers.drop();
                    break;
                case "6) Create Sequences and Triggers":
                    System.out.println("Create Sequences and Triggers selected.");
                    CreateSequencesTriggers.create();
                    break;


                    

                case "7) Add a new row":
                    System.out.println("Add a new row selected.");

                    // Step 1: Ask user which table they want to update
                    String[] tables = {"Equipment_Type", "Customer", "Staff", "Rental", "Overdue", "Payment", "Reservation", "Maintenance"};
                    String selectedTable = (String) JOptionPane.showInputDialog(
                            null,
                            "Select the table to update:",
                            "Table Selection",
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            tables,
                            tables[0]
                    );

                    if (selectedTable == null || selectedTable.isEmpty()) {
                        System.out.println("Table selection canceled or empty.");
                        break;
                    }
                    System.out.println("Selected Table: " + selectedTable);

                    try (Connection conn = DriverManager.getConnection(
                            "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                            "d1yeung", "06145080")) {

                        // Step 2: Fetch column information for the selected table
                        PreparedStatement columnsStmt = conn.prepareStatement(
                                "SELECT COLUMN_NAME, DATA_TYPE FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?");
                        columnsStmt.setString(1, selectedTable.toUpperCase()); // Ensure table name is in uppercase
                        ResultSet columnsRs = columnsStmt.executeQuery();

                        StringBuilder columnsBuilder = new StringBuilder();
                        StringBuilder valuesBuilder = new StringBuilder();
                        Object[] userInputs = new Object[50];
                        int columnIndex = 0;

                        while (columnsRs.next()) {
                            String columnName = columnsRs.getString("COLUMN_NAME");
                            String dataType = columnsRs.getString("DATA_TYPE");

                            // Ask the user for each column's value
                            String userInput = JOptionPane.showInputDialog(
                                    null,
                                    "Enter value for " + columnName + " (" + dataType + "):",
                                    "Input",
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (userInput == null || userInput.isEmpty()) {
                                System.out.println("Input for column " + columnName + " canceled or empty. Skipping row addition.");
                                break;
                            }

                            columnsBuilder.append(columnName).append(", ");
                            valuesBuilder.append("?, ");

                            // Convert input to appropriate type
                            if (dataType.equalsIgnoreCase("NUMBER")) {
                                userInputs[columnIndex] = Double.parseDouble(userInput);
                            } else if (dataType.equalsIgnoreCase("DATE")) {
                                userInputs[columnIndex] = java.sql.Date.valueOf(userInput); // Format: YYYY-MM-DD
                            } else {
                                userInputs[columnIndex] = userInput; // Treat as String
                            }
                            columnIndex++;
                        }
                        columnsRs.close();

                        // Remove trailing commas
                        columnsBuilder.setLength(columnsBuilder.length() - 2);
                        valuesBuilder.setLength(valuesBuilder.length() - 2);

                        // Step 3: Add the row to the table
                        String sql = "INSERT INTO " + selectedTable + " (" + columnsBuilder + ") VALUES (" + valuesBuilder + ")";
                        PreparedStatement insertStmt = conn.prepareStatement(sql);

                        for (int i = 0; i < columnIndex; i++) {
                            insertStmt.setObject(i + 1, userInputs[i]);
                        }

                        int rowsInserted = insertStmt.executeUpdate();
                        if (rowsInserted > 0) {
                            System.out.println("Row added successfully to table " + selectedTable + ".");
                        } else {
                            System.out.println("Failed to add the row.");
                        }

                    } catch (SQLException sqlEx) {
                        System.err.println("Database error: " + sqlEx.getMessage());
                    } catch (Exception ex) {
                        System.err.println("Error: " + ex.getMessage());
                    }
                    break;






                    case "8) Remove a row":
                        // Step 1: Ask user which table to remove from
                        String[] tables_remove = {"Equipment_Type", "Customer", "Staff", "Rental", "Overdue", "Payment", "Reservation", "Maintenance"};
                        String selectedTable_remove = (String) JOptionPane.showInputDialog(
                                null,
                                "Select the table to remove from:",
                                "Table Selection",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                tables_remove,
                                tables_remove[0]
                        );

                        if (selectedTable_remove == null || selectedTable_remove.isEmpty()) {
                            System.out.println("Table selection canceled or empty.");
                            break;
                        }
                        System.out.println("Selected Table: " + selectedTable_remove);

                        try (Connection conn = DriverManager.getConnection(
                                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                                "d1yeung", "06145080")) {

                            // Step 2: Fetch column names for the selected table
                            PreparedStatement columnsStmt = conn.prepareStatement(
                                    "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?");
                            columnsStmt.setString(1, selectedTable_remove.toUpperCase());
                            ResultSet columnsRs = columnsStmt.executeQuery();

                            java.util.List<String> columnsList = new java.util.ArrayList<>();
                            while (columnsRs.next()) {
                                columnsList.add(columnsRs.getString("COLUMN_NAME"));
                            }
                            columnsRs.close();

                            if (columnsList.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "No columns found for the selected table.", "Remove Error", JOptionPane.ERROR_MESSAGE);
                                break;
                            }

                            String[] columns_remove = columnsList.toArray(new String[0]);

                            // Step 3: Ask user to select a column for identifying the row
                            String selectedColumn_remove = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Select the column to identify the row to remove:",
                                    "Column Selection",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    columns_remove,
                                    columns_remove[0]
                            );

                            if (selectedColumn_remove == null || selectedColumn_remove.isEmpty()) {
                                System.out.println("Column selection canceled or empty.");
                                break;
                            }

                            // Step 4: Ask for the value of the identifying column
                            String inputValue_remove = JOptionPane.showInputDialog(
                                    null,
                                    "Enter the value for column " + selectedColumn_remove + " to identify the row to remove:",
                                    "Input Value",
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (inputValue_remove == null || inputValue_remove.isEmpty()) {
                                System.out.println("Search value canceled or empty.");
                                break;
                            }

                            // Check if row exists
                            String condition = selectedColumn_remove + " = ?";
                            String selectSql = "SELECT * FROM " + selectedTable_remove + " WHERE " + condition;

                            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                                selectStmt.setString(1, inputValue_remove);
                                ResultSet rs = selectStmt.executeQuery();

                                if (!rs.next()) {
                                    JOptionPane.showMessageDialog(null, "No matching row found to remove.", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }

                                // Display the matching row
                                ResultSetMetaData metaData = rs.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                StringBuilder rowDetails = new StringBuilder("Matching Row:\n");
                                for (int i = 1; i <= columnCount; i++) {
                                    rowDetails.append(metaData.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                                }
                                JOptionPane.showMessageDialog(null, rowDetails.toString(), "Row Details", JOptionPane.INFORMATION_MESSAGE);

                                // Step 5: Confirm deletion
                                int confirm = JOptionPane.showConfirmDialog(
                                        null,
                                        "Are you sure you want to remove this row?",
                                        "Confirm Remove",
                                        JOptionPane.YES_NO_OPTION
                                );

                                if (confirm == JOptionPane.NO_OPTION) {
                                    System.out.println("Row removal canceled.");
                                    break;
                                }

                                // Step 6: Delete the row
                                String deleteSql = "DELETE FROM " + selectedTable_remove + " WHERE " + condition;

                                try (PreparedStatement deleteStmt = conn.prepareStatement(deleteSql)) {
                                    deleteStmt.setString(1, inputValue_remove);

                                    int rowsDeleted = deleteStmt.executeUpdate();
                                    if (rowsDeleted > 0) {
                                        JOptionPane.showMessageDialog(null, "Row removed successfully!", "Remove Row", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Failed to remove the row.", "Remove Row", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                            } catch (SQLException er) {
                                System.err.println("Query execution error: " + er.getMessage());
                            }

                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(null, "Database connection error: " + ex.getMessage(), "Connection Error", JOptionPane.ERROR_MESSAGE);
                            System.err.println("Database connection error: " + ex.getMessage());
                        }
                    break;







                    case "9) Update a row":
                        // Step 1: Ask user which table they want to update
                        String[] tables_update = {"Equipment_Type", "Customer", "Staff", "Rental", "Overdue", "Payment", "Reservation", "Maintenance"};
                        String selectedTable_update = (String) JOptionPane.showInputDialog(
                                null,
                                "Select the table to update:",
                                "Table Selection",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                tables_update,
                                tables_update[0]
                        );

                        if (selectedTable_update == null || selectedTable_update.isEmpty()) {
                            System.out.println("Table selection canceled or empty.");
                            break;
                        }
                        System.out.println("Selected Table: " + selectedTable_update);

                        try (Connection conn = DriverManager.getConnection(
                                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                                "d1yeung", "06145080")) {

                            // Step 2: Fetch column names for the selected table
                            PreparedStatement columnsStmt = conn.prepareStatement(
                                    "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?");
                            columnsStmt.setString(1, selectedTable_update.toUpperCase());
                            ResultSet columnsRs = columnsStmt.executeQuery();

                            java.util.List<String> columnsList = new java.util.ArrayList<>();
                            while (columnsRs.next()) {
                                columnsList.add(columnsRs.getString("COLUMN_NAME"));
                            }
                            columnsRs.close();

                            if (columnsList.isEmpty()) {
                                System.out.println("No columns found for the selected table.");
                                break;
                            }

                            String[] columns_update = columnsList.toArray(new String[0]);

                            // Step 3: Select column for identifying the row
                            String selectedColumn_search = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Select the column to identify the row:",
                                    "Column Selection",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    columns_update,
                                    columns_update[0]
                            );

                            if (selectedColumn_search == null || selectedColumn_search.isEmpty()) {
                                System.out.println("Column selection canceled or empty.");
                                break;
                            }

                            // Step 4: Enter value to identify the row
                            String inputValue_search = JOptionPane.showInputDialog(
                                    null,
                                    "Enter the value for column " + selectedColumn_search + " to identify the row:",
                                    "Input Value",
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (inputValue_search == null || inputValue_search.isEmpty()) {
                                System.out.println("Input value canceled or empty.");
                                break;
                            }

                            // Check if row exists
                            String condition = selectedColumn_search + " = ?";
                            String selectSql = "SELECT * FROM " + selectedTable_update + " WHERE " + condition;

                            try (PreparedStatement selectStmt = conn.prepareStatement(selectSql)) {
                                selectStmt.setString(1, inputValue_search);
                                ResultSet rs = selectStmt.executeQuery();

                                if (!rs.next()) {
                                    JOptionPane.showMessageDialog(null, "No matching row found.", "Update Row", JOptionPane.INFORMATION_MESSAGE);
                                    break;
                                }

                                // Display the matching row
                                ResultSetMetaData metaData = rs.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                StringBuilder rowDetails = new StringBuilder("Matching Row:\n");
                                for (int i = 1; i <= columnCount; i++) {
                                    rowDetails.append(metaData.getColumnName(i)).append(": ").append(rs.getString(i)).append("\n");
                                }
                                JOptionPane.showMessageDialog(null, rowDetails.toString(), "Row Details", JOptionPane.INFORMATION_MESSAGE);

                                // Step 5: Select column to update
                                String selectedColumn_update = (String) JOptionPane.showInputDialog(
                                        null,
                                        "Select the column to update:",
                                        "Column Selection",
                                        JOptionPane.QUESTION_MESSAGE,
                                        null,
                                        columns_update,
                                        columns_update[0]
                                );

                                if (selectedColumn_update == null || selectedColumn_update.isEmpty()) {
                                    System.out.println("Column update selection canceled or empty.");
                                    break;
                                }

                                // Step 6: Enter new value for the selected column
                                String newValue = JOptionPane.showInputDialog(
                                        null,
                                        "Enter the new value for column " + selectedColumn_update + ":",
                                        "Input New Value",
                                        JOptionPane.QUESTION_MESSAGE
                                );

                                if (newValue == null || newValue.isEmpty()) {
                                    System.out.println("New value input canceled or empty.");
                                    break;
                                }

                                // Step 7: Update the database
                                String updateSql = "UPDATE " + selectedTable_update + " SET " + selectedColumn_update + " = ? WHERE " + condition;

                                try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                                    updateStmt.setString(1, newValue);
                                    updateStmt.setString(2, inputValue_search);

                                    int rowsUpdated = updateStmt.executeUpdate();
                                    if (rowsUpdated > 0) {
                                        JOptionPane.showMessageDialog(null, "Row updated successfully!", "Update Row", JOptionPane.INFORMATION_MESSAGE);
                                    } else {
                                        JOptionPane.showMessageDialog(null, "Failed to update the row.", "Update Row", JOptionPane.ERROR_MESSAGE);
                                    }
                                }

                            } catch (SQLException et) {
                                System.err.println("Query execution error: " + et.getMessage());
                            }

                        } catch (SQLException er) {
                            System.err.println("Database connection error: " + er.getMessage());
                        }
                    break;








                    case "10) Search":
                        // Step 1: Ask user which table they want to search
                        String[] tables_search = {"Equipment_Type", "Customer", "Staff", "Rental", "Overdue", "Payment", "Reservation", "Maintenance"};
                        String selectedTable_search = (String) JOptionPane.showInputDialog(
                                null,
                                "Select the table to search:",
                                "Table Selection",
                                JOptionPane.QUESTION_MESSAGE,
                                null,
                                tables_search,
                                tables_search[0]
                        );

                        if (selectedTable_search == null || selectedTable_search.isEmpty()) {
                            System.out.println("Table selection canceled or empty.");
                            break;
                        }
                        System.out.println("Selected Table: " + selectedTable_search);

                        try (Connection conn = DriverManager.getConnection(
                                "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(Host=oracle.cs.ryerson.ca)(Port=1521))(CONNECT_DATA=(SID=orcl)))",
                                "d1yeung", "06145080")) {

                            // Step 2: Fetch column names for the selected table
                            PreparedStatement columnsStmt = conn.prepareStatement(
                                    "SELECT COLUMN_NAME FROM USER_TAB_COLUMNS WHERE TABLE_NAME = ?");
                            columnsStmt.setString(1, selectedTable_search.toUpperCase()); // Ensure table name is in uppercase
                            ResultSet columnsRs = columnsStmt.executeQuery();

                            // Populate column names into a dropdown
                            java.util.List<String> columnsList = new java.util.ArrayList<>();
                            while (columnsRs.next()) {
                                columnsList.add(columnsRs.getString("COLUMN_NAME"));
                            }
                            columnsRs.close();

                            if (columnsList.isEmpty()) {
                                System.out.println("No columns found for the selected table.");
                                break;
                            }

                            String[] columns_search = columnsList.toArray(new String[0]);
                            String selectedColumn_search = (String) JOptionPane.showInputDialog(
                                    null,
                                    "Select the column to search:",
                                    "Column Selection",
                                    JOptionPane.QUESTION_MESSAGE,
                                    null,
                                    columns_search,
                                    columns_search[0]
                            );

                            if (selectedColumn_search == null || selectedColumn_search.isEmpty()) {
                                System.out.println("Column selection canceled or empty.");
                                break;
                            }
                            System.out.println("Selected Column: " + selectedColumn_search);

                            // Step 3: Ask user to input data for the selected column
                            String inputValue = JOptionPane.showInputDialog(
                                    null,
                                    "Enter the value to search in column " + selectedColumn_search + ":",
                                    "Input Value",
                                    JOptionPane.QUESTION_MESSAGE
                            );

                            if (inputValue == null || inputValue.isEmpty()) {
                                System.out.println("Input value canceled or empty.");
                                break;
                            }
                            System.out.println("Input Value: " + inputValue);

                            // Step 4: Check if the table, column has that data
                            String condition = selectedColumn_search + " = ?";
                            String sql = "SELECT * FROM " + selectedTable_search + " WHERE " + condition;

                            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                stmt.setString(1, inputValue);
                                ResultSet rs = stmt.executeQuery();

                                // Display results in a dialog
                                ResultSetMetaData metaData = rs.getMetaData();
                                int columnCount = metaData.getColumnCount();
                                StringBuilder result = new StringBuilder();
                                boolean hasResults = false;

                                while (rs.next()) {
                                    hasResults = true;
                                    for (int i = 1; i <= columnCount; i++) {
                                        result.append(metaData.getColumnName(i)).append(": ").append(rs.getString(i)).append(" | ");
                                    }
                                    result.append("\n");
                                }

                                if (hasResults) {
                                    JOptionPane.showMessageDialog(null, result.toString(), "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                } else {
                                    JOptionPane.showMessageDialog(null, "No results found for the given condition.", "Search Results", JOptionPane.INFORMATION_MESSAGE);
                                }

                            } catch (SQLException es) {
                                System.err.println("Query execution error: " + es.getMessage());
                            }

                        } catch (SQLException el) {
                            System.err.println("Database connection error: " + el.getMessage());
                        }
                    break;



                    


                
                case "X) Stop Oracle DB":
                    System.out.println("Force/Stop/Kill Oracle DB selected.");
                    ForceStopOracleDB.stop();
                    break;
                case "E) Exit":
                    System.out.println("Exiting program...");
                    frame.dispose();
                    break;
            }
        };

        // Attach action commands and listeners to buttons
        createTablesButton.setActionCommand("1) Create Tables");
        populateTablesButton.setActionCommand("2) Populate Tables");
        queryTablesButton.setActionCommand("3) Query Tables");
        deleteTablesButton.setActionCommand("4) Delete Tables");
        deleteSequencesTriggersButton.setActionCommand("5) Delete Sequences and Triggers");
        createSequencesTriggersButton.setActionCommand("6) Create Sequences and Triggers");
        
        addRowButton.setActionCommand("7) Add a new row");

        removeRowButton.setActionCommand("8) Remove a row");

        updateRowButton.setActionCommand("9) Update a row");

        searchButton.setActionCommand("10) Search");

        forceStopOracleDBButton.setActionCommand("X) Stop Oracle DB");
        exitButton.setActionCommand("E) Exit");

        createTablesButton.addActionListener(buttonListener);
        populateTablesButton.addActionListener(buttonListener);
        queryTablesButton.addActionListener(buttonListener);
        deleteTablesButton.addActionListener(buttonListener);
        deleteSequencesTriggersButton.addActionListener(buttonListener);
        createSequencesTriggersButton.addActionListener(buttonListener);

        addRowButton.addActionListener(buttonListener);
        removeRowButton.addActionListener(buttonListener);
        updateRowButton.addActionListener(buttonListener);
        searchButton.addActionListener(buttonListener);
        
        forceStopOracleDBButton.addActionListener(buttonListener);
        exitButton.addActionListener(buttonListener);

        // Show the frame
        frame.setVisible(true);
    }
}

// Custom OutputStream for redirecting text to JTextArea
class TextAreaOutputStream extends java.io.OutputStream {
    private final JTextArea textArea;

    public TextAreaOutputStream(JTextArea textArea) {
        this.textArea = textArea;
    }

    @Override
    public void write(int b) {
        textArea.append(String.valueOf((char) b));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    @Override
    public void write(byte[] b, int off, int len) {
        textArea.append(new String(b, off, len));
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
