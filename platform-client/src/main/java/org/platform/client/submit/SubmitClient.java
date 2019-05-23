package org.platform.client.submit;

import org.apache.spark.SparkConf;
import org.apache.spark.deploy.rest.CreateSubmissionResponse;
import org.apache.spark.deploy.rest.RestSubmissionClient;
import org.apache.spark.deploy.rest.SubmissionStatusResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.collection.immutable.HashMap;

/**
 * @Author lou
 */
public class SubmitClient {
	private static  Logger logger = LoggerFactory.getLogger(SubmitClient.class);
	public static String submit()
	{
		String appResource = "hdfs://jars/hello-spark.jar";
		String mainClass = "SelectLog";
		String[] args = {

		};
		SparkConf conf = new SparkConf();
		conf.setMaster("spark://47.100.177.21:6066")
		.set("spark.submit.deployMode", "cluster")
		.set("spark.jars", appResource)
		.setAppName("hello-spark" + System.currentTimeMillis());

		CreateSubmissionResponse response = null;

		try {
			response = (CreateSubmissionResponse) RestSubmissionClient.run(appResource, mainClass, args, conf, new HashMap<String, String>());
		}catch (Exception e){
			logger.error("run application error...", e);
		}
		return response.submissionId();
	}

	private static RestSubmissionClient client = new RestSubmissionClient("spark://47.100.177.21:6066");


	public static boolean monitory(String appId){
		SubmissionStatusResponse response = null;
		boolean finished = false;
		try {
			response = (SubmissionStatusResponse) client.requestSubmissionStatus(appId, true);
			if ("FINISHED".equals(response.driverState()) || "ERROR".equals(response.driverState())){
				finished = true;
			}
			Thread.sleep(5000);
		}catch (Exception e){
			logger.error("monitory error...", e);
		}
		return finished;
	}
}
