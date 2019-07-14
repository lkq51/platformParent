package org.platform.client.submit;

/**
 * @Author lou
 */
public class SubmitClientTest {
	public static void main(String[] args) {
		SubmitClient client = new SubmitClient();
		String id = client.submit();
		boolean flag;
		do {
			flag = SubmitClient.monitory(id);
		} while (!flag);
		System.out.println("job finished...");
	}
}
