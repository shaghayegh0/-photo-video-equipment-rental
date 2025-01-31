import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ForceStopOracleDB {

    public static void stop() {
        try {
            // Command to shut down Oracle Database (replace with your actual credentials and SID)
            String[] command = {
                "bash", "-c", 
                "echo 'shutdown immediate;' | sqlplus -s d1yeung/06145080@//oracle.cs.ryerson.ca:1521/orcl"
            };

            // Run the command using ProcessBuilder
            ProcessBuilder processBuilder = new ProcessBuilder(command);
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            // Capture the output of the command
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                }
            }

            // Wait for the process to exit
            process.waitFor();

            System.out.println("Oracle DB is being shut down.");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
