package org.slieer.commons.Digester.guide.core;

public class Bar {
	private int id;
	private String title;

	@Override
	public String toString() {
		return "Bar [id=" + id + ", title=" + title + "]";
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
