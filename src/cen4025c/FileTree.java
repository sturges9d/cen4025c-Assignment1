package cen4025c;

import java.io.File;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Scanner;

/**
 * Class for CEN 4025C, Software Development I, Assignment 1.
 * Created with guidance from this tutorial: https://youtu.be/UI1LV78WwZk
 * @author Stephen Sturges Jr.
 * @version 09/02/2022
 */
public class FileTree {

	public static void main(String[] args) throws Exception {
		// TODO: Remove debugging line below.
		System.out.println("Current directory: " + System.getProperty("user.dir"));
		
		// Get user input.
		Scanner input = new Scanner(System.in);
		System.out.print("Enter file path: ");
		String userInput = input.nextLine();
		// Convert user input string into URI.
		File file = new File(userInput);
		URI folderURI = file.toURI();
		// Create path to file.
		Path folderPath = Paths.get(folderURI);
		
		listFiles(folderPath, 0);
		
		// TODO: Decide about removing the debugging line below.
		System.out.println("End of Directory.");

	} // End of main method.
	
	public static void listFiles(Path path, int depth) throws Exception { // TODO: Change the name of the "depth" variable.
		BasicFileAttributes fileAttributes = Files.readAttributes(path, BasicFileAttributes.class);
		
		if (fileAttributes.isDirectory()) {
			DirectoryStream<Path> paths = Files.newDirectoryStream(path);
			System.out.println("|" + fileTreeSpaces(depth) + "+ " + path.getFileName());
			
			for(Path tempPath : paths) {
				listFiles(tempPath, depth + 1);
			}
			
		} else {
			System.out.println("|" + fileTreeSpaces(depth) + "- " + path.getFileName());
		}// End of if-else statement.
		
	} // End of listFiles method.
	
	public static String fileTreeSpaces(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("    ");
		}
		return builder.toString();
	} // End of fileTreeLines method.

} // End of FileTree class.
