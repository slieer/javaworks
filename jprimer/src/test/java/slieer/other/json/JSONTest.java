package slieer.other.json;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import slieer.other.json.testdata.Data;
import slieer.other.json.testdata.Data.Group;
import slieer.other.json.testdata.Data.User;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class JSONTest {
	@Test
	public void f() {
		String json = "{\"code\":0,\"info\":\"Diliver success~\"}";

		JSONObject j = (JSONObject) JSON.parse(json);
		int i = (Integer) j.get("code");
		System.out.println(i);
	}

	@Test
	public void ff() {
		Group d = Data.getData();
		String jsonString = JSON.toJSONString(d);

		System.out.println(jsonString);

		Group group2 = JSON.parseObject(jsonString, Group.class);
	}

	@Test
	public void map() {
		User u = new User();
		u.setId(1L);
		u.setName("slie");
		Map<String, User> map = new HashMap<String, User>();
		map.put("first", u);

		String object = JSONObject.toJSONString(map);
		System.out.println(object);

	}

	@Test
	public void fff() {
		String jsonStr = "{\"JACKIE_ZHANG\":\"张学友\",\"ANDY_LAU\":\"刘德华\",\"LIMING\":\"黎明\",\"Aaron_Kwok\":\"郭富城\"}";

		// 做5次测试
		for (int i = 0, j = 5; i < j; i++) {
			JSONObject jsonObject = JSONObject.parseObject(jsonStr);
			for (Map.Entry<String, Object> entry : jsonObject
					.entrySet()) {
				System.out
						.print(entry.getKey() + "-" + entry.getValue() + "\t");
			}
			System.out.println();// 用来换行
		}
	}
}
