package slieer.commons.Chain.blogjava;

import org.apache.commons.chain.Context;
import org.apache.commons.chain.Filter;

public class Filter1 implements Filter {

	public boolean postprocess(Context arg0, Exception arg1) {
		System.out.println("Filter1 is after done!");
		
		return false;
	}

	public boolean execute(Context arg0) throws Exception {
		System.out.println("Filter1 is done!");

		return false;
	}

}