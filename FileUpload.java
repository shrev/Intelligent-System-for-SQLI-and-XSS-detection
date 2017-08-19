package servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import manager.FileManager;

@MultipartConfig
public class FileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
        public static String filename="";
        //private static String filepath="f://upload//";
	@Override
	protected void doGet(HttpServletRequest request, 
    HttpServletResponse response) 
		throws ServletException, IOException {
		uploadFile(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, 
    HttpServletResponse response) 
		throws ServletException, IOException {
		uploadFile(request, response);
	}
	
	// uploadFile method upload file to server.
	private void uploadFile(HttpServletRequest request, 
      HttpServletResponse response) 
			throws ServletException, IOException 
	{
		ArrayList<String> schema;
		for(Part part : request.getParts()) 
		{
			String name = part.getName();
			InputStream is = request.getPart(name).getInputStream();
			String fileName = getUploadedFileName(part);
			FileOutputStream fos = new FileOutputStream(fileName);
			int data = 0;
            filename=fileName;
			while((data = is.read()) != -1) 
			{
				fos.write(data);
			}
			fos.close();
			is.close();
		}
		schema=FileManager.getDBSchema(FileUpload.filename,"");
		File fout = new File("schema.txt");
		FileOutputStream fos = new FileOutputStream(fout);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
	 
		for (String s:schema) {
			bw.write(s);
			bw.newLine();
		}
		bw.close();
		//System.out.println("From here : "+filename);
		new File(filename).delete();
		response.sendRedirect("/test.html");
	}

	private String getUploadedFileName(Part p) {
		String file = "", header = "Content-Disposition";
		String[] strArray = p.getHeader(header).split(";");
		for(String split : strArray) {
			if(split.trim().startsWith("filename")) {
				file = split.substring(split.indexOf('=') + 1);
				file = file.trim().replace("\"", "");
				//System.out.println("File name : "+file);
			}
		}
		return file;
	}	
}