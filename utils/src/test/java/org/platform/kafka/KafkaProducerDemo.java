package org.platform.kafka;

import com.alibaba.fastjson.JSONObject;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.serializer.StringDecoder;

import java.util.*;

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

	@Override
	public void run() {
		System.out.println("start to send message to kafka");
		int count = 0;
		while (true){
			JSONObject jsonObject = new JSONObject();
			int num = new Random().nextInt(10);
			String ip = "ip" + num;
			String userId = UUID.randomUUID().toString().replace("-", "");
			jsonObject.put("userId", userId);
			jsonObject.put("userName", "Tiger" + userId);
			System.out.println("userId >>> " + userId);
			producer.send(new KeyedMessage<String, String>(topic, userId, jsonObject.toJSONString()));

			try {
				Thread.sleep(10);
			}catch (InterruptedException e){
				e.printStackTrace();
			}
			count ++;
			if (count == 500){
				System.out.println("stop to send message to kafka");
				break;
			}
		}
	}
}
