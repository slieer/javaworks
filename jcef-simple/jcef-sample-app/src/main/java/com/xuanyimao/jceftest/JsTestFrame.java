/**  
 * http://www.xuanyimao.com
 * @author:liuming
 * @date: 2019年9月8日
 * @version V1.0 
 */
package com.xuanyimao.jceftest;

import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.browser.CefMessageRouter.CefMessageRouterConfig;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefAppHandlerAdapter;
import org.cef.handler.CefMessageRouterHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * @Description:Js与Java代码交互测试
 * @author liuming
 */
public class JsTestFrame extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9131822589633996915L;

	public static void main(String[] args) {
		String url=System.getProperty("user.dir")+"/jstest.html";
		new JsTestFrame(url);
	}
	
	public JsTestFrame(String url) {
		//是否Linux系统
		boolean useOSR=OS.isLinux();
		//是否透明
		boolean isTransparent=false;
		//添加Handler，在CEFAPP状态为终止时退出程序
		CefApp.addAppHandler(new CefAppHandlerAdapter(null) {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });
		
        CefSettings settings = new CefSettings();
        settings.windowless_rendering_enabled = useOSR;
		//获取CefApp实例
        CefApp cefApp=CefApp.getInstance(settings);
        //创建客户端实例
        CefClient cefClient = cefApp.createClient();
        
        //添加一个JS交互
        jsActive(cefClient);
        
        //创建浏览器实例
        CefBrowser cefBrowser = cefClient.createBrowser(url, useOSR, isTransparent);
		
        //将浏览器UI添加到窗口中
		
		getContentPane().add(cefBrowser.getUIComponent(), BorderLayout.CENTER);
		
		pack();
		setTitle("测试JCEF-JS与JAVA代码交互");
        setSize(800, 600);
        setVisible(true);
        //添加一个窗口关闭监听事件
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
	}
	
	/**
	 * 添加js交互
     * @author:liuming
     */
    public void jsActive(CefClient client) {
    	 //配置一个查询路由,html页面可使用 window.java({}) 和 window.javaCancel({}) 来调用此方法
		 CefMessageRouterConfig cmrc=new CefMessageRouterConfig("java","javaCancel");
		 //创建查询路由
		 CefMessageRouter cmr=CefMessageRouter.create(cmrc);
		 cmr.addHandler(new CefMessageRouterHandler() {
			
			@Override
			public void setNativeRef(String str, long val) {
				System.out.println(str+"  "+val);
			}
			
			@Override
			public long getNativeRef(String str) {
				System.out.println(str);
				return 0;
			}
			
			@Override
			public void onQueryCanceled(CefBrowser browser, CefFrame frame, long query_id) {
				System.out.println("取消查询:"+query_id);
			}
			
			@Override
			public boolean onQuery(CefBrowser browser, CefFrame frame, long query_id, String request, boolean persistent,
					CefQueryCallback callback) {
				System.out.println("request:"+request+"\nquery_id:"+query_id+"\npersistent:"+persistent);
				
				callback.success("Java后台处理了数据");
				return true;
			}
		}, true);
		client.addMessageRouter(cmr);
	}
}
