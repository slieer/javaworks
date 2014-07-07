package org.jboss.as.quickstarts.helloworld.service.animalService;

import javax.ejb.Remote;

@Remote
public interface DogRemoteService {
	public void wangwang();
}
