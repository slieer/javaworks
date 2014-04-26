package simple.foundation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class PropertiesTest {
    public static void main(String[] args) {
        Properties properties = new Properties();
        File f = new File("JavaSE/simple/foundation/test.properties");
        System.out.println(f.getAbsolutePath());
        try {
            properties.load(new FileInputStream(f));
            //properties.setProperty("c", "xxx");
            //properties.setProperty("a", "yyy");
            //properties.setProperty("china", "plus");
            properties.setProperty("by", "翟小斌");
            properties.store(new FileWriter(f), null);
            
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
