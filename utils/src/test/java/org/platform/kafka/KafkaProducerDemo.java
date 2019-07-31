package org.platform.kafka;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Date 2019-07-31
 * @Author lou
 */
public class KafkaProducerDemo extends Thread {
	private String topic;

	private Producer producer;
	private static Map<String, String> config = new HashMap<String, String>();

	public KafkaProducerDemo(String topic){
		this.topic = topic;
		this.producer = createProducer();
	}

	private Producer createProducer(){
		Properties properties = new Properties();
		properties.put("zookeeper.connect", config.get("zookeeper.connect"));
		properties.put("serializer.class", StringDecoder.class.getName());
		properties.put("metadata.broker.list", config.get("metadata.broker.list"));
		properties.put("partitioner.class", PartitionDemo.class);
		return new Producer<Integer, String>(new ProducerConfig(properties));
	}

}
