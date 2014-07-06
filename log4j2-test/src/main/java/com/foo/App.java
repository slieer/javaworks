package com.foo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// Import log4j classes.

import com.biology.animal.Personal;

public class App {

    // Define a static logger variable so that it references the
    // Logger instance named "MyApp".
    static Logger logger = LogManager.getLogger(App.class.getName());

    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.

        logger.trace("Entering application.");
        Bar bar = new Bar();
        if (!bar.doIt()) {
            logger.error("Didn't do it.");
        }
        logger.trace("Exiting application.");
        
        
        Personal p = new Personal();
        p.thinking("programming...");
    }
}