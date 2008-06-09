package rofage.common.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * Gives additional functionnalities for zip files
 * @author pierrot
 *
 */
public abstract class ZipToolkit {
	private static final int BUFFER = 2048;

	public static List<ZipEntry> getEntries (String zipPath) {
		List<ZipEntry> entries = new ArrayList<ZipEntry>();
		
		try {
			ZipFile zipFile = new ZipFile(zipPath);
			Enumeration zipEntries = zipFile.entries();
			while (zipEntries.hasMoreElements()) {
				ZipEntry entry = (ZipEntry) zipEntries.nextElement();
				entries.add(entry);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return entries;
	}
	
	/**
	 * Checks whether the zip file provided contains a file with the given extension
	 * @param zipPath : path to the zip File
	 * @param extension to search
	 * @return
	 */
	public static boolean containsType (String zipPath, String extension) {
		boolean containsType = false;
		ZipFile zipfile = null;
		try {
			zipfile = new ZipFile(zipPath);
			Enumeration entries = zipfile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = ((ZipEntry)entries.nextElement());
			    if (entry.getName().endsWith(extension)) {
			    	containsType = true;
			    	break; // We do not need to check other files
			    }
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (zipfile!=null) zipfile.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return containsType;

	}
	
	/**
	 * Uncompress the given file into the specified dir
	 * @param path
	 * @param destPath path where the zip file will be uncompressed, pass null to uncompress
	 * the file into the working directory
	 * @returns List of paths to the extracted files
	 */
	public static List<String> uncompressFile (String path, String destPath) {
		List<String> listExtractedPaths = new ArrayList<String>();
		if (destPath==null) {
			destPath = ".";
		}
		
		byte data[] = new byte[BUFFER];
		
		BufferedOutputStream dest = null;
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		ZipInputStream zis = null;
		ZipEntry entry = null;
		
		try {
			fis = new FileInputStream(path);
			bis = new BufferedInputStream(fis);
			zis = new ZipInputStream(bis);
			
			// Getting file content
			while ((entry=zis.getNextEntry())!=null) {
				FileOutputStream fos = new FileOutputStream(destPath+File.separator+entry.getName());
				dest = new BufferedOutputStream (fos, BUFFER);
				
				// Writing the file
				int count;
				while ((count = zis.read(data, 0, BUFFER)) != -1) {
			         dest.write(data, 0, count);
				}
				// We save the path in the list
				listExtractedPaths.add(destPath+File.separator+entry.getName());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				dest.flush();
				dest.close();
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listExtractedPaths;
	}
}
