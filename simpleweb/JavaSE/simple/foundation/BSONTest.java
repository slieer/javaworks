package simple.foundation;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import de.undercouch.bson4jackson.BsonFactory;

/**
 * 
 * @author slieer
 */
public class BSONTest {
	public static void main(String[] args) {
		try {
			ManualSample.test1();
			ManualSample.test2();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

class ManualSample {
	public static void test1() throws Exception {
		// create dummy POJO
		Person bob = new Person();
		bob.setName("Bob");

		// serialize data
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectMapper mapper = new ObjectMapper(new BsonFactory());
		mapper.writeValue(baos, bob);

		// deserialize data
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		Person clone_of_bob = mapper.readValue(bais, Person.class);

		assert bob.getName().equals(clone_of_bob.getName());
	}

	public static void test2() throws Exception {
		// create dummy POJO
		Person bob = new Person();
		bob.setName("Bob");

		// create factory
		BsonFactory factory = new BsonFactory();

		// serialize data
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		JsonGenerator gen = factory.createJsonGenerator(baos);
		gen.writeStartObject();
		gen.writeFieldName("name");
		gen.writeString(bob.getName());
		gen.close();

		// deserialize data
		ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
		JsonParser parser = factory.createJsonParser(bais);
		Person clone_of_bob = new Person();
		parser.nextToken();
		while (parser.nextToken() != JsonToken.END_OBJECT) {
			String fieldname = parser.getCurrentName();
			parser.nextToken();
			if ("name".equals(fieldname)) {
				clone_of_bob.setName(parser.getText());
			}
		}

		assert bob.getName().equals(clone_of_bob.getName());
	}
}

class Person {
	private String _name;
	private List<String> phoneNumber;

	public void setName(String name) {
		_name = name;
	}

	public String getName() {
		return _name;
	}

	public List<String> getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(List<String> phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}