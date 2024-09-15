package slieer.commons.Chain.blogjava.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;

public class Command1 implements Command {
	public boolean execute(Context arg0) throws Exception {
		System.out.println("Command1 is done!");
		return false;
	}
}