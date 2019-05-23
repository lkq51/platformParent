package org.platform.client.submit;

/**
 * @Author lou
 */
public class SubmitClientTest {
	public static void main(String[] args) {
		SubmitClient client = new SubmitClient();
		String id = client.submit();
		boolean flag;
		while (true){
			flag = client.monitory(id);
			if (flag){
				break;
			}
		}
		System.out.println("job finished...");
	}
}
