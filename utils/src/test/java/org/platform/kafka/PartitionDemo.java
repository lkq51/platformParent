package org.platform.kafka;

import kafka.utils.VerifiableProperties;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @Date 2019-08-01
 * @Author lou
 */
public class PartitionDemo  implements Partitioner {

	public PartitionDemo(VerifiableProperties properties){

	}


	@Override
	public int partition(String s, Object o, byte[] bytes, Object o1, byte[] bytes1, Cluster cluster) {
		return 0;
	}

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> map) {

	}
}
