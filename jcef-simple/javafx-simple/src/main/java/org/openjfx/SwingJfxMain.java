package org.openjfx;

import javafx.application.Application;
import javafx.embed.swing.SwingNode;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;

/**
 * Swing结合JavaFX Application
 */
public class SwingJfxMain extends Application {

    @Override
    public void start(Stage primaryStage) {
        // 创建一个Swing组件
        JButton button = new JButton("Click me!");
        button.setPreferredSize(new Dimension(200, 50));
        button.addActionListener(e -> button.setText("Clicked!" + LocalDateTime.now().toString()));

        JTextPane textPane = new JTextPane();
        textPane.setText("Hello, Swing!");

        JComponent frame = new JPanel();
        frame.setSize(300, 200);
        frame.setVisible(true);
        frame.add(textPane);
        frame.add(button);
        frame.setVisible(true);

        // 创建一个SwingNode并将Swing组件嵌入其中
        SwingNode swingNode = new SwingNode();
        SwingUtilities.invokeLater(() -> swingNode.setContent(frame));

        // 创建一个JavaFX布局并将SwingNode添加到其中
        StackPane root = new StackPane();
        root.getChildren().add(swingNode);

        // 创建一个场景并将布局添加到其中
        Scene scene = new Scene(root, 300, 200);
        // 设置Swing组件的大小
        button.setPreferredSize(new Dimension(100, 50));
        // 更新SwingNode的大小
        swingNode.resizeRelocate(0, 0, 100, 50);
        // 显示窗口
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}