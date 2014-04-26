package simple.foundation.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

/**
 * http://blog.csdn.net/flexworks/article/details/6623750
 * 
 * @author slieer
 * Create Date2012-2-17
 * version 1.0
 */
public class ZipFileWithTier {
	
	/**
	 * 文件压缩路径
	 */
    private static final String zipPath = "D:\\temp\\MyZip.zip";
    /**
     * 解压缩文件路径
     */
    private static final String unzipPath = "D:\\temp\\MyZip\\";
    /**
     * 
     */
    private static final String srcFiles = "D:\\temp\\MyZip\\";

    public static void main(String[] args) {
    	ZipFileWithTier z = new ZipFileWithTier();
    	z.zipFile();
	}
    
    @Test
    public void zipFile(){
	 File file = new File(zipPath);
	 if(file.exists())
		 file.delete();
	 zipFileWithTier(srcFiles, zipPath);
    }
    
    @Test
    public void upZipFile(){
	try {
	    unzipFilesWithTier(unzipPath + File.separator);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    /*
     * Compress the specify file that contains sub-folders and store them to a zipfile.
     * @param srcFiles: the file will be compressed
     * @param zipPath: the location which stores the zipfile.
     */
    public static void zipFileWithTier(String srcFiles, String zipPath) {
    	ZipOutputStream out = null;
		try {
	
		    out = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(zipPath)));
		    zipFiles(srcFiles, out, "");
		   
		} catch (IOException e) {
		    e.printStackTrace();
		}finally{
			 try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
    }

    /*
     * Recursive the specify file also contains the folder which may don't include any file.
     * @param filePath: compress file
     * @param ZipOutputStream: the zipfile's outputStream. 
     * @param prefix: the prefix indicates the parent folder name of the file that makes the tier relation.
     */
	public static void zipFiles(String filePath, ZipOutputStream out,
			String prefix) throws IOException {
		File file = new File(filePath);
		if (file.isDirectory()) {
			if (file.listFiles().length == 0) {
				//process null directory.
				ZipEntry zipEntry = new ZipEntry(prefix + file.getName() + "/");
				out.putNextEntry(zipEntry);
				out.closeEntry();
			} else {
				prefix += file.getName() + File.separator;
				for (File f : file.listFiles())
					zipFiles(f.getAbsolutePath(), out, prefix);
			}
		} else {
			FileInputStream in = new FileInputStream(file);
			ZipEntry zipEntry = new ZipEntry(prefix + file.getName());
			out.putNextEntry(zipEntry);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.closeEntry();
			in.close();
		}

	}

    /*
     * Unzip the file also contains the folder which the listFiles's length is 0.
     * @param bytes: the content of the zipfile by byte array.     * 
     * @param prefix: the prefix is the root of the store path.
     * @IOExcetion: the ioexception during unzipFiles.
     */
	public static void unzipFilesWithTier(String prefix) throws IOException {

		InputStream bais = new ByteArrayInputStream(new byte[] {});
		ZipInputStream zin = new ZipInputStream(bais);
		ZipEntry ze;
		while ((ze = zin.getNextEntry()) != null) {
			if (ze.isDirectory()) {
				File file = new File(prefix + ze.getName());
				if (!file.exists())
					file.mkdirs();
				continue;
			}
			File file = new File(prefix + ze.getName());
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			ByteArrayOutputStream toScan = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int len;
			while ((len = zin.read(buf)) > 0) {
				toScan.write(buf, 0, len);
			}
			byte[] fileOut = toScan.toByteArray();
			toScan.close();
			writeByteFile(fileOut, new File(prefix + ze.getName()));
		}
		zin.close();
		bais.close();
	}

	public static byte[] readFileByte(String filename) throws IOException {

		if (filename == null || filename.equals("")) {
			throw new NullPointerException("File is not exist!");
		}
		File file = new File(filename);
		long len = file.length();
		byte[] bytes = new byte[(int) len];

		BufferedInputStream bufferedInputStream = new BufferedInputStream(
				new FileInputStream(file));
		int r = bufferedInputStream.read(bytes);
		if (r != len)
			throw new IOException("Read file failure!");
		bufferedInputStream.close();

		return bytes;

	}
    
	public static String writeByteFile(byte[] bytes, File file) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "success";
	}
  

}