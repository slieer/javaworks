package com.beans.lombok;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;

/**
 * @author root
 *
 *1、下载Lombok.jar http://projectlombok.googlecode.com/files/lombok.jar 
  2、运行Lombok.jar: java -jar lombok.jar 
        数秒后将弹出一框，以确认eclipse的安装路径
  3、确认完eclipse的安装路径后，点击install/update按钮，即可安装完成
 */

@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class GetterSetterUser {
	
	@Getter @Setter
	private int age = 25;
	
	@Setter(AccessLevel.PROTECTED)
	private String address = "shanghai";
	
	@Getter @Setter @NonNull
	private String email = null;
	
	@Getter
	private Object name = null;
	
	/** code by myself */
	public void setName(String name) {
		this.name = name + " from my code";
	}
	
	@Data
	@ToString(exclude={"hour","minite", "sceond"})
	static class Date{
		private int year, mouth, day, hour, minite, sceond;
		
	}
	
	public static void main(String[] args) {
		// we test the getter/setter annotation
		GetterSetterUser bean = new GetterSetterUser();
		bean.setAge(20);
		bean.setAddress("xi'an");
		bean.setName("wity_lv,");
		
		System.out.println(bean.toString());
		
		/*
		 * set a null value to email
		 */
		try {
			bean.setEmail(null);
		} catch(NullPointerException e) {
			System.out.println("phone could not be null value");
		}
		
	}
}
