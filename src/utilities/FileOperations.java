package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.testng.Assert;

public class FileOperations {

	public String getValueFromPropertyFile(String filePath, String key) {
		String keyValue = null;
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(filePath));
			keyValue = prop.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
			Assert.fail();
		}
		return keyValue;
	}

	public final static void updateValueToPropertyFile(String filePath, String key, String value) throws IOException {
		FileInputStream in = new FileInputStream(filePath);
		Properties props = new Properties();
		props.load(in);
		in.close();

		FileOutputStream out = new FileOutputStream(filePath);
		props.setProperty(key, value);
		props.store(out, null);
		out.close();
	}

	public static boolean isFileExist(String fileName, String destinationDirectory) {
		File dir = new File(destinationDirectory);
		for(File file: dir.listFiles()) {
			String name = file.getName();
			if(name.equals(fileName)) {
				return true;
			}
		}
		return false;
	}

	public static void deleteFileInDirectory(String destinationDirectory, String fileName) throws Exception {
		try {
			File dir = new File(destinationDirectory);
			for(File file: dir.listFiles()) {
				if(file.getName().equals(fileName)) {
					file.delete();
				}
			}
		}	
		catch(Exception ex) {
			throw new Exception(destinationDirectory+ " not found \n"+ ex);
		}
	}

	public static void deleteAllFilesInDirectory(String destinationDirectory) throws Exception {
		try {
			File dir = new File(destinationDirectory);
			for(File file: dir.listFiles()) {
				file.delete();
			}
		}	
		catch(Exception ex) {
			throw new Exception(destinationDirectory+ " not found \n"+ ex);
		}
	}

	public static void copyFile(String sourcePath,String destPath,String fileName) {
		File source = new File(sourcePath + fileName);
		File dest = new File(destPath+fileName);
		try {
			FileUtils.copyFile(source, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
