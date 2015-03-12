package com.sheffield.ecommerce;

import java.io.IOException;
   import java.io.FileNotFoundException;
   
   import java.util.Properties;
   import java.util.Vector;

   import javax.servlet.*;
   import javax.servlet.http.*;
   import javax.servlet.ServletConfig;
   
   import org.apache.velocity.Template;
   import org.apache.velocity.context.Context;
   import org.apache.velocity.servlet.VelocityServlet;
   import org.apache.velocity.app.Velocity;
   import org.apache.velocity.exception.ResourceNotFoundException;
   import org.apache.velocity.exception.ParseErrorException;
   
   public class Test extends VelocityServlet
   {
    /**
     *   Make sure the template files are found
     */
    protected Properties loadConfiguration(ServletConfig config )
        throws IOException, FileNotFoundException
    {
     Properties p = new Properties();
     String path = config.getServletContext().getRealPath("/");
      if (path == null)
      {
       System.out.println(" SampleServlet.loadConfiguration() : unable to " 
                          + "get the current webapp root.  Using '/'. Please fix.");
       path = "/";
      }
      p.setProperty( Velocity.FILE_RESOURCE_LOADER_PATH,  path );
      p.setProperty( "runtime.log", path + "test.log" );
      return p;
    }
  
    /*
     * The Servlet
     */
    public Template handleRequest(HttpServletRequest request, 
                                  HttpServletResponse response, Context ctx )
                                  
    {        
     String inputEmail = request.getParameter("inputEmail");
     ctx.put("email", inputEmail);
     Template outty = null;
     String text = "Velocity";
     ctx.put("name", text);
        
     try {
       // Open the template 'hello.vm'
       outty =  getTemplate("templates/test.vm");
     } catch( ParseErrorException pee ){
       System.out.println("SampleServlet : parse error for template " + pee);
     } catch( ResourceNotFoundException rnfe ){
       System.out.println("SampleServlet : template not found " + rnfe);
     } catch( Exception e ){
       System.out.println("Error " + e);
     }          
   
     return outty;
    }
   }