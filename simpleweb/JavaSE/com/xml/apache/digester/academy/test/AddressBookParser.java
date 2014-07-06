package com.xml.apache.digester.academy.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.digester3.Digester;
import org.xml.sax.SAXException;

public class AddressBookParser {
	final static File FILE = new File("JavaSE/com/xml/apache/digester/xml.address.book.xml");
	final static List<Contact> contacts = new ArrayList<Contact>();
	/**
	 * Prints the contact information to standard output.
	 * 
	 * @param contact
	 *            the <code>Contact</code> to print out
	 */
	public void addContact(Contact contact) {
		contacts.add(contact);
	}

	/**
	 * Configures Digester rules and actions, parses the XML file specified as
	 * the first argument.
	 * 
	 * @param args
	 *            command line arguments
	 */
	public static void main(String[] args) throws IOException, SAXException {
		// instantiate Digester and disable XML validation
		Digester digester = new Digester();
		digester.setValidating(false);

		// instantiate AddressBookParser class
		digester.addObjectCreate("address-book", AddressBookParser.class);
		// instantiate Contact class
		digester.addObjectCreate("address-book/contact", Contact.class);

		// set type property of Contact instance when 'type' attribute is found
		// 对有属性的值通过setProperties方法

		digester.addSetProperties("address-book/contact", "myType", "type");

		// set different properties of Contact instance using specified methods
		// addCallMethod与addBeanPropertySetter等价
		// 参数 0代表一个参数，默认就是当前读的数据

		digester.addCallMethod("address-book/contact/name", "setName", 0);
		digester.addCallMethod("address-book/contact/address", "setAddress", 0);
		//digester.addCallMethod("address-book/contact/address", "setAddress", 0);
		digester.addCallMethod("address-book/contact/city", "setCity", 0);
		digester.addCallMethod("address-book/contact/province", "setProvince", 0);
		digester.addCallMethod("address-book/contact/postalcode", "setPostalcode", 0);
		digester.addCallMethod("address-book/contact/country", "setCountry", 0);

		// 增加country的属性 : from
		digester.addSetProperties("address-book/contact/country", "from", "countryFrom");
		digester.addCallMethod("address-book/contact/telephone", "setTelephone", 0);

		// call 'addContact' method when the next 'address-book/contact' pattern is seen
		digester.addSetNext("address-book/contact", "addContact");

		// now that rules and actions are configured, start the parsing process
		//AddressBookParser abp = (AddressBookParser) digester.parse(FILE);
		digester.parse(FILE);
		
		for(Contact c : contacts){
			System.out.println(c);
		}
	}

	/**
	 * JavaBean class that holds properties of each Contact entry. It is
	 * important that this class be public and static, in order for Digester to
	 * be able to instantiate it.
	 */
	public static class Contact {
		private String type;
		private String name;
		private String address;
		private String city;
		private String province;
		private String postalcode;
		private String country;
		// 增加一个country的属性: from
		private String countryFrom;
		private String telephone;

		@Override
		public String toString() {
			return "Contact [type=" + type + ", name=" + name + ", address="
					+ address + ", city=" + city + ", province=" + province
					+ ", postalcode=" + postalcode + ", country=" + country
					+ ", countryFrom=" + countryFrom + ", telephone="
					+ telephone + "]";
		}

		public void setType(String newType) {
			type = newType;
		}

		public String getType() {
			return type;
		}

		public void setName(String newName) {
			name = newName;
		}

		public String getName() {
			return name;
		}

		public void setAddress(String newAddress) {
			address = newAddress;
		}

		public String getAddress() {
			return address;
		}

		public void setCity(String newCity) {
			city = newCity;
		}

		public String getCity() {
			return city;
		}

		public void setProvince(String newProvince) {
			province = newProvince;
		}

		public String getProvince() {
			return province;
		}

		public void setPostalcode(String newPostalcode) {
			postalcode = newPostalcode;
		}

		public String getPostalcode() {
			return postalcode;
		}

		public void setCountry(String newCountry) {
			country = newCountry;
		}

		public String getCountry() {
			return country;
		}

		public void setTelephone(String newTelephone) {
			telephone = newTelephone;
		}

		public String getTelephone() {
			return telephone;
		}

		public String getCountryFrom() {
			return countryFrom;
		}

		public void setCountryFrom(String countryFrom) {
			this.countryFrom = countryFrom;
		}
	}
}
