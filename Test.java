package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import detection.classifier;
import detection.result;
import manager.SignupManager;
import sqlidefender.HttpPacket;

public class Test extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Scanner scanner;
	boolean res =false;
	boolean res_learn;
	boolean verdict=false;
    int mod=1;
	String uname="",pwd="";
       
	protected void doGet(HttpServletRequest request, 
    HttpServletResponse response) 
		throws ServletException, IOException {
	

		/*
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    String title = "Request Header";
	    ArrayList<String> schema=new ArrayList();
	    
	    BufferedReader br = new BufferedReader(new FileReader("schema.txt"));
	    String line="";
	    
	    while((line=br.readLine())!=null)
		{
	    	schema.add(line);
	    }
	    
	    for(String s:schema)
	    {
	    	System.out.println(s);
	    }
	    
	    HttpPacket packet=new HttpPacket();
	    
	 /*   out.println(
	                "<BODY>\n" +
	                "<H1>" + title + "</H1>\n" +
	                "<B>Request Method: </B>" +
	                request.getMethod() + "<BR>\n" +
	                "<B>Request URI: </B>" +
	                request.getRequestURI() + "<BR>\n" +
	                "<B>Request Protocol: </B>" +
	                request.getProtocol() + "<BR>\n" +
	                "<B>Request Parametrs: </B>"
	                
	    		); 
	    
	    
	    packet.setRequestMethod(request.getMethod());
	    packet.setRequestUri(request.getRequestURI());
	    packet.setRequestProtocol(request.getProtocol());
	    
	    String params="";
	    
	    String test="";
	    
	    
	    packet.setRequestParametrs(params);
	    
	    
	   /* out.println("<BR><BR>\n");            
	    out.println("<TABLE>\n" +
	                "<TR>\n" +
	                "<TH>Header Name<TH>Header Value"); 
	    
	    out.println("Parameters : <br><br>");
	    
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	      String headerName = (String)headerNames.nextElement();
	   //   out.println("<TR><TD>" + headerName);
	    //  out.println("    <TD>" + request.getHeader(headerName));
	      test+=headerName+" "+request.getHeader(headerName);
	    }
	    
	    Enumeration<String> paramNames = request.getParameterNames();
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.println(paramName + ":");
	      //params+=paramName;
	      out.println(request.getParameter(paramName)+"<br>");
	      params+=" "+request.getParameter(paramName)+"";	      
	    }
	    test=params;
	    
	    
	    if(mod==0)
	    {
	     
	      /*if(!res)
	      {
	    	  out.println("\nNot Sqli");
	    	  res = classifier.basic_xss_detect(test);
	      }
	      res =	classifier.basic_sqli_detect(test);
	    }
	    
	    else
	    {
	       res_learn = classifier.learn_xss(test);
	       if(res_learn) verdict =true;
	       else
	       {
	    	   res_learn = classifier.learn_sqli(test, schema);
	       }
	       if(res_learn) verdict=true;
	       else verdict =false;
	    	//verdict = classifier.learn_sqli(test, schema);
	       
	    }
	    
	    if(!verdict)
	    {
	    	try {
				SignupManager.login("phoebe","smelly_cat");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    if(res || verdict)
	    {
	    	//out.print("<br><br><b>Malicious packet!! Request Dropped</b>");
	    	try {
				obj.put("status", "malicious packet");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else
	    {
	    	String result="";
	    	 try {
	    			result=SignupManager.login(uname,pwd);
	    		 } catch (SQLException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    		 }
	    }
	    
	    
	    
	    packet.setHeaderUpgradeInsecureRequests(request.getHeader("Upgrade-Insecure-Requests"));
	  //  System.out.println("here : "+packet.getHeaderUpgradeInsecureRequests());
	    
	    packet.setHeaderAccept(request.getHeader("Accept"));
	   // System.out.println("here : "+packet.getHeaderAccept());
	    
	    packet.setHeaderConnection(request.getHeader("Connection"));
	  //  System.out.println("here : "+packet.getHeaderConnection());
	    
	    packet.setHeaderReferer(request.getHeader("Referer"));
	  //  System.out.println("here : "+packet.getHeaderReferer());
	    
	    packet.setHeaderCacheControl(request.getHeader("Cache-Control"));
	 //   System.out.println("here : "+packet.getHeaderCacheControl());

	    packet.setHeaderAcceptEncoding(request.getHeader("Accept-Encoding"));
	  //  System.out.println("here : "+packet.getHeaderAcceptEncoding());
	    
	    packet.setHeaderUserAgent(request.getHeader("User-Agent"));
	  //  System.out.println("here : "+packet.getHeaderUserAgent());
	    
	    packet.setHeaderAcceptLanguage(request.getHeader("Accept-Language"));
	  //  System.out.println("here : "+packet.getHeaderAcceptLanguage());
	    
	    packet.setHeaderHost(request.getHeader("Host"));
	   // System.out.println("here : "+packet.getHeaderHost());
	    
	    out.println("</TABLE><BR><BR>\n");
	    scanner = new Scanner(request.getInputStream(), "UTF-8");
		Scanner s = scanner.useDelimiter("\\A");
        String temp=s.hasNext() ? s.next() : "";
        out.println("Packet body : "+temp);
	    out.println("</BODY></HTML>");
	    
	    packet.setPacketBody(temp); */
	    
	  //  System.out.println("File Name : "+FileUpload.filename);
	}
//---------------------------------------------------------------------------------------------------------	
	protected void doPost(HttpServletRequest request, 
    HttpServletResponse response) 
		throws ServletException, IOException {
		uname=request.getParameter("uname");
		pwd=request.getParameter("pwd");
		pwd=pwd.trim();
		String test = uname+" "+pwd,params="";
		int mod = Integer.parseInt(request.getParameter("mod"));
	  	String result="";
		System.out.println(test);
		
		PrintWriter out = response.getWriter();
	    String title = "Request Header";
	    
	    response.setContentType("application/json");
	    JSONObject obj=new JSONObject();
	    
	   /* out.println(
	                "<BODY>\n" +
	                "<H1>" + title + "</H1>\n" +
	                "<B>Request Method: </B>" +
	                request.getMethod() + "<BR>\n" +
	                "<B>Request URI: </B>" +
	                request.getRequestURI() + "<BR>\n" +
	                "<B>Request Protocol: </B>" +
	                request.getProtocol() + "<BR>\n" +
	                "<B>Request Parametrs: </B>" 
	    		); */
	    
	  /*  Enumeration<String> paramNames = request.getParameterNames();
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.println(paramName);
	      out.println(request.getParameter(paramName));
	    } */
	    
	   /* out.println("<BR><BR>\n");
	                
	    out.println("<TABLE>\n" +
	                "<TR>\n" +
	                "<TH>Header Name<TH>Heasad Value");
	    
	    Enumeration<String> headerNames = request.getHeaderNames();
	    while(headerNames.hasMoreElements()) {
	      String headerName = (String)headerNames.nextElement();
	      out.println("<TR><TD>" + headerName);
	      out.println("    <TD>" + request.getHeader(headerName));
	    }
	    out.println("</TABLE><BR><BR>\n");
	    scanner = new Scanner(request.getInputStream(), "UTF-8");
		Scanner s = scanner.useDelimiter("\\A");
        String temp=s.hasNext() ? s.next() : "";
        //out.println("Packet body : "+temp);
      //  out.println("Detection result : "+res); */
	    

	   // out.println("<b>Parameters</b> : <br><br>");
	    
	 /*   Enumeration<String> paramNames = request.getParameterNames();
	    while(paramNames.hasMoreElements()) {
	      String paramName = (String)paramNames.nextElement();
	      out.println(paramName + ":");
	      //params+=paramName;
	      out.println(request.getParameter(paramName)+"<br>");
	      params+=" "+request.getParameter(paramName)+"";	      
	    } */
	    
 ArrayList<String> schema=new ArrayList();
	    
	    BufferedReader br = new BufferedReader(new FileReader("schema.txt"));
	    String line="";
	    
	    while((line=br.readLine())!=null)
		{
	    	schema.add(line);
	    }
	    
	    for(String s:schema)
	    {
	    	System.out.println(s);
	    }
	    
        
        if(mod==0)
	    {
        	res =classifier.basic_sqli_detect(test);
	     
	      if(!res)
	      {
	    	//  out.println("<br>Not Sqli");
	    	  res = classifier.basic_xss_detect(test);
	      }
	    
	    }
	    
	    else
	    {
	       res_learn = classifier.learn_xss(test);
	       if(res_learn) verdict =true;
	       else
	       {
	    	   res_learn = classifier.learn_sqli(test, schema);
	       }
	       if(res_learn) verdict=true;
	       else verdict =false;
	    	//verdict = classifier.learn_sqli(test, schema);
	       
	    }
	    
	    /*if(!verdict)
	    {
	    	try {
				SignupManager.login("phoebe","smelly_cat");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    } */
	    
	    if(res || verdict)
	    {
	    	//out.print("<br><br><b>Malicious packet!! Request Dropped</b>");
	    	try {
				obj.put("status", "malicious packet");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    else
	    {
	  
	    	 try {
	    			result=SignupManager.login(pwd,uname);
	    			obj.put("status",result);
	    		 } catch (SQLException e) {
	    					// TODO Auto-generated catch block
	    					e.printStackTrace();
	    		 } catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	    }
	    
	    
	    
	   // out.println(result);
	    
	    String json=obj.toString();
	    System.out.println("result object : "+json);	
	    out=response.getWriter();
	    out.write(json);
	}		
}