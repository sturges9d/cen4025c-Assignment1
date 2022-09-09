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
				listDir(t, tempPath); // Call the method again to go a level deeper.
			} else {
				treeNode.numFiles++; // If the path is pointing to a file, increment the number of files up on the current node.
				treeNode.totalSizeOfFiles += tempPath.toFile().length(); // Get the size of the file and add it to the total size stored in the node. 
			}
		}
	}
	
	public static void writeTree(TreeNode treeNode, int depth) throws Exception {
		System.out.print(spacesForDepth(depth) + treeNode.name);
		System.out.print(" " + treeNode.numFiles);
		System.out.println(" " + treeNode.totalSizeOfFiles);
		for(TreeNode node : treeNode.listOfSubFolders) {
			writeTree(node, depth + 1);
		}
	}
	
	public static String spacesForDepth(int depth) {
		StringBuilder builder = new StringBuilder();
		for(int i = 0; i < depth; i++) {
			builder.append("  ");
		}
	return builder.toString();
	}
}

class TreeNode {
	String name;
	int numFiles;
	int totalSizeOfFiles;
	ArrayList<TreeNode> listOfSubFolders = new ArrayList<TreeNode>();
} 