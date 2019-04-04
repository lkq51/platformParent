package livy;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Map;

/**
 * @Author lou
 */
public class BatchPostRequest {
	private String file;
	private String proxyUser;
	private String className;
	private String[] args;
	private String[] jars;
	private String[] pyFiles;
	private String[] files;
	private String driverMemory;
	private int driverCores;
	private String executorMemory;
	private int executorCores;
	private int numExecutors;
	private String archives;
	private String queue;
	private String name;
	private Map<String, String> conf;

	public int getNumExecutors() {
		return numExecutors;
	}

	public void setNumExecutors(int numExecutors) {
		this.numExecutors = numExecutors;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getProxyUser() {
		return proxyUser;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String[] getJars() {
		return jars;
	}

	public void setJars(String[] jars) {
		this.jars = jars;
	}

	public String[] getPyFiles() {
		return pyFiles;
	}

	public void setPyFiles(String[] pyFiles) {
		this.pyFiles = pyFiles;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	public String getDriverMemory() {
		return driverMemory;
	}

	public void setDriverMemory(String driverMemory) {
		this.driverMemory = driverMemory;
	}

	public int getDriverCores() {
		return driverCores;
	}

	public void setDriverCores(int driverCores) {
		this.driverCores = driverCores;
	}

	public String getExecutorMemory() {
		return executorMemory;
	}

	public void setExecutorMemory(String executorMemory) {
		this.executorMemory = executorMemory;
	}

	public int getExecutorCores() {
		return executorCores;
	}

	public void setExecutorCores(int executorCores) {
		this.executorCores = executorCores;
	}

	public String getArchives() {
		return archives;
	}

	public void setArchives(String archives) {
		this.archives = archives;
	}

	public String getQueue() {
		return queue;
	}

	public void setQueue(String queue) {
		this.queue = queue;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getConf() {
		return conf;
	}

	public void setConf(Map<String, String> conf) {
		this.conf = conf;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
