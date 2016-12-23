package com.zhai;

import org.openqa.selenium.WebDriver;

public class LocalFile {
	public static void loadLocalFile() {
		String url = "file:///opt/devTools/workspace/myGithub/javaworks/ctripGather/xiecheng.html";
		WebDriver selenium = Load.getWebDriver(url);
	}

	public static void main(String[] args) {
		loadLocalFile();
	}
}
