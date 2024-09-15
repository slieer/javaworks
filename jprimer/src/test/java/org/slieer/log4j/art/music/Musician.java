package org.slieer.log4j.art.music;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 音乐家
 * @author slieer
 * Create Date 2014-2-8
 * version 1.0
 */
public class Musician {
    static Logger logger = LogManager.getLogger(Musician.class.getName());
    
    public void wirteWorks(){        
        logger.error("error 1{} , {}", "Moves Like Jagger", "Love Story");
        
        logger.warn("warn 1{} , {}", "Moves Like Jagger", "Love Story");
        logger.warn("warn 2{} , {}", "Moves Like Jagger", "Love Story");
        logger.warn("warn 3{} , {}", "Moves Like Jagger", "Love Story");
        
        logger.info("stat info 1{} , {}", "Moves Like Jagger", "Love Story");
        logger.info("stat info 2{} , {}", "Moves Like Jagger", "Love Story");
    }
    
    public void show(){
        logger.info("stat {} , {}", "info ---Moves Like Jagger---", "---Love Story---");
        logger.warn(" {} , {}", "warn 1---Moves Like Jagger---", "---Love Story---");
        logger.warn(" {} , {}", "warn 2---Moves Like Jagger---", "---Love Story---");
        logger.info("stat {} , {}", "info 1---Moves Like Jagger---", "---Love Story---");
        logger.info(" {} , {}", "info 2---Moves Like Jagger---", "---Love Story---");
    }
}
