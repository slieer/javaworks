package logging;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;

import static java.lang.System.Logger.Level.*;


/**
 * A modification of the image viewer program that logs various events. Run as
 * java -Djava.util.logging.config.file=logging.properties logging.LoggingImageViewer
 * @version 1.1 2023-09-26
 * @author Cay Horstmann
 */
public class LoggingImageViewer
{
   
   
   public static void main(String[] args)
   {
      EventQueue.invokeLater(() ->
            {
               var frame = new ImageViewerFrame();
               frame.setTitle("LoggingImageViewer");
               frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               System.Logger logger = System.getLogger("com.horstmann.corejava");
               logger.log(INFO, "Showing frame");
               frame.setVisible(true);
            });
   }
}

/**
 * The frame that shows the image.
 */
class ImageViewerFrame extends JFrame
{
   private static final int DEFAULT_WIDTH = 300;
   private static final int DEFAULT_HEIGHT = 400;   

   private JLabel label;
   private static System.Logger logger = System.getLogger("com.horstmann.corejava");

   public ImageViewerFrame()
   {
      logger.log(TRACE, "Entering ImageViewerFrame()");      
      setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

      // set up menu bar
      var menuBar = new JMenuBar();
      setJMenuBar(menuBar);

      var menu = new JMenu("File");
      menuBar.add(menu);

      var openItem = new JMenuItem("Open");
      menu.add(openItem);
      openItem.addActionListener(new FileOpenListener());

      var exitItem = new JMenuItem("Exit");
      menu.add(exitItem);
      exitItem.addActionListener(new ActionListener()
         {
            public void actionPerformed(ActionEvent event)
            {
               logger.log(INFO, "Exiting.");
               System.exit(0);
            }
         });

      // use a label to display the images
      label = new JLabel();
      add(label);
      logger.log(TRACE, "Exiting ImageViewerFrame()");
   }

   private class FileOpenListener implements ActionListener
   {
      public void actionPerformed(ActionEvent event)
      {
         logger.log(TRACE, "Entering ImageViewerFrame.FileOpenListener.actionPerformed(%s)", 
               event);

         // set up file chooser
         var chooser = new JFileChooser();
         chooser.setCurrentDirectory(new File("."));

         // accept all files ending with .gif
         chooser.setFileFilter(new javax.swing.filechooser.FileFilter()
            {
               public boolean accept(File f)
               {
                  return f.getName().toLowerCase().endsWith(".gif") || f.isDirectory();
               }

               public String getDescription()
               {
                  return "GIF Images";
               }
            });

         // show file chooser dialog
         int r = chooser.showOpenDialog(ImageViewerFrame.this);

         // if image file accepted, set it as icon of the label
         if (r == JFileChooser.APPROVE_OPTION)
         {
            String name = chooser.getSelectedFile().getPath();
            logger.log(DEBUG, "Reading file %s", name);
            label.setIcon(new ImageIcon(name));
         }
         else logger.log(DEBUG, "File open dialog canceled.");
         logger.log(TRACE, "Exiting ImageViewerFrame.FileOpenListener.actionPerformed");
      }
   }
}
