package org.platform.http;

import net.sourceforge.spnego.SpnegoHttpURLConnection;
import org.ietf.jgss.GSSException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PrivilegedActionException;
import java.util.Map;
import java.util.Set;

/**
 * @Author lou
 */
public class SpnegoHttpUtils {
	protected static Logger logger = LoggerFactory.getLogger(SpnegoHttpUtils.class);

	public static String getAccess(String url, Map<String, String> headers){
		return handleAccess("GET", url, headers, null);
	}


	public static String deleteAccess(String url, Map<String, String> headers){
		return handleAccess("DELETE", url, headers, null);
	}

	public static String postAccess(String url, Map<String, String> headers, String data){
		return  handleAccess("POST", url, headers, data);
	}


	private static String handleAccess(String action, String url, Map<String, String> headers, String data){
		StringBuilder sb = new StringBuilder();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		InputStream in = null;
		try{
			final SpnegoHttpURLConnection connection = new SpnegoHttpURLConnection("Client");
			connection.setRequestMethod(action);
			if (headers != null && headers.size() > 0){
				Set<Map.Entry<String, String>> kvSet = headers.entrySet();
				for (Map.Entry<String, String> entry : kvSet){
					connection.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
			if(data != null){
				bos.write(data.getBytes());
			}
			connection.connect(new URL(url), bos);
			in = connection.getInputStream();
			byte[] b = new byte[1024];
			int len;
			while ((len = in.read(b)) > 0){
				sb.append(new String(b, 0, len));
			}
		} catch (LoginException | GSSException | PrivilegedActionException | IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
}
