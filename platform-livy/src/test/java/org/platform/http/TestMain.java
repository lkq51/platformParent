package org.platform.http;

import org.apache.livy.LivyClient;
import org.apache.livy.LivyClientBuilder;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class TestMain {
	public static void main(String[] args) throws IOException, URISyntaxException {

		String url = "http://10.33.26.242:8998/";
		LivyClient client = new LivyClientBuilder()
				.setURI(new URI(url))
				.setConf("spark.app.name", "livyJob")
				.setConf("spark.cores.max", "1")
				.setConf("spark.master", "yarn")
				.build();

		try {
			String jar = "D:\\code\\platformParent\\out\\artifacts\\algorithm_jar2\\livyJob.jar";
			System.err.printf("Uploading %s to the Spark context...\n", jar);
			client.uploadJar(new File(jar)).get();

			System.err.printf("Running PiJob with %d samples...\n", 10);
//			double pi = client.submit(new LivyJob(10)).get();

//			System.out.println("Pi is roughly: " + pi);
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		} finally {
			client.stop(true);
		}
	}
}
