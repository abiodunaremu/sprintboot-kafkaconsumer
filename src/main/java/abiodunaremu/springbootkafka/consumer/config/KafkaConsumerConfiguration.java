package abiodunaremu.springbootkafka.consumer.config;

import entity.Student;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfiguration {

	@Value("${kafka.boot.server}")
	private String kafkaServer;

	@Value("${kafka.consumer.group.id}")
	private String kafkaGroupId;

	@Bean
	public ConsumerFactory<String, Student> consumerConfiguration() {
		Map<String, Object> consumerConfig = new HashMap<>();
		consumerConfig.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		consumerConfig.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
		consumerConfig.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
				StringDeserializer.class);
		consumerConfig.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
				JsonSerializer.class);
		return new DefaultKafkaConsumerFactory<>(consumerConfig, null,
				new JsonDeserializer<Student>(Student.class));
	}

	@Bean
	public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String,
			Student>> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, Student> listener =
				new ConcurrentKafkaListenerContainerFactory<>();
		listener.setConsumerFactory(consumerConfiguration());
		return listener;
	}

}

