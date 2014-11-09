package com.annotion.simple;

@DescriptionAnnotion("javaeye,做最棒的软件开发交流社区")
public class NameBean {
	@NameAnnotinon(originate = "创始人:robbin", community = "javaEye")
	public String getName() {
		return null;
	}

	@NameAnnotinon(originate = "创始人:江南白衣", community = "springside")
	public String getName2() {
		return "借用两位的id一用,写这一个例子,请见谅!";
	}

}