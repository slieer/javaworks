package org.jboss.as.quickstarts.helloworld.service.animalService;

import javax.ejb.Remote;

@Remote
public interface CatRemoteService {
	public void marrige();
}
