package slieer.other.json.testdata;

import java.util.ArrayList;
import java.util.List;

public class Data {
	public static Group getData(){
		Group group = new Group();
		group.setId(0L);
		group.setName("admin");

		User guestUser = new User();
		guestUser.setId(2L);
		guestUser.setName("guest");

		User rootUser = new User();
		rootUser.setId(3L);
		rootUser.setName("root");

		group.getUsers().add(guestUser);
		group.getUsers().add(rootUser);
		
		return group;
	}
	
	
	public static class User {
		private Long id;
		private String name;

		@Override
		public String toString() {
			return "User [id=" + id + ", name=" + name + "]";
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	public static class Group {
		private Long id;
		private String name;
		private List<User> users = new ArrayList<User>();

		
		@Override
		public String toString() {
			return "Group [id=" + id + ", name=" + name + ", users=" + users
					+ "]";
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public List<User> getUsers() {
			return users;
		}

		public void setUsers(List<User> users) {
			this.users = users;
		}
	}

}
