package com.slieer.commons.Chain.blogjava;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.apache.commons.chain.impl.ChainBase;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Test;

import com.slieer.commons.Chain.blogjava.command.Command1;
import com.slieer.commons.Chain.blogjava.command.Command2;
import com.slieer.commons.Chain.blogjava.command.Command3;

public class MyCommandTest extends ChainBase {

	// 增加命令的顺序也决定了执行命令的顺序
	public MyCommandTest() {
		addCommand(new Command1());
		addCommand(new Command2());
		addCommand(new Command3());
	}

	@Test
	public void testChain() throws Exception {
		Command process = new MyCommandTest();
		Context ctx = new ContextBase();
		process.execute(ctx);
	}

	
	static final String cfgFile = "./chain-cfg.xml";
	@Test
	public void testConfig() throws Exception {
		//MyCommandTest loader = new MyCommandTest();
		
		ConfigParser parser = new ConfigParser();
		//parser.parse(this.getClass().getResource(cfgFile));
		parser.parse(MyCommandTest.class.getResource(cfgFile));
		Catalog catalog = CatalogFactoryBase.getInstance().getCatalog();
		// 加载 Chain
		Command cmd = catalog.getCommand("CommandChain");
		Context ctx = new ContextBase();
		cmd.execute(ctx);
		
		// 加载 Command
		cmd = catalog.getCommand("command4");
		cmd.execute(ctx);

	}
	
}