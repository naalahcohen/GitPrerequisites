package git;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;
public class Tree {
	public static void makeTree(ArrayList<String> list) throws IOException {
		File tree = new File("Objects/tree");
		tree.createNewFile();
		FileWriter myWriter = new FileWriter("Objects/tree");

		for(String pair : list) {
			pair+="\n";
		      myWriter.write(pair);
		      System.out.println("Successfully wrote to the file.");
		}
		myWriter.close();
		String contents = readFile(tree.getName(), StandardCharsets.US_ASCII);
		String sha1 = encryptThisString(contents);
		File newFile = new File("Objects/" + sha1);
		newFile.createNewFile();
		renameFile(tree.getName(), newFile.getName());
	}
	public static void renameFile(String oldName, String newName) throws IOException {
		// File (or directory) with old name
		File file = new File(oldName);

		// File (or directory) with new name
		File file2 = new File(newName);

		if (file2.exists())
		   throw new java.io.IOException("file exists");

		// Rename file (or directory)
		boolean success = file.renameTo(file2);

		if (!success) {
			System.out.println("File not renamed");
		}

	}
	public static String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
	public static String encryptThisString(String input) {
        try {
            // getInstance() method is called with algorithm SHA-1
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());
            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);
            // Convert message digest into hex value
            String hashtext = no.toString(16);
 
            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            // return the HashText
            return hashtext;
        }
        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
}
}
