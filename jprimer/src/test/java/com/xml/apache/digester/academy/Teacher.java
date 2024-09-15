package com.xml.apache.digester.academy;

import java.util.Vector;

public class Teacher {
	private String name;
	private Vector<String> certifications;

	public void addCertification(String certification){
		Vector<String> cer = this.getCertifications();
		if(cer == null){
			cer = new Vector<String>();
			this.setCertifications(cer);
		}
		
		cer.add(certification);
	}
	
	@Override
	public String toString() {
		return "Teacher [name=" + name + ", certifications=" + certifications
				+ "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector<String> getCertifications() {
		return certifications;
	}

	public void setCertifications(Vector<String> certifications) {
		this.certifications = certifications;
	}
}