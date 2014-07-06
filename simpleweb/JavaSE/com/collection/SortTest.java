package com.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class SortTest {

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
            @Override
            public int compare(String[] o1, String[] o2) {
                int o1Val = Integer.valueOf(o1[1]);
                int o2Val = Integer.valueOf(o2[1]);
                return o2Val - o1Val;
            }

        });

        Log log = LogFactory.getLog(SortTest.class);
        // log.info("---refIds:" + refIds);
        for (int i = 0; result != null && i < result.size(); i++) {
            String[] str = (String[]) result.get(i);
            log.info(str[0] + "--" + str[1] + ";");
        }
        
    }
}
