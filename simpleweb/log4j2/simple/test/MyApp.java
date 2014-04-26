package simple.test;

// Import log4j classes.
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MyApp {
    public static Logger logger = LogManager.getLogger(MyApp.class.getName());

    public static void main(String[] args) {
        // Set up a simple configuration that logs on the console.
        logger.trace("Entering application.");
        Bar bar = new Bar();
        if (!bar.doIt()) {
            logger.error("Didn't do it.");
        }
        logger.trace("Exiting application.");
    }
}

class Bar {
    static Logger logger = LogManager.getLogger(Bar.class.getName());

    public boolean doIt() {
        logger.entry();
        logger.error("Did it again!");
        return logger.exit(false);
    }
}