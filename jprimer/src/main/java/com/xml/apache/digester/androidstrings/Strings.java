package com.xml.apache.digester.androidstrings;

import java.util.List;

import org.apache.commons.digester3.Digester;

public class Strings {
    public void digest() {
        Digester digester = new Digester();
        // 注意，此处并没有象上例一样使用push，是因为此处从根元素创建了一个对//象实例
        digester.addObjectCreate("strings", string.class);
        // 将< academy >的属性与对象的属性关联
        digester.addSetProperties("string");
        digester.addObjectCreate("academy/name", String.class);
        digester.addSetProperties("academy/value");
    }
    
    class strings{
        List<string> string;

        public List<string> getString() {
            return string;
        }

        public void setString(List<string> string) {
            this.string = string;
        }
    }
    
    class string implements Comparable<string>{
        private String name;
        private String value;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getValue() {
            return value;
        }
        public void setValue(String value) {
            this.value = value;
        }
        @Override
        public int compareTo(string o) {
            return name.compareTo(o.name);
        }
    }
}
