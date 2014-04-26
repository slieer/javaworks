package simple.foundation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.Scanner;

import org.junit.Test;

public class ProcessTest {
    @Test
    public void basic() {
        try {
            Process proc = Runtime.getRuntime().exec("notepad");

            proc.getErrorStream();
            proc.getInputStream();
            proc.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void ieTest() {
        String cmdStr = "\"C:/Program Files/Internet Explorer/IEXPLORE.EXE\"";
        String message = "www.google.com";
        try {
            String[] cmd = { cmdStr, message };
            Process proc = Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @Test
    public void processList() {
        Scanner br = null;
        try {
            Process proc = Runtime.getRuntime().exec("tasklist");
            br = new Scanner(new BufferedReader(
                    new InputStreamReader(proc.getInputStream())));

            while (br.hasNextLine()) {
                String line = br.nextLine();;
                String str = new String(line.getBytes("GBK"));
                System.out.println(str);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void execCamm() throws Exception{
        String command = " stat  /usr/local/mobee/data/appstore/app/2013/09/com.mipt.explorer/filemanager_01.02.02.apk";
        Process process = Runtime.getRuntime().exec(command);

        InputStreamReader ir=new InputStreamReader(process.getInputStream());
        LineNumberReader input = new LineNumberReader (ir);
        String line = null;
        while ((line = input.readLine ()) != null){
         System.out.println(line);
        }
    }
    
    public static void main(String[] args) {
    }
}
