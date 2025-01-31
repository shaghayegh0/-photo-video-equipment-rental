import java.util.Scanner;


public class MainMenu {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=================================================================");
            System.out.println("| Oracle All Inclusive Tool");
            System.out.println("| Main Menu - Select Desired Operation(s):");
            System.out.println("| <CTRL-Z Anytime to Enter Interactive CMD Prompt>");
            System.out.println("-----------------------------------------------------------------");
            System.out.println(" 1) Create Tables");
            System.out.println(" 2) Populate Tables");
            System.out.println(" 3) Query Tables (All Queries)");
            System.out.println(" 4) Delete Tables");
            System.out.println(" 5) Delete Sequences and Triggers");
            System.out.println(" 6) Create Sequences and Triggers");
            System.out.println(" X) Force/Stop/Kill Oracle DB");
            System.out.println(" E) End/Exit");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "1":
                    CreateTables.create();
                    break;
                case "2":
                    PopulateTables.populate();
                    break;
                case "3":
                    QueryExecutor.query();
                    break;
                case "4":
                    DeleteTables.delete();
                    break;
                case "5":
                    DropSequencesTriggers.drop();
                    break;
                case "6":
                    CreateSequencesTriggers.create();
                    break;
                case "X":
                    ForceStopOracleDB.stop();
                    break;
                case "E":
                    System.out.println("Exiting program...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid option. Try again.");
            }
        }
    }
}
