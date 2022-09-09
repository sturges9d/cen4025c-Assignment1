package example;

import java.nio.file.DirectoryStream; 
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.nio.file.Paths; 
import java.nio.file.attribute.BasicFileAttributes; 
import java.util.ArrayList; 

public class TreeList { 

	static TreeNode top; 

	public static void main(String[] args) throws Exception { 
		Path currentPath = Paths.get("S:\\eclipse-workspace\\cen4025c-Assignment1"); 
		top = new TreeNode(); 
		listDir(top, currentPath); 
		writeTree(top, 0); 
	}
	
	public static void listDir(TreeNode treeNode, Path path) throws Exception { 
		DirectoryStream<Path> paths = Files.newDirectoryStream(path); 
		treeNode.name = path.getFileName().toString(); 
		for(Path tempPath: paths) { 
			BasicFileAttributes attr = Files.readAttributes(tempPath, BasicFileAttributes.class);
			if(attr.isDirectory()) {
				TreeNode t = new TreeNode();
				treeNode.listOfSubFolders.add(t);
				listDir(t, tempPath);
			} else {
				treeNode.numFiles++;
				treeNode.totalSizeOfFiles += tempPath.toFile().length();
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