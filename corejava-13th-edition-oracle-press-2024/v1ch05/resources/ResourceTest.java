package resources;

import java.io.*;
import java.net.*;
import javax.swing.*;

/**
 * @version 1.51 2023-08-16
 * @author Cay Horstmann
 */
public class ResourceTest
{
   public static void main(String[] args) throws IOException
   {
      Class cl = ResourceTest.class;
      URL aboutURL = cl.getResource("about.gif");
      var icon = new ImageIcon(aboutURL);

      InputStream stream = cl.getResourceAsStream("data/about.txt");
      var about = new String(stream.readAllBytes());

      InputStream stream2 = cl.getResourceAsStream("/corejava/title.txt");      
      var title = new String(stream2.readAllBytes()).strip();

      JOptionPane.showMessageDialog(null, about, title, JOptionPane.INFORMATION_MESSAGE, icon);
   }
}
