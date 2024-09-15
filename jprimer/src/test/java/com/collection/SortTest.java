package com.collection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortTest {
    public static Logger log = LogManager.getLogger(SortTest.class.getName());

    /**
     * @param args
     */
    public static void main(String[] args) {
        listSort();
    }

    
    static void listSort(){
        List<String[]> result = new ArrayList<String[]>();
        result.add(new String[] { "str", "1" });
        result.add(new String[] { "str1", "2" });
        result.add(new String[] { "str23", "11" });
        result.add(new String[] { "str3", "-1" });
        result.add(new String[] { "strw", "1100" });

        Collections.sort(result, new Comparator<String[]>() {
            public int compare(String[] o1, String[] o2) {
                int o1Val = Integer.valueOf(o1[1]);
                int o2Val = Integer.valueOf(o2[1]);
                return o2Val - o1Val;
            }

        });

        // log.info("---refIds:" + refIds);
        for (int i = 0; result != null && i < result.size(); i++) {
            String[] str = (String[]) result.get(i);
            log.info(str[0] + "--" + str[1] + ";");
        }
        
    }
}
