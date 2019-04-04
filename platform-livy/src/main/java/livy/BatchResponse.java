package livy;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @Author lou
 */
public class BatchResponse {
	private String id;
	private String appId;
	private AppInfo appInfo;
	private String[] log;
	private String state;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public AppInfo getAppInfo() {
		return appInfo;
	}

	public void setAppInfo(AppInfo appInfo) {
		this.appInfo = appInfo;
	}

	public String[] getLog() {
		return log;
	}

	public void setLog(String[] log) {
		this.log = log;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public class AppInfo{
		private String driverLogUrl;
		private String sparkUiURL;

		public String getDriverLogUrl() {
			return driverLogUrl;
		}

		public void setDriverLogUrl(String driverLogUrl) {
			this.driverLogUrl = driverLogUrl;
		}

		public String getSparkUiURL() {
			return sparkUiURL;
		}

		public void setSparkUiURL(String sparkUiURL) {
			this.sparkUiURL = sparkUiURL;
		}

		@Override
		public String toString() {
			return ToStringBuilder.reflectionToString(this);
		}
	}
}




