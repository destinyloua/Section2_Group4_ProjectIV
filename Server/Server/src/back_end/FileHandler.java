package back_end;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileHandler {
	
	public static String GetTimeStamp() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yy-HH:mm:ss");
        String timeStamp = dateFormat.format(new Date());
        return timeStamp;
	}

    // Create a new file
    public static boolean CreateFile(String fileName) {
        try {
            // Create a Path object for the file
            Path filePath = Paths.get(fileName);
            
            // If the file doesn't exist, create it
            if (!Files.exists(filePath)) {
                Files.createFile(filePath);
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Error creating file: " + e.getMessage());
            return false;
        }
    }

    // Write content to an existing file
    public static boolean WriteToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);  // Write the content to the file
            writer.newLine();  // Ensure content is written on a new line
            return true;
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
            return false;
        }
    }

    // Read content from a file and return it as a string
    public static String ReadFromFile(String fileName) {
        try {
            // Read all content from the file and return it as a string
            String content = new String(Files.readAllBytes(Paths.get(fileName)));
            return content;
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
            return null;
        }
    }
}
