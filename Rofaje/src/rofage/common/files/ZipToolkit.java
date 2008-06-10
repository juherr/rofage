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
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

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
				if (dest!=null)	{
					dest.flush();
					dest.close();
				}
				if (zis!=null) {
					zis.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return listExtractedPaths;
	}
	
	/**
	 * Compress files into a zip file located in destPath
	 * @param listFiles
	 * @param destPath
	 */
	public static void compress (List<String> listPaths, String destPath) {
		byte data[] = new byte[BUFFER];
		
		ZipOutputStream out = null;
		try {
			FileOutputStream dest = new FileOutputStream (destPath);
			BufferedOutputStream buff = new BufferedOutputStream(dest);
			out = new ZipOutputStream(buff);
			out.setMethod(ZipOutputStream.DEFLATED);
			out.setLevel(9);
			Iterator<String> iterPaths = listPaths.iterator();
			while (iterPaths.hasNext()) {
				String path = iterPaths.next();
				File file = new File(path);
				FileInputStream fi = new FileInputStream(file);
				BufferedInputStream buffi = new BufferedInputStream(fi, BUFFER);
				ZipEntry entry = new ZipEntry(file.getName());
				out.putNextEntry(entry);
				int count;
				while((count = buffi.read(data, 0, BUFFER)) != -1) {
				         out.write(data, 0, count);
				}
				out.closeEntry();
			    buffi.close();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
