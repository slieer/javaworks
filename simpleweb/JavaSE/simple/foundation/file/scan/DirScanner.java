
package simple.foundation.file.scan;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class DirScanner {
    private static final int N = 10000;
    private static List<File> filesContainer = new ArrayList<File>(N);

    // private static FileFilter filteCondition;

    /**
     * @author slieer,2007-8-14
     * @param file 一个可读取的目录；
     * @return 无条件对一个目录进行遍历；
     * @throws MyException
     */
    public static List<File> process(File file) throws Exception {
        LinkedList<File> dir = new LinkedList<File>();
        File[] tempFile = null;

        if (file.isDirectory() && file.canRead()) {
            tempFile = file.listFiles();
        } else {
            throw new Exception("directory cann't read or this is a file!");
        }
        do {
            for (int i = 0; i < tempFile.length; i++) {
                if (tempFile[i].isFile()) {
                    
                    filesContainer.add(tempFile[i]);
                } else {
                    //System.out.println(tempFile[i]);
                    dir.add(tempFile[i]);
                }
            }
            if (!dir.isEmpty())
                tempFile = dir.remove().listFiles();

        } while (!dir.isEmpty());

        return filesContainer;
    }

    /**
     * 这个模块可以重构！充分使用FileFilter接口；
     * 
     * @param file
     * @param fileExtendName
     * @return
     * @throws MyException
     */
    public static List<File> process(File file, String fileExtendName) throws Exception {
        List<File> fileList = process(file);
        List<File> objectFiles = new ArrayList<File>();
        // FilenameFilter filter1 = new FilenameFilter("java");

        for (File f : fileList) {
            if(f.isFile()){
                int subIndex = f.getName().indexOf(".") + 1;
                
                if (f.getName().substring(subIndex).equalsIgnoreCase(fileExtendName)) {
                    objectFiles.add(f);
                }
            }
        }

        return objectFiles;
    }

    /**
     * @category 根据文件的扩展名进行过滤；
     * @author slieer
     */
    static class FilenameFilter implements FileFilter {
        private String fileName = null;

        public FilenameFilter(String fileName) {
            this.fileName = fileName;
        }

        public boolean accept(File pathname) {
            if (pathname.isFile()) {
                String fileName = pathname.getAbsolutePath();
                // int index = fileName.charAt('.');
                int index = fileName.indexOf(".");
                String extendName = fileName.substring(index + 1);

                return extendName.equalsIgnoreCase(this.fileName);
            } else {
                return false;
            }
        }
    }
}
