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
 * 
 * @author Stephen Sturges Jr.
 * @version 09/10/2022
 */
public class FileTree {

	static FileTreeNode root; // The first node to be created and modified.
	
	public static void main(String[] args) throws Exception {
		System.out.println("Current directory: " + System.getProperty("user.dir")); // Prints the current directory for user's convenience.
		
		// Get user input.
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter file path (or copy and paste the path above): ");
		String userInput = input.nextLine();
		
		// Convert user input string into URI.
		File file = new File(userInput);
		URI folderURI = file.toURI();
		
		// Create path to file.
		Path folderPath = Paths.get(folderURI);
		// Instantiate the root node.
		root = new FileTreeNode();
		
		scanFiles(root, folderPath);
		System.out.println("\nOutput Legend:\n<Folder/File Name>, <# Folders>, <# Files>, <Total Size (bytes)>\n");
		displayFileTree(root, 0);
		System.out.println("End of Directory.");

	} // End of main method.
	
	/**
	 * Loops through a directory, assigns name, #folders, #files, and total size to a node, and continues until there are no more folders/files.
	 * 
	 * In my original program I had the for loop contained in the if-else statement, which was based off the Tree Reversal tutorial. This caused trouble when I was trying to figure out how to sum the variables
	 * for each node. So, I simplified my node class to make it easier to assign variables (and for time), renamed my node class to FileTreeNode to associate it with this class, 
	 * 
	 * @param	fileTreeNode	The current node whose variables will be modified.
	 * @param	path			The current path of the folder/file to be scanned.
	 * @throws	Exception		An unhandled exception.
	 */
	public static void scanFiles(FileTreeNode fileTreeNode, Path path) throws Exception {
		fileTreeNode.name = path.getFileName().toString(); // Assign the current file/folder name to the current node.
		DirectoryStream<Path> paths = Files.newDirectoryStream(path); // Create (put simply) an array of paths that you can loop through using the for-each method.
		
		// Loop through the paths obtained from the DirectoryStream.
		for(Path tempPath : paths) {
			BasicFileAttributes fileAttributes = Files.readAttributes(tempPath, BasicFileAttributes.class); // Get the folder/file attributes for the current path.
			/*
			 * If the path points to a folder, create a new node, assign that node as a child of the current node, increment the number of folder up 1, and call the listFiles method again to step in another level.
			 * Otherwise (else), the path points to a file, create a new node to represent that file and assign the name, numberOfFiles, and size variables. Finally, add the new file node to the current (folder) node. 
			 */
			if (fileAttributes.isDirectory()) {	
				fileTreeNode.numberOfFolders++; // Increment up the number of folders.
				FileTreeNode newNode = new FileTreeNode();
				fileTreeNode.children.add(newNode); // Add the new node to the children of the current node.
				scanFiles(newNode, tempPath); // Scan the new node for more folders/files.
				fileTreeNode.numberOfFolders += newNode.numberOfFolders; // Sums the total number of folder in the folder. This does not work if the number of folders is not incremented before the newNode is created.
				fileTreeNode.numberOfFiles += newNode.numberOfFiles; // Sums the total number of files in the folder.
				fileTreeNode.size += newNode.size; // Sums the size of all files in the folder.
			} else {
				fileTreeNode.numberOfFiles++; // If the path is pointing to a file, increment the number of files up on the current node.
				fileTreeNode.size += tempPath.toFile().length(); // Get the size of the file and add it to the total size stored in the current node.
				FileTreeNode newNode2 = new FileTreeNode(); // Create a new node to represent a file (instead of a folder).
				newNode2.name = tempPath.getFileName().toString(); // Assign the current file/folder name to the new node.
				newNode2.numberOfFiles = 1; // Assign numberOfFiles 1 as there is only 1 file.
				newNode2.size = tempPath.toFile().length(); // Assign the size of the file to the new node.
				fileTreeNode.children.add(newNode2); // Add the new node (representing a file) to the fileTreeNode (representing a folder).
			}// End of if-else statement.
		} // End of for loop.
	} // End of listFiles method.
	
	/**
	 * Outputs the given node's variables indented corresponding to the node's location the the file tree hierarchy.
	 * 
	 * @param fileTreeNode	The node whose variable are to be output to the console. 
	 * @param depth			Controls the indentation of the node's variables. Works with the fileTreeSpaces method.
	 */
	public static void displayFileTree(FileTreeNode fileTreeNode, int depth) {
		System.out.println(fileTreeSpaces(depth)
							+ fileTreeNode.name
							+ ", "
							+ fileTreeNode.numberOfFolders
							+ ", "
							+ fileTreeNode.numberOfFiles
							+ ", " + fileTreeNode.size);
		// Loop through and display all the information for the nodes stored in the given node's children ArrayList.
		for (FileTreeNode node : fileTreeNode.children) {
			displayFileTree(node, depth + 1);
		} // End of for loop.
	} // End of displayTree method.
	
	/**
	 * Used for indenting folder/file names when outputting the file tree to the console. Borrowed from the Tree Reversal video tutorial.
	 * 
	 * @param	depth The level where a folder/file is in the hierarchy of the file tree.
	 * @return	String with depth number of four space blocks.
	 */
	public static String fileTreeSpaces(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("    ");
		} // End of for loop.
		return builder.toString();
	} // End of fileTreeLines method.

} // End of FileTree class.
