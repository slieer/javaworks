package compiler;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.List;

import javax.tools.*;
import javax.tools.JavaFileObject.*;

/**
 * @version 1.2 2023-08-16
 * @author Cay Horstmann
 */
public class CompilerTest
{
   public static void main(final String[] args) 
         throws IOException, ReflectiveOperationException
   {
      JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

      var classFileObjects = new ArrayList<ByteArrayClass>();

      var diagnosticsListener = new DiagnosticCollector<JavaFileObject>();

      JavaFileManager fileManager 
         = compiler.getStandardFileManager(diagnosticsListener, null, null);
      fileManager = new ForwardingJavaFileManager<>(fileManager)
         {
            public JavaFileObject getJavaFileForOutput(Location location, 
                  String className, Kind kind, FileObject sibling) throws IOException
            {
               if (kind == Kind.CLASS) 
               {
                  var fileObject = new ByteArrayClass(className);
                  classFileObjects.add(fileObject);
                  return fileObject;
               }
               else return super.getJavaFileForOutput(location, className, kind, sibling);
            }
         };


      String mainClassName = "compiler.Main";
      String worksheetName = args.length > 0 ? args[0] : "compiler/worksheet";
      
      StandardJavaFileManager stdFileManager 
         = compiler.getStandardFileManager(null, null, null);
      var sources = new ArrayList<JavaFileObject>();
      for (JavaFileObject file : stdFileManager.getJavaFileObjectsFromStrings(
            List.of(mainClassName.replace(".",  "/") + ".java")))
         sources.add(file);
      
      JavaFileObject source = buildSource(mainClassName, Path.of(worksheetName));
      JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, 
         diagnosticsListener, null, null, List.of(source));
      Boolean result = task.call();

      for (Diagnostic<? extends JavaFileObject> d : diagnosticsListener.getDiagnostics())
         System.out.println(d.getKind() + ": " + d.getMessage(null));
      fileManager.close();
      if (!result)
      {
         System.out.println("Compilation failed.");
         System.exit(1);
      }

      var loader = new ByteArrayClassLoader(classFileObjects);
      Class<?> mainClass = loader.loadClass(mainClassName);
      mainClass.getMethod("main", String[].class).invoke(null, new Object[] { args });
   }

   /**
    * Builds the source for the class that implements the worksheet actions.
    * @param className the name of the class to be built
    * @param worksheet the file containing the worksheet instructions
    * @return a file object containing the source in a string builder
    */
   static JavaFileObject buildSource(String className, Path worksheet) 
         throws IOException
   {
      var builder = new StringBuilder();
      int n = className.lastIndexOf(".");
      if (n >= 0)
      {
         String packageName = className.substring(0, n); 
         className = className.substring(n + 1);
         builder.append("package ").append(packageName).append(";\n\n");
      }
      builder.append("import java.util.Scanner;");      
      builder.append("public class ").append(className).append(" {\n");
      builder.append("public static void main(String[] args) {\n");
      boolean verbatim = false;
      for (String line : Files.readAllLines(worksheet))
      {
         if (line.equals("```")) verbatim = !verbatim;
         else if (verbatim) builder.append(line).append("\n");
         else
         {
            String[] fragments = line.split("`");
            for (int i = 0; i < fragments.length; i++)
            {
               builder.append("System.out.print(");
               if (i % 2 == 0) 
                  builder.append("\"")
                     .append(fragments[i].replace("\\", "\\\\").replace("\"", "\\\""))
                     .append("\"");
               else
                  builder.append(fragments[i]);
               builder.append(");\n");
            }
            builder.append("System.out.println()\n;");
         }
      }
      
      builder.append("} }\n");
      return new StringSource(className, builder.toString());
   }
}