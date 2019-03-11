package com.mafashen.jvm.code.instrumentation;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import javassist.ClassPool;
import javassist.CtBehavior;
import javassist.CtClass;

/**
 * @author mafashen
 * created on 2018/12/9.
 */
public class PerfMonXformer implements ClassFileTransformer{

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
							ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		System.out.println("Transforming " + className);
		byte[] transformed = null;
		ClassPool pool = ClassPool.getDefault();
		CtClass ctClass = null;
		try {
			ctClass = pool.makeClass(new ByteArrayInputStream(classfileBuffer ));
			if (ctClass.isInterface() == false){
				CtBehavior[] methods = ctClass.getDeclaredBehaviors();
				for (int i = 0; i < methods.length; i++) {
					CtBehavior method = methods[i];
					if (method.isEmpty() == false){
//						method.insertBefore("System.out.println(System.nanoTime());");
						method.insertBefore("long stime = System.nanoTime();");
						method.insertAfter("System.out.println(\" takes:\"+ (System.nanoTime() - stime));");
					}
				}
				transformed = ctClass.toBytecode();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (ctClass != null){
				ctClass.detach();
			}
		}
		return transformed;
	}
}
