package org.platform.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.StringUtils;
import org.platform.common.SysConfigUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static org.apache.ivy.util.Checks.checkNotNull;

/**
 * @Author lou
 */
public class LivyRestfulClient {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private static Gson gson = null;
	static {
		GsonBuilder gsonBuilder = new GsonBuilder();
		gson = gsonBuilder.create();
	}

	static

	public LivyRestConfig restConfig;
	private Map<String, String> headers = new HashMap<String, String>() {
		private static final long serialVersionUID = 908212646872217803L;
		{
			put("Content-Type", "application/json");
			put("Accept", "application/json");
			put("X-Requested-By", "org/platform/http");
			put("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
		}
	};
	private boolean useSpnego = Boolean.valueOf(SysConfigUtils.getValueByKey("use.Kerbero"));

	public LivyRestfulClient(LivyRestConfig config){
		super();
		checkNotNull(config, "参数[restConfig]不能为null");
		checkNotNull(config.getUrlRestful(), "参数[UrlRestful]不能为null");
		this.restConfig = config;
	}
	public BatchResponse submit(BatchPostRequest request) throws URISyntaxException, IOException {
		request.setDriverCores(1);
		request.setExecutorCores(3);
		request.setExecutorMemory("4G");
		request.setNumExecutors(12);

		String strRequest = gson.toJson(request);
		if (logger.isDebugEnabled()){
			logger.debug(strRequest);
		}

		String urlFull = restConfig.getUrlRestful() + "/batches";

		String batchResult = null;
		if (this.useSpnego){
			batchResult = SpnegoHttpUtils.postAccess(urlFull, headers, strRequest);
		}else {
			HttpReturnInfo returnInfo = HttpClientUtils.postAccess(urlFull, strRequest, headers);
			if (returnInfo.getRetcode() >= 200 && returnInfo.getRetcode() < 300){
				batchResult = returnInfo.getContent();
			}else {
			}
		}

		if (logger.isDebugEnabled()){
			logger.debug("batches reponse: {}", batchResult);
		}
		if (StringUtils.isBlank(batchResult)){

		}
		BatchResponse batchResponse = gson.fromJson(batchResult, BatchResponse.class);
		if (logger.isDebugEnabled()){
			logger.debug(batchResponse.toString());
		}

		String batchId = batchResponse.getId();
		String appId = batchResponse.getAppId();

		if (StringUtils.isBlank(appId)){
			int times = 10;
			do {
				if (logger.isDebugEnabled()){
					logger.debug("未获取到appId, 剩余尝试{}次", times);
				}
				try {
					Thread.sleep(3000L);
				}catch (InterruptedException e){

				}

				try {
					BatchResponse tmpResponse = this.getBatchInfo(batchId);
					appId = tmpResponse.getAppId();
					if (StringUtils.isNotBlank(appId)){
						batchResponse = tmpResponse;
						if (logger.isDebugEnabled()){
							logger.debug("");
						}
						break;
					}
				}catch (Exception e){
				}
				--times;
			}while (StringUtils.isBlank(appId) && times > 0);
		}
		//if (StringUtils.isBlank(appId)){
		//
		//}
		return  batchResponse;
	}
	public BatchResponse getBatchInfo(String batchid) {

		String urlBatchInfo = String.format("%s/batches/%s",
				restConfig.getUrlRestful(), batchid);
		String infoResult = null;
		if (this.useSpnego) {
			infoResult = SpnegoHttpUtils.getAccess(urlBatchInfo, headers);
		} else {
			HttpReturnInfo returnInfo = HttpClientUtils.getAccess(urlBatchInfo);
			if (returnInfo.getRetcode() >= 200 && returnInfo.getRetcode() < 300) {
				infoResult = returnInfo.getContent();
			} else {
			}
		}

		BatchResponse batchPostResponse = gson.fromJson(infoResult,
				BatchResponse.class);
		if (logger.isDebugEnabled()) {
			logger.debug(batchPostResponse.toString());
		}
		return batchPostResponse;
	}

	public static void main(String[] args) throws IOException, URISyntaxException {
		LivyRestConfig config = new LivyRestConfig();
		config.setUrlRestful("http://10.33.26.242:8998");
		LivyRestfulClient client = new LivyRestfulClient(config);

		BatchPostRequest request = new BatchPostRequest();
		request.setClassName("com.algorit.test");
		request.setFile("/org/platform/http/algorithm.jar");
		request.setName("test");
		BatchResponse response = client.submit(request);
		System.out.println(response);
		System.out.println("==========================");

		BatchResponse infoResponse = client.getBatchInfo(response.getId());
		System.out.println(infoResponse);
		System.out.println("==========================");
	}
}
