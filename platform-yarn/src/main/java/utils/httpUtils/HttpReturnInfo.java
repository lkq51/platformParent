package utils.httpUtils;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @Author lou
 */
public class HttpReturnInfo {
	private int retcode;
	private String content;

	public int getRetcode() {
		return retcode;
	}

	public void setRetcode(int retcode) {
		this.retcode = retcode;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
