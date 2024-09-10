package org.jboss.as.quickstarts.helloworld.service.animalService;

import javax.ejb.Local;

@Local
public interface CatLocalService {
	public void hand();
}
