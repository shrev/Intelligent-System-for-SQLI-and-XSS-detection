package manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class FileManager {

	public static ArrayList<String> getDBSchema(String fileName,String filePath)
	{
		ArrayList<String> schema=new ArrayList<String>(); 
		BufferedReader br = null; String strLine = ""; 
		//filePath="f://upload/";
		try 
		{ 
			br = new BufferedReader( new FileReader(fileName)); 
			while( (strLine = br.readLine()) != null)
			{ 
				//System.out.println(strLine);
				if(strLine.contains("CREATE TABLE"))
				{
					strLine=strLine.replace("CREATE TABLE `", "");
					strLine=strLine.replace("` (", "");
					System.out.println(strLine);
					schema.add(strLine);
				}
			} 
		}catch (IOException e){
			System.err.println("Unable to read the file: fileName"); 
		}		
		return schema;
	}
}