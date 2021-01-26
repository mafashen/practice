package com.mafashen.jvm.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Try With Resource 可以算是一个语法糖, 在编译时会生成finally, 或者在finally里增加调用 close 方法的指令 与 异常表
 *
 * @author mafashen
 * created on 2018/11/19.
 */
public class TryWithResource {

	public static void main(String[] args) {
	/*
		Exception in thread "main" java.lang.RuntimeException: Initial
		at com.mafashen.jvm.code.TryWithResource.main(TryWithResource.java:19)
		Suppressed: java.lang.RuntimeException: Foo2
			at com.mafashen.jvm.code.TryWithResource$Foo.close(TryWithResource.java:32)
			at com.mafashen.jvm.code.TryWithResource.main(TryWithResource.java:20)
		Suppressed: java.lang.RuntimeException: Foo1
			at com.mafashen.jvm.code.TryWithResource$Foo.close(TryWithResource.java:32)
			at com.mafashen.jvm.code.TryWithResource.main(TryWithResource.java:20)
		Suppressed: java.lang.RuntimeException: Foo0
			at com.mafashen.jvm.code.TryWithResource$Foo.close(TryWithResource.java:32)
			at com.mafashen.jvm.code.TryWithResource.main(TryWithResource.java:20)
	 */
		try (
			Foo foo0 = new Foo("Foo0"); // try-with-resources
//			 Foo foo1 = new Foo("Foo1");
//			 Foo foo2 = new Foo("Foo2")
		) {
			throw new RuntimeException("Initial");
		}
/*
 0 new #2 <com/mafashen/jvm/code/TryWithResource$Foo>
 3 dup
 4 ldc #3 <Foo0>
 6 invokespecial #4 <com/mafashen/jvm/code/TryWithResource$Foo.<init>>
 9 astore_1
10 aconst_null
11 astore_2
12 new #5 <java/lang/RuntimeException>
15 dup
16 ldc #6 <Initial>
18 invokespecial #7 <java/lang/RuntimeException.<init>>
21 athrow
22 astore_3
23 aload_3
24 astore_2
25 aload_3
26 athrow
27 astore 4
29 aload_1
30 ifnull 59 (+29)
33 aload_2
34 ifnull 55 (+21)
37 aload_1
38 invokevirtual #9 <com/mafashen/jvm/code/TryWithResource$Foo.close>
41 goto 59 (+18)
44 astore 5
46 aload_2
47 aload 5
49 invokevirtual #10 <java/lang/Throwable.addSuppressed>
52 goto 59 (+7)
55 aload_1
56 invokevirtual #9 <com/mafashen/jvm/code/TryWithResource$Foo.close>
59 aload 4
61 athrow

Exception table:
         from    to  target type
            12    22    22   Class java/lang/Throwable
            37    41    44   Class java/lang/Throwable
            12    29    27   any

*/
	}

	static class Foo implements AutoCloseable {
		private final String name;

		public Foo(String name) {
			this.name = name;
		}

		@Override
		public void close() {
			throw new RuntimeException(name);
		}
	}
}
