package com.yotagumi.SandBox;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for PHPSerialize
 * 
 * @author Yotama
 * 
 */
public class PHPSerializerTest {
	PHPSerializer target;

	@Before
	public void setUp() throws Exception {
		target = new PHPSerializer();
	}

	@Test
	public void test_serialize_Null() {
		assertThat(target.serialize(null), is("N;"));
	}

	@Test
	public void test_serialize_String() {
		assertThat(target.serialize("Yotama"), is("s:6:\"Yotama\";"));
	}

	@Test
	public void test_serialize_Boolean() {
		assertThat(target.serialize(Boolean.valueOf("true")), is("b:1;"));
		assertThat(target.serialize(Boolean.valueOf("false")), is("b:0;"));
	}
	
	@Test
	public void test_serialize_Integer() {
		assertThat(target.serialize(Integer.valueOf(Integer.MAX_VALUE)), is("i:2147483647;"));
		assertThat(target.serialize(Long.valueOf(Long.MAX_VALUE)), is("i:9223372036854775807;"));
		assertThat(target.serialize(Byte.valueOf(Byte.MAX_VALUE)), is("i:127;"));
		assertThat(target.serialize(Short.valueOf(Short.MAX_VALUE)), is("i:32767;"));
	}
	
	@Test
	public void test_serialized_Double() {
		assertThat(target.serialize(Double.valueOf(0.01d)), is("d:0.01;"));
		assertThat(target.serialize(Float.valueOf(0.1f)), is("d:0.1;"));
	}

	@Test
	public void test_serialized_Map() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "Yotama");
		map.put("age", 28);
		assertThat(target.serialize(map), is("a:2:{s:3:\"age\";i:28;s:4:\"name\";s:6:\"Yotama\";}"));
	}
	
	@Test
	public void test_serialized_List() {
		List<String> list = new ArrayList<String>();
		list.add("Kagoshima");
		list.add("Tokyo");
		assertThat(target.serialize(list), is("a:2:{i:0;s:9:\"Kagoshima\";i:1;s:5:\"Tokyo\";}"));
	}

	@Test
	public void test_serialize_bean() {
		class TestBaen {
			private String name;
			private int age;

			@SuppressWarnings("unused")
			public String getName() {
				return name;
			}

			public void setName(String name) {
				this.name = name;
			}

			@SuppressWarnings("unused")
			public int getAge() {
				return age;
			}

			public void setAge(int age) {
				this.age = age;
			}
		}

		TestBaen dto = new TestBaen();
		dto.setName("Yotama");
		dto.setAge(28);

		assertThat(target.serialize(dto), is("a:2:{s:3:\"age\";i:28;s:4:\"name\";s:6:\"Yotama\";}"));
	}
}
