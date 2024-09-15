package com.security.jaas.login;

import java.security.Principal;

public class SimplePrincipal implements Principal{
	public SimplePrincipal(String descr,String value){
		this.descr = descr;
		this.value = value;
	}
	
	public String getName(){
		return descr.concat("=").concat(value);
	}
	
	public boolean equals(Object ojb){
		if(this == ojb)return true;
		if(ojb == null)return false;
		if(getClass() != ojb.getClass())return false;
		SimplePrincipal other = (SimplePrincipal)ojb;
		
		return getName().equals(other.getName());
	}
	
	public int hashCode(){
		return getName().hashCode();
	}
	
	private String descr;
	private String value;
}
