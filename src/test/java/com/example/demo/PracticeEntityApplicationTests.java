package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.dao.ExampleItems;
import com.example.demo.dao.UpdateDemoData;
import com.example.demo.models.Person;

@SpringBootTest
class PracticeEntityApplicationTests {

	@Autowired
	private UpdateDemoData updateDemoData;
	
	@Autowired
	private ExampleItems exampleItems;

	@BeforeEach
	void init() {
		updateDemoData.save();
	}

	@AfterEach
	void endProcess() {
		updateDemoData.deleteAll();
	}

	/**
	 * 傳入部門 name 查詢其下課別 所有主管名稱 傳入參數:"IT"
	 * 預期結果:[{"name":"joe","EmployeeId":2},{"name":"alice","EmployeeId":5}]
	 */
	@Test
	void testRetriveManagersByDepartment() {
		List<Person> persons = new ArrayList<Person>();
		persons = exampleItems.retriveManagersByDepartment("IT");
		JSONArray jsonArr = null;
		JSONObject jsonObj = null;
		try {
			jsonArr = new JSONArray();
			for (int i = 0; i < persons.size(); i++) {
				jsonObj = new JSONObject();
				jsonObj.put("EmployeeId", persons.get(i).getEmployeeId());
				jsonObj.put("name", persons.get(i).getName());
				jsonArr.put(jsonObj);
			}
			assertThat(jsonArr.optJSONObject(0).optString("name")).isEqualTo("joe");
			assertThat(jsonArr.optJSONObject(0).optInt("EmployeeId")).isEqualTo(2);
			assertThat(jsonArr.optJSONObject(1).optString("name")).isEqualTo("alice");
			assertThat(jsonArr.optJSONObject(1).optInt("EmployeeId")).isEqualTo(5);
		} catch (Exception je) {
			je.printStackTrace();
		}
	}

	/**
	 * 傳入部門 name 查詢其下課別的所有訂單總和(注意同一訂單在兩個課別的情況 必須去除重複)
	 * 傳入參數：'RD'
	 * 預期結果:1560
	 */
	@Test
	void testSumOrderQuantityByDepartment() {
		int count = exampleItems.sumOrderQuantityByDepartment("RD");
		assertThat(count).isEqualTo(1560);
	}

}
