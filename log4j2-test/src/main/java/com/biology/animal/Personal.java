package com.biology.animal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.art.music.Musician;
import com.biology.plant.Rice;

public class Personal {
    Logger log = LogManager.getLogger();
    
    public Personal(){
        eat();
        work();
    }
    
    public void thinking(String type){
        log.info("hi,personal thinking..." + type);
        
        log.error("thinking catastrophe");
    }
    
    void language(){
        
    }
    
    void eat(){
        Rice r = new Rice();
        r.root();
        r.leaf();
        r.grain();
        
        log.warn("eat, eat !");
    }
    
    void work(){
        Musician m = new Musician();
        m.wirteWorks();
        m.show();
    }
}
