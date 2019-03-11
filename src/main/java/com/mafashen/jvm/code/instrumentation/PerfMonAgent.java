package com.mafashen.jvm.code.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @author mafashen
 * created on 2018/12/9.
 */
public class PerfMonAgent {
	static private Instrumentation inst = null;

	public static void premain(String agentArgs, Instrumentation _inst){
		System.out.println("PerfonAgent.permain() entry");
		inst = _inst;
		ClassFileTransformer trans = new PerfMonXformer();
		System.out.println("Adding a PerfMonXformer instance to the jvm");
		inst.addTransformer(trans);
	}
}
