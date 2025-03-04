package org.slieer.bTrace;

import org.openjdk.btrace.core.annotations.*;

import static org.openjdk.btrace.core.BTraceUtils.*;

/**
 * https://github.com/btraceio/btrace/wiki
 * https://blog.csdn.net/iteye_2259/article/details/82453010
 * https://zhuanlan.zhihu.com/p/384791323
 *
 * BTrace是什么？
 * BTrace使用Java的Attach技术，可以让我们无缝的将我们BTrace脚本挂到JVM上，
 *  通过脚本你可以获取到任何你想拿到的数据，在侵入性和安全性都非常可靠，特别是定位线上问题的神器。
 *
 * BTrace原理
 * BTrace是基于动态字节码修改技术（Hotswap）向目标程序的字节码注入追踪代码。
 *
 * 生产环境可以使用，但修改的字节码不会被还原，使用Btrace时，需要确保追踪的动作是只读的（即：追踪行为不能修改目标程序的状态）
 *  和有限的行为（即：追踪行为需要在有限的时间内终止）
 */
public class BtraceTest {
    @BTrace
    public static class TraceObject {

        @OnMethod(clazz = "com.zhaming.trace.btrace.ActionObject", method = "work", location = @Location(value = Kind.CALL, clazz = "/.*/", method = "/.*/"))
        public static void checkWhoCallMe() {
            println("check who ActionObject.work method:");
            jstack();
        }
    }

    /**
     * This script traces method entry into every method of
     * every class in javax.swing package! Think before using
     * this script -- this will slow down your app significantly!!
     */
    @BTrace public class AllMethods {
        @OnMethod(
                clazz="/javax\\.swing\\..*/",
                method="/.*/"
        )
        public static void m(@ProbeClassName String probeClass, @ProbeMethodName String probeMethod) {
            print(Strings.strcat("entered ", probeClass));
            println(Strings.strcat(".", probeMethod));
        }
    }
}
