package org.jboss.as.quickstarts.helloworld;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.as.quickstarts.helloworld.stuService.SchoolService;
import org.jboss.as.quickstarts.helloworld.stuService.StudentService;
import org.jboss.as.quickstarts.helloworld.stuService.TecherService;

/**
 * Session Bean implementation class GradenService
 */
@Stateless
@Local({SchoolService.class, StudentService.class})
@Remote(TecherService.class)
public class StuServiceImpl implements SchoolService, StudentService, TecherService{

   public StuServiceImpl() {
    }

	@Override
	public String getTechName() {
		return "zhai";
	}

	@Override
	public String getShool() {
		return "huang";
	}

	@Override
	public void getTech() {		
	}

	@Override
	public void getStudentList() {
		// TODO Auto-generated method stub
		
	}

}
