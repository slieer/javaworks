package slieer.commons.BeanUtils.userguide;

import java.util.HashMap;

import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.WrapDynaBean;
import slieer.commons.BeanUtils.userguide.bean.Address;
import slieer.commons.BeanUtils.userguide.bean.Address.AddrType;
import slieer.commons.BeanUtils.userguide.bean.Employee;


public class DynaBeanTest {
	public void testBasic() throws IllegalAccessException, InstantiationException{
		DynaProperty[] props = new DynaProperty[]{
		        new DynaProperty("address", java.util.Map.class),
		        new DynaProperty("subordinate", Employee[].class),
		        new DynaProperty("firstName", String.class),
		        new DynaProperty("lastName",  String.class)
		      };
		    BasicDynaClass dynaClass = new BasicDynaClass("employee", null, props);

		    DynaBean employee = dynaClass.newInstance();
		    employee.set("address", new HashMap<AddrType, Address>());
		    employee.set("subordinate", new Employee[0]);
		    employee.set("firstName", "Fred");
		    employee.set("lastName", "Flintstone");
		
	}
	
	public void testWrapDynaBean(){
		Employee bean = new Employee();
		DynaBean wrapper = new WrapDynaBean(bean);
		String firstName = (String) wrapper.get("firstName");
	}
	
}
