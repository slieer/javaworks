package stax;

import java.io.*;
import java.net.*;
import javax.xml.stream.*;

/**
 * This program demonstrates how to use a StAX parser. The program prints all 
 * hyperlinks of an XHTML web page. <br>
 * Usage: java stax.StAXTest URL
 * @author Cay Horstmann
 * @version 1.11 2023-10-24
 */
public class StAXTest
{
   public static void main(String[] args) throws Exception
   {
      String urlString;
      if (args.length == 0)
      {
         urlString = "http://www.w3c.org";
         System.out.println("Using " + urlString);
      }
      else urlString = args[0];
      InputStream in = new URI(urlString).toURL().openStream();
      XMLInputFactory factory = XMLInputFactory.newInstance();
      XMLStreamReader parser = factory.createXMLStreamReader(in);
      while (parser.hasNext())
      {
         int event = parser.next();
         if (event == XMLStreamConstants.START_ELEMENT)
         {
            if (parser.getLocalName().equals("a")) 
            {
               String href = parser.getAttributeValue(null, "href");
               if (href != null)
                  System.out.println(href);               
            }
         }
      }
   }
}