package rofage.common.files;

import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.swing.JOptionPane;

import rofage.common.object.Configuration;
import rofage.common.object.GlobalConfiguration;
import rofage.ihm.Messages;
import de.schlichtherle.io.File;
import de.schlichtherle.io.FileInputStream;

public abstract class FileToolkit {
	
	public final static int FILTER_ROMS_ARCHIVES 	= 1;
	public final static int FILTER_ROMS				= 2;
	
	public static void checkAndCreateFolder (String path) {
		File folderImages = new File (path);
		if (!folderImages.exists()) {
			// We have to create this folder
			folderImages.mkdir();
		}	
	}
	
	/**
	 * delete a directory and all its contents
	 * @param directory File
	 */
	public static void deleteDirectory (File directory) {
        if (directory.exists() && directory.isDirectory()) { 
			File[] files = (File[])directory.listFiles(); 
			for(int i=0; i<files.length; i++) { 
				if(files[i].isDirectory()) { 
					deleteDirectory(files[i]); 
				} 
				else { 
					files[i].delete(); 
				}
			}
			directory.delete();
        }
	}
	
	public static String getCRC32(String filepath) {

        try {
            CheckedInputStream cis = null;
            try {
                // Computer CRC32 checksum
                cis = new CheckedInputStream(
                        new FileInputStream(filepath), new CRC32());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            byte[] buf = new byte[128];
            while(cis.read(buf) >= 0) {
            }
            
            return convertLongCRCToStringCRC(cis.getChecksum().getValue()); 

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error";  //$NON-NLS-1$
    }
	
	public static String convertLongCRCToStringCRC (long longCRC) {
//		 The CRC String must have 8 digits
        String crc32 = Long.toHexString(longCRC).toUpperCase();
        while (crc32.length()<8) {
        	crc32 = "0".concat(crc32);
        }
        return crc32;
	}
	
	/**
	 * returns the number of file contained in this directory based on the filefilter
	 * this method also looks inside subdirectories
	 * @return
	 */
	public static int getFileNb(File topDirectory, FilenameFilter filenameFilter) {
		int nbFile=0;
		if (!topDirectory.isDirectory()) return 0; // We ensure it's a directory
		
		String[] tabFiles = topDirectory.list(filenameFilter);
		
		// We convert the array to a list
		List<String> listFilePaths = new ArrayList<String>();
		for (int i=0; i<tabFiles.length; i++) {
			listFilePaths.add(tabFiles[i]);
		}
		
		Iterator<String> iterFilePaths = listFilePaths.iterator();
		List<String> subdirectoriesPath = new ArrayList<String>();
		while (iterFilePaths.hasNext()) {
			String curPath = iterFilePaths.next();
			File file = new File(topDirectory.getAbsolutePath()+File.separator+curPath);
			if (file.isDirectory()) {
				subdirectoriesPath.add(topDirectory.getAbsolutePath()+File.separator+curPath);
			} else {
				nbFile++;
			}
		}
		Iterator<String> iterSubDir = subdirectoriesPath.iterator();
		while (iterSubDir.hasNext()) {
			File subDir = new File(iterSubDir.next());
			nbFile += getFileNb(subDir, filenameFilter);
		}
		return nbFile;
	}
	
	/**
	 * Move the specified file to the new filepath
	 * This method uses renameTo which may be quicker, but it has limitation (same disk, etc..)
	 * if it doesn't work we try an alternative way.
	 * @param filepath
	 * @param destPath
	 */
	public static void moveFile (File source, String destPath) {
		File destination = new File (destPath);
        // We try with renameTo
        boolean result = source.renameTo(destination);
        if( !destination.exists() ) {
            if( !result ) {
                // We try to copy
                result = true;
                result &= source.copyTo(destination);
                if(result) source.delete();
            }
        } else {
                JOptionPane.showMessageDialog(null, Messages.getString("FileToolkit.1"), Messages.getString("FileToolkit.2"), JOptionPane.ERROR_MESSAGE); //$NON-NLS-1$ //$NON-NLS-2$
        }  
	}
	
	/**
	 * Returns the extension of the file
	 * If no file extension an empty String is returned
	 * @param fileName
	 * @return
	 */
	public static String getFileExtension (String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index!=-1) return fileName.substring(index).toLowerCase();
		return ""; //$NON-NLS-1$
	}
	
	/**
	 * Returns the name of the file without its extension
	 * If the file has no extension then filename is returned
	 * @param fileName
	 * @return
	 */
	public static String removeFileExtension (String fileName) {
		int index = fileName.lastIndexOf('.');
		if (index!=-1) return fileName.substring(0, index);
		return fileName;
	}
	
	public static FilenameFilter getFileNameFilter(int type, final Configuration conf) {
		switch (type) {
			case FILTER_ROMS_ARCHIVES :
				return new FilenameFilter () {
					public boolean accept(java.io.File dir, String name) {
						String ext = getFileExtension(name).toLowerCase();
						if (GlobalConfiguration.allowedCompressedExtensions.contains(ext)
								|| conf.getAllowedExtensions().contains(ext)) {
							return true;
						}
						return false;
					}
				};
			case FILTER_ROMS :
				return new FilenameFilter () {
					public boolean accept(java.io.File dir, String name) {
						String ext = getFileExtension(name).toLowerCase();
						if (conf.getAllowedExtensions().contains(ext)) {
							return true;
						}
						return false;
					}
				};
		}
		return null;
	}	
	
	/**
	 * List all the file paths included in a directory
	 * This function does NOT look inside archive
	 * @param dirPath : path to the directory
	 * @return
	 */
	public static List<String> getAllFilePaths (String path) {
		List<String> listFilePaths = new ArrayList<String>();
		File f = new File(path);
		if (f.exists()) {
			if (f.isDirectory() && !f.isArchive()) {
				String[] tabPaths = f.list();
				for (int i=0; i<tabPaths.length; i++) {
					listFilePaths.addAll(getAllFilePaths(f.getAbsolutePath()+File.separator+tabPaths[i]));
				}
			} else {
				// It's a regular file we add it to the list
				listFilePaths.add(path);
			}
		}
		return listFilePaths;
	}
}
