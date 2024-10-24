package httpClient;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import java.net.http.*;
import java.net.http.HttpRequest.*;

/**
 * This program demonstrates the HTTP client
 * @version 1.02 2023-08-16
 * @author Cay Horstmann
 */
class MoreBodyPublishers 
{   
   public static BodyPublisher ofFormData(Map<Object, Object> data) 
   {
      boolean first = true;
      var builder = new StringBuilder();
      for (Map.Entry<Object, Object> entry : data.entrySet()) 
      {
         if (first) first = false;
         else builder.append("&");
         builder.append(URLEncoder.encode(entry.getKey().toString(),
            StandardCharsets.UTF_8));
         builder.append("=");
         builder.append(URLEncoder.encode(entry.getValue().toString(), 
            StandardCharsets.UTF_8));
      }
      return BodyPublishers.ofString(builder.toString());
   }
  
   public static BodyPublisher ofMimeMultipartData(Map<String, List<?>> data, String boundary) 
         throws IOException 
   {
      var bps = new ArrayList<BodyPublisher>();
      var header = new StringBuilder();
      for (Map.Entry<String, List<?>> entry : data.entrySet()) 
      {
         for (Object value : entry.getValue()) 
         {
            header.append("--%s\r\nContent-Disposition: form-data; name=%s".formatted(
                  boundary, entry.getKey()));
            
            if (value instanceof Path path) 
            {
               header.append("; filename=\"%s\"\r\n".formatted(path.getFileName())); 
               String mimeType = Files.probeContentType(path);
               if (mimeType != null) header.append("Content-Type: %s\r\n".formatted(mimeType));
               header.append("\r\n");
               bps.add(BodyPublishers.ofString(header.toString()));         
               bps.add(BodyPublishers.ofFile(path));
            }
            else 
            {
               header.append("\r\n\r\n%s\r\n".formatted(value));
               header.append("\r\n");
               bps.add(BodyPublishers.ofString(header.toString()));
            }
            header = new StringBuilder("\r\n");
         }
      }
      bps.add(BodyPublishers.ofString("\r\n--%s--\r\n".formatted(boundary))); 
      return BodyPublishers.concat(bps.toArray(BodyPublisher[]::new));
   }
   
   public static BodyPublisher ofSimpleJSON(Map<Object, Object> data) 
   {
      var builder = new StringBuilder();
      builder.append("{");
      var first = true;
      for (Map.Entry<Object, Object> entry : data.entrySet())
      {
         if (first) first = false;
         else
            builder.append(",");
         builder.append(jsonEscape(entry.getKey().toString())).append(": ")
            .append(jsonEscape(entry.getValue().toString()));
      }
      builder.append("}");
      return BodyPublishers.ofString(builder.toString());      
   }
   
   private static Map<Character, String> replacements = Map.of('\b', "\\b", '\f', "\\f",
      '\n', "\\n", '\r', "\\r", '\t', "\\t", '"', "\\\"", '\\', "\\\\");
   
   private static StringBuilder jsonEscape(String str)
   {
      var result = new StringBuilder("\"");
      for (int i = 0; i < str.length(); i++)
      {
         char ch = str.charAt(i);
         String replacement = replacements.get(ch);
         if (replacement == null) result.append(ch);
         else result.append(replacement);
      }
      result.append("\"");
      return result;
   }
}

public class HttpClientTest
{
   public static void main(String[] args)
         throws IOException, URISyntaxException, InterruptedException
   {
      System.setProperty("jdk.httpclient.HttpClient.log", "headers,requests,content");
      String propsFilename = args.length > 0 ? args[0] : "post.properties"; 
      Path propsPath = Path.of(propsFilename);
      var props = new Properties();
      try (Reader in = Files.newBufferedReader(propsPath))
      {
         props.load(in);
      }
      String urlString = "" + props.remove("url");
      String contentType = "" + props.remove("Content-Type");
      BodyPublisher publisher = null;
      if (contentType.equals("application/x-www-form-urlencoded"))
         publisher = MoreBodyPublishers.ofFormData(props);
      else if (contentType.equals("multipart/form-data")) 
      {
         // Split each value along commas, replace strings starting with
         // file:// with Path objects
         var data = new HashMap<String, List<?>>();
         for (Map.Entry<Object, Object> entry : props.entrySet())
         {
            data.put(entry.getKey().toString(), 
                  Stream.of(entry.getValue().toString().split("\\s*,\\s*"))
                     .map(s -> s.startsWith("file://") 
                        ? propsPath.getParent().resolve(Path.of(s.substring(7))) 
                        : s)
                     .toList());
         }
         String boundary = UUID.randomUUID().toString().replace("-", ""); 
         contentType += "; boundary=" + boundary; 
         publisher = MoreBodyPublishers.ofMimeMultipartData(data, boundary);
      } 
      else
      {
         contentType = "application/json";
         publisher = MoreBodyPublishers.ofSimpleJSON(props);
      }

      String result = doPost(urlString, contentType, publisher);
      System.out.println(result);
   }  
   
   public static String doPost(String url, String contentType, BodyPublisher publisher)
         throws IOException, URISyntaxException, InterruptedException
   {        
      try (HttpClient client = HttpClient.newBuilder()
         .followRedirects(HttpClient.Redirect.ALWAYS).build()) 
      {
                           
         HttpRequest request  = HttpRequest.newBuilder()
            .uri(new URI(url))
            .header("Content-Type", contentType)
            .POST(publisher)
            .build();
         
         HttpResponse<String> response 
            = client.send(request, HttpResponse.BodyHandlers.ofString());
         return response.body();
      }
   }   
}
