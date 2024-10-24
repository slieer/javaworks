package bytecodeAnnotations;

import java.io.*;
import java.nio.file.*;

import org.objectweb.asm.*;
import org.objectweb.asm.commons.*;

/**
 * Adds "entering" logs to all methods of a class that have the LogEntry annotation.
 * @version 1.22 2023-12-01
 * @author Cay Horstmann
 */
public class EntryLogger extends ClassVisitor
{
   private String className;
   
   /**
    * Constructs an EntryLogger that inserts logging into annotated methods of a given class.
    */
   public EntryLogger(ClassWriter writer, String className)
   {
      super(Opcodes.ASM8, writer);
      this.className = className;
   }
   
   public MethodVisitor visitMethod(int access, String methodName, String desc, 
         String signature, String[] exceptions) 
   {
      MethodVisitor mv = cv.visitMethod(access, methodName, desc, signature, exceptions);   
      return new AdviceAdapter(Opcodes.ASM8, mv, access, methodName, desc) 
      {          
         private String loggerName;
          
         public AnnotationVisitor visitAnnotation(String desc, boolean visible) 
         {
            return new AnnotationVisitor(Opcodes.ASM8) 
               {
                  public void visit(String name, Object value) 
                  {
                     if (desc.equals("LbytecodeAnnotations/LogEntry;") 
                           && name.equals("logger")) 
                        loggerName = value.toString();                    
                  }
               };
         }   
          
         public void onMethodEnter() 
         {
            if (loggerName != null) 
            {
               /*
               visitLdcInsn(loggerName);
               visitMethodInsn(INVOKESTATIC, "java/util/logging/Logger", "getLogger", 
                  "(Ljava/lang/String;)Ljava/util/logging/Logger;", false);
               visitLdcInsn(className);
               visitLdcInsn(methodName);
               visitMethodInsn(INVOKEVIRTUAL, "java/util/logging/Logger", "entering", 
                  "(Ljava/lang/String;Ljava/lang/String;)V", false);
               */
               visitLdcInsn(loggerName);
               visitMethodInsn(INVOKESTATIC, "java/lang/System", "getLogger", 
                  "(Ljava/lang/String;)Ljava/lang/System$Logger;", false);
               visitFieldInsn(Opcodes.GETSTATIC, "java/lang/System$Logger$Level", "INFO", 
                  "Ljava/lang/System$Logger$Level;");
               visitLdcInsn("Entering {0}.{1}");

               // Create an array of Objects with a length of 2
               mv.visitInsn(Opcodes.ICONST_2);  // array length
               mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");  // array type

               // Add two objects to the array
               mv.visitInsn(Opcodes.DUP);  // duplicate array reference
               mv.visitInsn(Opcodes.ICONST_0);  // index 0
               visitLdcInsn(className);
               mv.visitInsn(Opcodes.AASTORE);  // store in array

               mv.visitInsn(Opcodes.DUP);  // duplicate array reference
               mv.visitInsn(Opcodes.ICONST_1);  // index 1
               visitLdcInsn(methodName);
               mv.visitInsn(Opcodes.AASTORE);  // store in array
                              
               visitMethodInsn(INVOKEINTERFACE, "java/lang/System$Logger", "log", 
                  "(Ljava/lang/System$Logger$Level;Ljava/lang/String;[Ljava/lang/Object;)V", 
                  true);
               loggerName = null;
            }                  
         }
      };
   }

   /**
    * Adds entry logging code to the given class.
    * @param args the name of the class file to patch
    */
   public static void main(String[] args) throws IOException
   {
      if (args.length == 0)
      {
         System.out.println("USAGE: java bytecodeAnnotations.EntryLogger classfile");
         System.exit(1);
      }
      Path path = Path.of(args[0]);
      var reader = new ClassReader(Files.newInputStream(path));
      var writer = new ClassWriter(
         ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
      var entryLogger = new EntryLogger(writer, 
         path.toString().replace(".class", "").replaceAll("[/\\\\]", "."));
      reader.accept(entryLogger, ClassReader.EXPAND_FRAMES);
      Files.write(Path.of(args[0]), writer.toByteArray());
   }   
}
