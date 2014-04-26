package com.collection.set;

import java.util.HashSet;
import java.util.Iterator;

public class HashSetTest {
    static class E {
        int i = 0;
        String str = "";
        public E(int i, String str) {
            super();
            this.i = i;
            this.str = str;
        }
        @Override
        public String toString() {
            return "E [i=" + i + ", str=" + str + "]";
        }
    }
    
    public static void main(String[] args) {
        HashSet<E> h = new HashSet<HashSetTest.E>();
        h.add(new E(1,"sd"));
        h.add(new E(-1,"wz"));
        h.add(new E(12,"23"));
        h.add(new E(122,"s"));
        h.add(new E(11,"s"));
        h.add(new E(3,"tes"));
        
        h.add(new E(-2,"s"));
        h.add(new E(31,"s"));
        h.add(new E(13,"tes"));
        
        for(E e : h){
            System.out.print("," + e);
        }
        
        System.out.println();
        Iterator< E> it = h.iterator();
        while(it.hasNext()){
            System.out.print("," + it.next());
        }
        System.out.println();
    }
}
