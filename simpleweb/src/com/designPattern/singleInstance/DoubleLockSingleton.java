package com.designPattern.singleInstance;

/**
 * 双重检查锁机制
 * 在第一次获取的时候才构造对象
 * 
 * 文章《双重检查锁定及单例模式》 2004年 5月 01日,java5.0
 * http://www.ibm.com/developerworks/cn/java/j-dcl.html
 * 中谈到了非常多演变的双重锁机制带来的问题，
 * 包括比较难以理解的指令重排序机制等。
 * 总之就是双重检查锁机制仍然对导致错误问题而不是性能问题。
 * 
 * 持续改进 {@link DoubleLockSingleton1}
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class DoubleLockSingleton {

    private static volatile DoubleLockSingleton instance = null;

    private DoubleLockSingleton() {
    }

    public static DoubleLockSingleton getInstance() {
        if (instance == null) {
            synchronized (DoubleLockSingleton.class) {
                if (instance == null) {
                    instance = new DoubleLockSingleton();
                }
            }
        }
        return instance;
    }
}