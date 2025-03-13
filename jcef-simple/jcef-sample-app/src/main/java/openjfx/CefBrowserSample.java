package openjfx;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;

import javax.swing.*;

/**
 * 可能出现的错误：
 * Error: JavaFX runtime components are missing, and are required to run this application
 *
 * 解决方法： 必须声明 module-info.java 文件，并且文件内容如下：
 *
 * module openjfx {
 *    requires javafx.web;
*     requires javafx.swing;
 *    requires static lombok;
 *    requires org.slf4j;
 *
 * }
 */
public class CefBrowserSample extends Application {

    @Override
    public void start( Stage primaryStage ) {
        Pane root = new Pane();
        SwingNode swingNode = new SwingNode();
        root.getChildren().add( swingNode );
        Scene scene = new Scene( root, 500, 500 );
        primaryStage.setTitle( "JCEF Example" );
        primaryStage.setScene( scene );

        CefApp app = CefApp.getInstance();
        CefClient client = app.createClient();
        CefBrowser browser = client.createBrowser( "www.baidu.com", true, false );
        swingNode.setContent( (JPanel) browser.getUIComponent() );

        primaryStage.show();
    }

    public static void main( String[] args ) {
        launch( args );
    }

}