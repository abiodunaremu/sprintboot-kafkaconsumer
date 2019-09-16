package abiodunaremu.springbootkafka.consumer.services;

import entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaReceiver {
	private static final Logger LOGGER = LoggerFactory.getLogger(KafkaReceiver.class);

	@KafkaListener(topics = "${kafka.topic.name}", groupId = "${kafka.consumer.group.id}")
	public void receiveData(Student student) {
		LOGGER.info("Data: " + student.toString() + " received");
	}
}
