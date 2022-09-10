package cen4025c;

import java.io.File;
import java.net.URI;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Iterator;
import java.util.Scanner;

/**
 * Class for CEN 4025C, Software Development I, Assignment 1.
 * Created with guidance from this tutorial: https://youtu.be/UI1LV78WwZk
 * 
 * @author Stephen Sturges Jr.
 * @version 09/10/2022
 */
public class FileTree {

	static FileTreeNode root;
	
	public static void main(String[] args) throws Exception {
		// TODO: Remove debugging line below.
		System.out.println("Current directory: " + System.getProperty("user.dir"));
		
		// Get user input.
		Scanner input = new Scanner(System.in);
		System.out.print("\nEnter file path (or copy and paste the path above): ");
		String userInput = input.nextLine();
		
		// Convert user input string into URI.
		File file = new File(userInput);
		URI folderURI = file.toURI();
		
		// Create path to file.
		Path folderPath = Paths.get(folderURI);
		
		root = new FileTreeNode();
		
		scanFiles(root, folderPath);
		System.out.println("\nOutput Legend:\n<Folder/File Name>, <# Folders>, <# Files>, <Total Size (bytes)>\n");
		displayFileTree(root, 0);
		
		
		// TODO: Decide about removing the debugging line below.
		System.out.println("End of Directory.");

	} // End of main method.
	
	/**
	 * 
	 * 
	 * @param	fileTreeNode
	 * @param	path
	 * @param	depth
	 * @throws	Exception
	 */
	public static void scanFiles(FileTreeNode fileTreeNode, Path path) throws Exception {
		fileTreeNode.name = path.getFileName().toString(); // Assign the current file/folder name to the current node.
		DirectoryStream<Path> paths = Files.newDirectoryStream(path); // Create (put simply) an array of paths that you can loop through using the for-each method.
		
		// Loop through the paths.  
		for(Path tempPath : paths) {
			BasicFileAttributes fileAttributes = Files.readAttributes(tempPath, BasicFileAttributes.class); // Get the folder/file attributes for the current path.

			/*
			 * If the path points to a folder, create a new node, assign that node as a child of the current node, increment the number of folder up 1, and call the listFiles method again to step in another level.
			 * Otherwise (else),  
			 */
			if (fileAttributes.isDirectory()) {	
				fileTreeNode.numberOfFolders++;
				FileTreeNode newNode = new FileTreeNode();
				fileTreeNode.children.add(newNode);
				scanFiles(newNode, tempPath);
				fileTreeNode.numberOfFolders += newNode.numberOfFolders;
				fileTreeNode.numberOfFiles += newNode.numberOfFiles;
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
		}
		
	} // End of listFiles method.
	
	// TODO finish this method.
	public static void displayFileTree(FileTreeNode fileTreeNode, int depth) {
		System.out.println(fileTreeSpaces(depth)
							+ fileTreeNode.name
							+ ", "
							+ fileTreeNode.numberOfFolders
							+ ", "
							+ fileTreeNode.numberOfFiles
							+ ", " + fileTreeNode.size);
		// Loop through all the node stored in the given node's children ArrayList and call the displayFileTree method again to display them.
		for (FileTreeNode node : fileTreeNode.children) {
			displayFileTree(node, depth + 1);
		}
	} // End of displayTree method.
	
	// TODO finish this method.
	public static String fileTreeSpaces(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("    ");
		}
		return builder.toString();
	} // End of fileTreeLines method.

} // End of FileTree class.
