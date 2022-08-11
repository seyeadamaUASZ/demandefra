package sn.gainde2000.pi.formgenerator.generator;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;

public class FileUtils {

	public static boolean saveFile(String path, String filename, StringBuffer content) {
		boolean success = false;
		File file = new File(path+"/"+filename);
		// creation fichier
		File filePath = new File(path);
		if(!filePath.exists()) {
			success = filePath.mkdirs();
		}
		if(!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(file);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos, Charset.forName("UTF-8")));
			out.write(content.toString());
			out.flush();
			out.close();
			success = true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return success;
	}
	
	public static StringBuffer getFileContent(File file) {
		BufferedReader buffer;
		StringBuffer contentBuffer = new StringBuffer();

		
			try {
				buffer = Files.newBufferedReader(file.toPath(), Charset.forName("UTF-8"));
				String line = null;
				while ((line = buffer.readLine()) != null) {
					contentBuffer.append(line+"\n");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
			return contentBuffer;
			
	}
}
