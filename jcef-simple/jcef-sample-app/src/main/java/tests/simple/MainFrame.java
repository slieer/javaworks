// Copyright (c) 2014 The Chromium Embedded Framework Authors. All rights
// reserved. Use of this source code is governed by a BSD-style license that
// can be found in the LICENSE file.

package tests.simple;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefApp.CefAppState;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefDisplayHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.Serial;

/**
 * This is a simple example application using JCEF.
 * It displays a JFrame with a JTextField at its top and a CefBrowser in its
 * center. The JTextField is used to enter and assign an URL to the browser UI.
 * No additional handlers or callbacks are used in this example.
 * <p>
 * The number of used JCEF classes is reduced (nearly) to its minimum and should
 * assist you to get familiar with JCEF.
 * <p>
 * For a more feature complete example have also a look onto the example code
 * within the package "tests.detailed".
 */
public class MainFrame extends JFrame {
    @Serial
    private static final long serialVersionUID = -5570653778104813836L;
    private final CefApp cefApp_;
    private final CefClient client_;
    private final CefBrowser browser_;
    private final Component browserUI_;
    private boolean browserFocus_ = true;
    private JFrame fullscreenFrame_;
    private final JTextField address_;

    private MainFrame(String startURL, boolean useOSR, boolean isTransparent) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        var builder = new CefAppBuilder();
        builder.setInstallDir(new File("/opt/workspace/javaworks/jcef-bundle"));
        var cefSettings = builder.getCefSettings();
        // Enable OSR for better performance
        cefSettings.windowless_rendering_enabled = useOSR;
        cefSettings.remote_debugging_port = 8081;
        cefSettings.log_severity = CefSettings.LogSeverity.LOGSEVERITY_INFO;
        cefSettings.user_agent = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/133.0.0.0 Safari/537.36";
        builder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(org.cef.CefApp.CefAppState state) {
                // Shutdown the app if the native CEF part is terminated
                if (state == CefAppState.TERMINATED) System.exit(0);
            }
        });

        cefApp_ = builder.build();
        client_ = cefApp_.createClient();
        browser_ = client_.createBrowser(startURL, useOSR, isTransparent);
        browserUI_ = browser_.getUIComponent();

        address_ = new JTextField(startURL, 100);
        address_.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                browser_.loadURL(address_.getText());
            }
        });

        // Update the address field when the browser URL changes.
        client_.addDisplayHandler(new CefDisplayHandlerAdapter() {
            @Override
            public void onAddressChange(CefBrowser browser, CefFrame frame, String url) {
                address_.setText(url);
            }

            @Override
            public void onFullscreenModeChange(CefBrowser browser, boolean fullscreen) {
                setBrowserFullscreen(fullscreen);
            }
        });

        // Clear focus from the browser when the address field gains focus.
        address_.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (!browserFocus_) return;
                browserFocus_ = false;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                address_.requestFocus();
            }
        });

        // Clear focus from the address field when the browser gains focus.
        client_.addFocusHandler(new CefFocusHandlerAdapter() {
            @Override
            public void onGotFocus(CefBrowser browser) {
                if (browserFocus_) return;
                browserFocus_ = true;
                KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
                browser.setFocus(true);
            }

            @Override
            public void onTakeFocus(CefBrowser browser, boolean next) {
                browserFocus_ = false;
            }
        });

        // (5) All UI components are assigned to the default content pane of this
        //     JFrame and afterwards the frame is made visible to the user.
        getContentPane().add(address_, BorderLayout.NORTH);
        getContentPane().add(browserUI_, BorderLayout.CENTER);
        pack();
        setSize(800, 600);
        setVisible(true);

        // (6) To take care of shutting down CEF accordingly, it's important to call
        //     the method "dispose()" of the CefApp instance if the Java
        //     application will be closed. Otherwise you'll get asserts from CEF.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CefApp.getInstance().dispose();
                dispose();
            }
        });
    }

    public void setBrowserFullscreen(boolean fullscreen) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (fullscreen) {
                    if (fullscreenFrame_ == null) {
                        fullscreenFrame_ = new JFrame();
                        fullscreenFrame_.setUndecorated(true);
                        fullscreenFrame_.setResizable(true);
                    }
                    GraphicsConfiguration gc = MainFrame.this.getGraphicsConfiguration();
                    fullscreenFrame_.setBounds(gc.getBounds());
                    gc.getDevice().setFullScreenWindow(fullscreenFrame_);

                    getContentPane().remove(browserUI_);
                    fullscreenFrame_.add(browserUI_);
                    fullscreenFrame_.setVisible(true);
                    fullscreenFrame_.validate();
                } else {
                    fullscreenFrame_.remove(browserUI_);
                    fullscreenFrame_.setVisible(false);
                    getContentPane().add(browserUI_, BorderLayout.CENTER);
                    getContentPane().validate();
                }
            }
        });
    }

    public static void main(String[] args) throws UnsupportedPlatformException, CefInitializationException, IOException, InterruptedException {
        // Perform startup initialization on platforms that require it.
//        if (!CefApp.startup(args)) {
//            System.out.println("Startup initialization failed!");
//            return;
//        }

        // The simple example application is created as anonymous class and points
        // to Google as the very first loaded page. Windowed rendering mode is used by
        // default. If you want to test OSR mode set |useOsr| to true and recompile.
        boolean useOsr = false;
        new MainFrame("http://www.baidu.com", useOsr, false);
    }
}
