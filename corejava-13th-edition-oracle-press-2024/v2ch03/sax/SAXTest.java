package sax;

import java.io.*;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.xml.sax.helpers.*;

/**
 * This program demonstrates how to use a SAX parser. The program prints all 
 * hyperlinks of an XHTML web page. <br>
 * Usage: java sax.SAXTest URL
 * @version 1.02 2023-10-24
 * @author Cay Horstmann
 */
public class SAXTest
{
   public static void main(String[] args) throws Exception
   {
      String url;
      if (args.length == 0)
      {
         url = "https://www.w3.org/2010/04/xhtml10-strict.html";
         System.out.println("Using " + url);
      }
      else url = args[0];

      var handler = new DefaultHandler()
         {
            public void startElement(String namespaceURI, String lname, 
                  String qname, Attributes attrs)
            {
               if (lname.equals("a") && attrs != null)
               {
                  String href = attrs.getValue("href");
                  if (href != null) System.out.println(href);
               }
            }
         };

      SAXParserFactory factory = SAXParserFactory.newInstance();
      factory.setNamespaceAware(true);
      factory.setFeature(
         "http://apache.org/xml/features/nonvalidating/load-external-dtd", 
         false);
      SAXParser saxParser = factory.newSAXParser();
      InputStream in = new URI(url).toURL().openStream();
      saxParser.parse(in, handler);
   }
}
