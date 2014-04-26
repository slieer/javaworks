package simple.foundation;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

public class BeanUtilsTest {
	public static void main(String[] args) {
		Map properties = new HashMap();
		properties.put("id", 1);
		properties.put("name", "1");
		properties.put("sex", true);
		Bean bean = new Bean();
		try {
			BeanUtils.populate(bean, properties);
			System.out.println(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static class Bean {
		int id;
		String name;
		boolean sex;
		Date birth;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public boolean isSex() {
			return sex;
		}

		public void setSex(boolean sex) {
			this.sex = sex;
		}

		public Date getBirth() {
			return birth;
		}

		public void setBirth(Date birth) {
			this.birth = birth;
		}

		@Override
		public String toString() {
			return "Bean [id=" + id + ", name=" + name + ", sex=" + sex
					+ ", birth=" + birth + "]";
		}
	}
}
