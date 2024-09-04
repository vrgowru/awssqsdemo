package springboot.aws.sqs.producers;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import springboot.aws.sqs.MessageHeadersMapper;
import springboot.aws.sqs.config.AWSSQSConfig;
import springboot.aws.sqs.domain.Message;
import springboot.aws.sqs.domain.MessageHeaders;
import springboot.aws.sqs.domain.PersonInfo;

import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
public class TestDataProducer {

    private final AWSSQSConfig awsSQSConfig;
    private final String queueURL = "http://sqs.us-east-1.localhost.localstack.cloud:4566/000000000000/aws-spring-sqs-demo-inbound-queue";
    @Value("${aws.inbound.queue.name}")
    private String inboundQueueName;

    public TestDataProducer(AWSSQSConfig awsSQSConfig) {
        this.awsSQSConfig = awsSQSConfig;
    }

    public void publishTestData(int numberOfMessages) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 0; i < numberOfMessages; i++) {
            log.info("Producing message {}", i);
            var message = Message.builder().messageType("json").personInfo(PersonInfo.builder().firstName("test").lastName("last").email("test@gmail.com").addressLine1("19030 138 ST SE,").city("BOTHELL").state("WA").country("USA").zipCode("98076").build()).messageId(UUID.randomUUID().toString()).createdAt(new Date()).build();

            // Message Body
            SendMessageRequest messageRequest = new SendMessageRequest(queueURL, objectMapper.writeValueAsString(message));

            // Message headers
            MessageHeadersMapper mapper = new MessageHeadersMapper();
            MessageHeaders messageHeaders = MessageHeaders.builder().messageType("json").messageId(UUID.randomUUID().toString()).clientName("test-client").correlationId(UUID.randomUUID().toString()).clientId("local-test-client").build();
            messageRequest.setMessageAttributes(mapper.map(messageHeaders));

            awsSQSConfig.amazonSQSAsyncClient().sendMessage(messageRequest);
        }
    }
}

