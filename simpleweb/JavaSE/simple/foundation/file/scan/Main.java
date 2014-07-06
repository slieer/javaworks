
package simple.foundation.file.scan;

import static java.lang.System.out;

import java.io.File;
import java.util.Iterator;

public class Main {
    public static void main(String[] args) {

        String dir = "F:\\javatutorial\\tutorial-javaSE6\\tutorial";
        dir = "E:/BorqsWorkspace/172.16.1.203_thunk/android/ott/Codes/filemanager-new";
        // DirScanner.FilenameFilter d = new DirScanner.FilenameFilter("html");

        try {
            //Iterator<File> l = DirScanner.process(new File(dir)).iterator();
            Iterator<File> l = DirScanner.process(new File(dir), "java").iterator();
            while (l.hasNext()) {
                out.println(l.next());
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
