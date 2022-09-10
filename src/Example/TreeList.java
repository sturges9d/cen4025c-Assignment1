package example;

import java.nio.file.DirectoryStream; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.attribute.BasicFileAttributes; 
import java.util.ArrayList; 

public class TreeList { 

	static TreeNode top; // AKA "root" 

	public static void main(String[] args) throws Exception { 
		Path currentPath = Paths.get("S:\\eclipse-workspace\\cen4025c-Assignment1");
		
		top = new TreeNode(); // Create a new TreeNode to use as the top/root.
		
		listDir(top, currentPath); // Adds nodes to tree. 
		writeTree(top, 0); // Outputs tree to console.
	}
	
	public static void listDir(TreeNode treeNode, Path path) throws Exception { 
		DirectoryStream<Path> paths = Files.newDirectoryStream(path);
		
		treeNode.name = path.getFileName().toString();
		
		for(Path tempPath: paths) {
			BasicFileAttributes attr = Files.readAttributes(tempPath, BasicFileAttributes.class); // Get the folder/files attributes.
			
			if(attr.isDirectory()) {
				TreeNode t = new TreeNode(); // If the path is pointing to a directory, create a new tree node.
				treeNode.listOfSubFolders.add(t); // Add the new tree node to the listOfSubFolders, AKA children.
//				treeNode.isFolder = true;
				treeNode.numFolders += 1;
				listDir(t, tempPath); // Call the method again to go a level deeper.
//				treeNode.totalSizeOfFiles += t.totalSizeOfFiles;
			} else {
				treeNode.numFiles++; // If the path is pointing to a file, increment the number of files up on the current node.
				treeNode.totalSizeOfFiles += tempPath.toFile().length(); // Get the size of the file and add it to the total size stored in the node.
				TreeNode t2 = new TreeNode();
				t2.name = tempPath.getFileName().toString();
				t2.numFiles = 1;
				t2.totalSizeOfFiles = (int) tempPath.toFile().length();
				treeNode.listOfSubFolders.add(t2);
			}
		}
	}
	
	public static void writeTree(TreeNode treeNode, int depth) throws Exception {
		System.out.print(spacesForDepth(treeNode, depth) + treeNode.name); // Print the node (file/folder) name with spaces in front based on how deep in the directory it is.
//		if (treeNode.numFolders > 0) {
//			System.out.print(", " + treeNode.numFolders + " Folder(s)"); // Print the count of folders in the file.
//		}
//		if (treeNode.numFiles > 0) {
//			System.out.print(", " + treeNode.numFiles + " File(s)"); // Print the count of files on the same line.
//			System.out.println(", " + treeNode.totalSizeOfFiles + " bytes"); // Print the sum of the file sizes on the same line, and go to new line.
//		}
		System.out.print(", " + treeNode.numFolders + " Folder(s)"); // Print the count of folders in the file.
		System.out.print(", " + treeNode.numFiles + " File(s)"); // Print the count of files on the same line.
		System.out.println(", " + treeNode.totalSizeOfFiles + " bytes"); // Print the sum of the file sizes on the same line, and go to new line.
		for(TreeNode node : treeNode.listOfSubFolders) {
			writeTree(node, depth + 1); // Call this method again (recursive) and go down a level so that the output is indented.
		}
	}
	
	public static String spacesForDepth(TreeNode treeNode, int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("    ");
		}
//		if (treeNode.isFolder == true) {
//			builder.append("+");
//		} else {
//			builder.append("-");
//		}
	return builder.toString();
	}
}

class TreeNode {
	String name;
//	boolean isFolder; // Added for output symbol.
	int numFolders; // Added number of folders.
	int numFiles;
	int totalSizeOfFiles;
	ArrayList<TreeNode> listOfSubFolders = new ArrayList<TreeNode>();
} 