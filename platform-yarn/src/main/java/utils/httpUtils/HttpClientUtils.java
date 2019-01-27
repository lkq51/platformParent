package utils.httpUtils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @Author lou
 */
public class HttpClientUtils {
	protected static Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

	public static HttpReturnInfo getAccess(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建httpget.
			HttpGet httpget = new HttpGet(url);
			// 执行get请求.
			response = httpclient.execute(httpget);
			/*
			 * refresh
			 */
			Header rHeader = response.getFirstHeader("refresh");
			if (rHeader != null) {
				String refreshValue = rHeader.getValue();
				if(logger.isDebugEnabled()) {
					logger.debug("refresh={}, Response from url: {}",refreshValue,url);
				}
				return getAccess(refreshValue.split(";")[1].split("=")[1]);
			}

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			StatusLine statusLine = response.getStatusLine();
			logger.info("HttpGet Response: " + statusLine.getStatusCode()
					+ ": " + statusLine);

			HttpReturnInfo returnInfo = new HttpReturnInfo();
			returnInfo.setRetcode(statusLine.getStatusCode());
			returnInfo.setContent(entity != null ? EntityUtils.toString(entity)
					: null);
			return returnInfo;
		} catch (IOException | ParseException e) {
			throw new IllegalStateException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}

			// 关闭连接,释放资源
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
				}
			}
		}
	}


	public static HttpReturnInfo postAccess(String urlPath, String jsonData, Map<String, String> headers) {

		CloseableHttpClient httpclient = HttpClients.createDefault();
		CloseableHttpResponse response = null;
		try {
			// 创建httpget.
			HttpPost httpPost = new HttpPost(urlPath);
			System.out.println("Request URL: " + httpPost.getURI());
			if (headers != null && headers.size() > 0) {
				Set<Map.Entry<String, String>> kvSet = headers.entrySet();
				for (Map.Entry<String, String> entry : kvSet) {
					httpPost.setHeader(entry.getKey(), entry.getValue());
				}
			}

			// 构建消息实体
			// 封装提交Spark作业的JSON数据
			StringEntity sEntity = new StringEntity(jsonData);
			sEntity.setContentEncoding("UTF-8");
			// 发送Json格式的数据请求
			sEntity.setContentType("application/json");
			httpPost.setEntity(sEntity);

			// 伪装成google的爬虫
			// httpPost.setHeader(
			// "User-Agent",
			// "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.130 Safari/537.36");
			// 执行get请求.
			response = httpclient.execute(httpPost);
			System.out.println("--------------------------------------");
			Header rHeader = response.getFirstHeader("refresh");
			if (rHeader != null) {
				System.out.println(rHeader.getName() + " == "
						+ rHeader.getValue());
			}

			// 获取响应实体
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			StatusLine statusLine = response.getStatusLine();
			logger.info("HttpPost Response: " + statusLine.getStatusCode()
					+ ": " + statusLine);

			HttpReturnInfo returnInfo = new HttpReturnInfo();
			returnInfo.setRetcode(statusLine.getStatusCode());
			returnInfo.setContent(entity != null ? EntityUtils.toString(entity)
					: null);
			return returnInfo;
		} catch (IOException | ParseException e) {
			throw new IllegalStateException(e);
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
				}
			}

			// 关闭连接,释放资源
			if (httpclient != null) {
				try {
					httpclient.close();
				} catch (IOException e) {
					}
				}
			}
		}
}

