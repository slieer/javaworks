package simple.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
public class Helloworld {
    private static Logger logger = LogManager.getLogger("HelloWorld");

    public static void main(String[] args) {
        logger.info("Hello, World!");
        logger.debug("Logging in user {} with id {}", "zhai", "007");
    }
}
