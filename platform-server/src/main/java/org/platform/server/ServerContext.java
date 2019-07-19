package org.platform.server;

import org.apache.spark.SparkContext;

import java.util.Stack;

/**
 * @Author lou
 */
public class ServerContext {
	private Stack<SparkContext> contextStack;

	ServerContext(){
		contextStack = new Stack<>();
	}
}
