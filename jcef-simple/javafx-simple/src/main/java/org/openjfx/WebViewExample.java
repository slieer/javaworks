package org.openjfx;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebViewExample extends Application {
    @Override
    public void start(Stage stage) {
        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webView.setContextMenuEnabled(true);
        webEngine.setUserAgent("MyApp Web Browser 1.0");
        // 加载网页
        webEngine.load("https://www.oschina.net");
        Worker<Void> loadWorker = webEngine.getLoadWorker();
        loadWorker.stateProperty().addListener((obs, oldState, newState) -> {
            log.info("newState:{}", newState);
            if (newState == Worker.State.READY) {
                //document loading ready
            }
            if (newState == Worker.State.SCHEDULED) {
                //document loading started
            }

            if (newState == Worker.State.SUCCEEDED) {
                //document finished loading
            }
            if (newState == Worker.State.CANCELLED) {
                //document loading cancelled
            }
            if (newState == Worker.State.FAILED) {
                //document loading failed
            }
            if (newState == Worker.State.RUNNING) {
                //document loading in progress
            }


        });


        // 创建布局并将 WebView 添加到场景中
        BorderPane root = new BorderPane();
        root.setCenter(webView);

        Scene scene = new Scene(root, 800, 600);
        stage.setScene(scene);
        stage.setTitle("JavaFX WebView Example");
        stage.show();
    }
}