package com.slieer.commons.Chain.blogjava.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Command2 implements Command {
	public boolean execute(Context arg0) throws Exception {
		System.out.println("Command2 is done!");
		return false;
	}
}