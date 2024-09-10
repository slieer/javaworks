package com.collection.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ListTest {
    List<String> list = new ArrayList<String>(); // Object基本数组
    List<Ob> llist = new LinkedList<Ob>(); // 双项链表

    static class Ob {
        int type;
        String value;

        public Ob(int type, String val) {
            this.type = type;
            this.value = val;
        }

        @Override
        public String toString() {
            return "Ob [type=" + type + ", value=" + value + "]";
        }
    }

    /**
     * throw java.util.ConcurrentModificationException
     */
    @Test
    public void testIteratorRemove() {
        List<Ob> list = new ArrayList<Ob>();
        initListData(list);

        for (Ob i : list) {
            if(i.type == 2){
                list.remove(i);
                System.out.println(i);
            }
        }
    }

    @Test
    public void testIter() {
        List<String> list = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            String str = i + "";
            list.add(str);
        }
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            System.out.println((String) it.next());
            it.remove();
        }
    }
    
    @Test
    public void testArrayRemove2(){
        List<Ob> list = new ArrayList<Ob>();
        initListData(list);

        for (int i = 0; i < list.size(); i++) {
            Ob o = list.get(i);
            if(o.type == 2){
                list.remove(o);
                System.out.println("remove:" + o);
                
                i = i -1;
            }
        }
        
        System.out.println("list:" + list);
        
    }

    @Test
    public void testLinkedRemove(){
        List<Ob> list = new LinkedList<Ob>();
        initListData(list);

        for (int i = 0; i < list.size(); i++) {
            Ob o = list.get(i);
            if(o.type == 2){
                list.remove(o);
                System.out.println("remove:" + o);
                
                i = i -1;
            }
        }
        
        System.out.println("list.size():" + list.size());
        
    }
    
    @Test
    public void test(){
        List<Ob> list = new ArrayList<Ob>();
        initListData(list);
        
        Iterator<Ob> item = list.iterator();
        while(item.hasNext()){
            Ob downloadItem = item.next();
            if (downloadItem.type == 1){
                item.remove();
            }
        }
        
        System.out.println("list.size():" + list);
    }
    
    private void initListData(List<Ob> list) {
        list.add(new Ob(2, "d"));
        list.add(new Ob(2, "d"));
        
        list.add(new Ob(1, "a"));
        list.add(new Ob(1, "b"));
        list.add(new Ob(2, "c"));
        list.add(new Ob(2, "d"));
        list.add(new Ob(2, "d"));
        list.add(new Ob(2, "d"));
        list.add(new Ob(1, "e"));
        
        list.add(new Ob(2, "d"));
        list.add(new Ob(1, "e"));
        list.add(new Ob(1, "e"));
        list.add(new Ob(2, "d"));
        
        list.add(new Ob(2, "d"));
        list.add(new Ob(1, "e"));
        list.add(new Ob(2, "d"));
        
    }
    
    @Test
    public void testArr(){
        Long[] idArr = new Long[10];
        List<Long> list = Arrays.asList(idArr);
        
        System.out.println(list.size());
        System.out.println(list);
    }
    
    public static void main(String[] args) {

    }
}
