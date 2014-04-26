package simple.foundation;

import net.sf.json.JSONObject;

import org.junit.Test;

public class JSONTest {
	@Test
	public void f(){
		String json = "{\"code\":0,\"info\":\"Diliver success~\"}";
		JSONObject j = JSONObject.fromObject(json);
		int i = (Integer)j.get("code");
		System.out.println(i);
	}
}
