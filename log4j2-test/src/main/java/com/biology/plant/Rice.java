package com.biology.plant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Rice {
    Logger log = LogManager.getLogger();
    
    public void root(){
        log.info("rooting...");
        
    }
    
    public void leaf(){
        log.info("leaf ...");
        
    }
    
    public void grain(){
        log.info("grain ...");
    }
}
