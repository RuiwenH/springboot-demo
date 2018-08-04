package com.reven.jvm;

public class JvmPrintGcTest {
	public static void main(String[] args) {
		JvmPrintGcTest.test();
		System.out.println("最大堆：" + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
		System.out.println("空闲堆：" + Runtime.getRuntime().freeMemory() / 1024 / 1024 + "M");
		System.out.println("总的堆：" + Runtime.getRuntime().totalMemory() / 1024 / 1024 + "M");
	}

	static void test() {
		// jvm添加参数-XX:+PrintGC
		// -XX:+PrintGCDetails
		// -Xloggc:d:/gc.log
		// -Xmx20m -Xms5m -Xmn2m -XX:+PrintGCDetails
		byte[] bytes = null;
		for (int i = 0; i < 200; i++) {
			bytes = new byte[1 * 1024 * 1024];
		}
	}

}
