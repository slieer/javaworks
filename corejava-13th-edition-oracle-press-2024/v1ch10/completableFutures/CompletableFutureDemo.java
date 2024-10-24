package completableFutures;

import java.awt.image.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.*;

import javax.imageio.*;

/**
 * @version 1.02 2023-07-30
 * @author Cay Horstmann
 */
public class CompletableFutureDemo
{
   private static final Pattern IMG_PATTERN = Pattern.compile(
         "[<]\\s*[iI][mM][gG]\\s*[^>]*[sS][rR][cC]\\s*[=]\\s*['\"]([^'\"]*)['\"][^>]*[>]");
   private ExecutorService executor = Executors.newCachedThreadPool();
   private URI uriToProcess;

   public CompletableFuture<String> readPage(URI uri)
   {
      return CompletableFuture.supplyAsync(() -> {
         try
         {
            var contents = new String(uri.toURL().openStream().readAllBytes());
            System.out.println("Read page from " + uri);
            return contents;
         }
         catch (IOException e)
         {
            throw new UncheckedIOException(e);
         }
      }, executor);
   }

   public List<URI> getImageLinks(String webpage) // not blocking
   {
      var result = new ArrayList<URI>();
      Matcher matcher = IMG_PATTERN.matcher(webpage);
      while (matcher.find())
      {
         URI uri = URI.create(uriToProcess + "/" + matcher.group(1));
         result.add(uri);
      }
      System.out.println("Found links: " + result);
      return result;
   }

   public CompletableFuture<List<BufferedImage>> getImages(List<URI> uris)
   {
      return CompletableFuture.supplyAsync(() -> {
         try
         {
            var result = new ArrayList<BufferedImage>();
            for (URI uri : uris)
            {
               result.add(ImageIO.read(uri.toURL()));
               System.out.println("Loaded " + uri);
            }
            return result;
         }
         catch (IOException e)
         {
            throw new UncheckedIOException(e);
         }
      }, executor);
   }

   public void saveImages(List<BufferedImage> images)
   {
      System.out.println("Saving " + images.size() + " images");
      try
      {
         for (int i = 0; i < images.size(); i++)
         {
            String filename = "/tmp/image" + (i + 1) + ".png";
            ImageIO.write(images.get(i), "PNG", new File(filename));
         }
      }
      catch (IOException e)
      {
         throw new UncheckedIOException(e);
      }
      executor.shutdown();
   }

   public CompletableFutureDemo(URI uri) 
   {
      uriToProcess = uri;
   }
   
   public void run() throws IOException, InterruptedException
   {
      CompletableFuture.completedFuture(uriToProcess)
            .thenComposeAsync(this::readPage, executor)
            .thenApply(this::getImageLinks)
            .thenCompose(this::getImages)
            .thenAccept(this::saveImages);

      // or use the HTTP client:
      /*     
      HttpClient client = HttpClient.newBuilder().build(); 
      HttpRequest request = HttpRequest.newBuilder(uriToProcess).GET().build();
      client.sendAsync(request, BodyHandlers.ofString())
         .thenApply(HttpResponse::body)
         .thenApply(this::getImageLinks)
         .thenCompose(this::getImages)
         .thenAccept(this::saveImages);
      */
   }

   public static void main(String[] args)
         throws IOException, InterruptedException
   {
      new CompletableFutureDemo(URI.create("http://horstmann.com/index.html")).run();
   }
}
