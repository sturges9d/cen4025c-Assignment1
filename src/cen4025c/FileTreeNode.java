package cen4025c;

import java.util.ArrayList;

/**
 * Class for CEN 4025C, Software Development I, Assignment 1.
 * 
 * I removed all of the setters and getters in order to streamline the interaction and for time purposes.
 * 
 * @author Stephen Sturges Jr
 * @version 09/10/2022
 */
public class FileTreeNode {

	// Variables.
	public int numberOfFolders;
	public int numberOfFiles;
	public long size;
	public String name;
	public ArrayList<FileTreeNode> children = new ArrayList<FileTreeNode>(); // I forgot to instantiate the children variable before by creating the new ArrayList.
	
} // End of FileNode class.
