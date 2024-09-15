package slieer.commons.Digester.student.bean;

import java.util.Vector;

public class Teacher {
	private String name;
	private Vector<String> certifications = new Vector<String>();

	public void addCertification(String cert){
		certifications.add(cert);
	}
	
	@Override
	public String toString() {
		return "Teacher [name=" + name + ", certifications="
				+ certifications + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Vector getCertifications() {
		return certifications;
	}

	public void setCertifications(Vector certifications) {
		this.certifications = certifications;
	}
}