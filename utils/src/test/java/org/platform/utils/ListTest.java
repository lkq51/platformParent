package org.platform.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author lou
 */
public class ListTest {
	public static void main(String[] args) {
		Thing t = new Thing();
		t.getData().add("1");
		System.out.println(t.getData());
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
