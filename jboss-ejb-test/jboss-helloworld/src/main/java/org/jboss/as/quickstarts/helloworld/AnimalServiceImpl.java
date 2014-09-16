package org.jboss.as.quickstarts.helloworld;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.as.quickstarts.helloworld.animalService.CatLocalService;
import org.jboss.as.quickstarts.helloworld.animalService.CatRemoteService;
import org.jboss.as.quickstarts.helloworld.animalService.DogRemoteService;

@Stateless
public class AnimalServiceImpl implements CatLocalService, CatRemoteService, DogRemoteService{
	Logger log = LogManager.getLogger();
	
	@Override
	public void marrige() {
		
	}

	@Override
	public void hand() {
		
	}

	@Override
	public void wangwang() {
		// TODO Auto-generated method stub
		
	}

}
