package org.platform.utils;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author lou
 */
public class ListTest {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>();

		map.put("1", "2");
		map.put("1", "1");

		System.out.println(map.get("1"));
	}

	static class Thing{
		private List<String> data;
		public void setData(List<String> data){
			this.data = data;
		}
		public List<String> getData(){
			if (this.data == null){
				this.data = new ArrayList();
			}
			return this.data;
		}
	}
}
