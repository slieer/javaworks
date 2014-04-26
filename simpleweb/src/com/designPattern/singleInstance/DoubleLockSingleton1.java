package com.designPattern.singleInstance;

/**
 * 改进的双重检查锁机制, 
 * 静态变量增加了volatile修饰
 * 
 * 另外一种单例构造方法 {@link Z_HolderSingleton}
 * @author slieer
 * Create Date 2012-2-8
 * version 1.0
 */
public class DoubleLockSingleton1 {
        private static volatile DoubleLockSingleton1 instance = null;

        private DoubleLockSingleton1() {
        }

        public static DoubleLockSingleton1 getInstance() {
            if (instance == null) {
                synchronized (DoubleLockSingleton.class) {
                    if (instance == null) {
                        instance = new DoubleLockSingleton1();
                    }
                }
            }
            return instance;
        }
  }
