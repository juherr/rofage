package rofage.ihm.windows.conf;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import rofage.common.files.FileToolkit;

public class FileChooserFilter extends FileFilter {
	
	private String [] allowedSuffixes;
	private String  description;
   
   public FileChooserFilter (String []allowedSuffixes, String description){
	   this.allowedSuffixes = allowedSuffixes;
	   this.description = description;
   }
   
   boolean isShownSuffixe (String suffixe){
	   for( int i = 0; i<allowedSuffixes.length; ++i) {
		   if(suffixe.equalsIgnoreCase(allowedSuffixes[i])) {
			   return true;
		   }
	   }
	   return false;
   }
   public boolean accept(File file) {
	   if (file.isDirectory()) return true;
	   String fileExtension = FileToolkit.getFileExtension(file.getName());
	   
	   return (fileExtension!=null && isShownSuffixe(fileExtension));
   }

   public String getDescription () {
	   return description;
   }
}