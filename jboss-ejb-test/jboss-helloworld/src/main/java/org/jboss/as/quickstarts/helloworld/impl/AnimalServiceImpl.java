package org.jboss.as.quickstarts.helloworld.impl;

import javax.ejb.Stateless;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.as.quickstarts.helloworld.service.animalService.CatLocalService;
import org.jboss.as.quickstarts.helloworld.service.animalService.CatRemoteService;
import org.jboss.as.quickstarts.helloworld.service.animalService.DogRemoteService;

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
