package springboot.aws.sqs.producers;

import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import springboot.aws.sqs.config.AWSSQSConfig;
import springboot.aws.sqs.domain.OutboundMessage;

@Slf4j
@Component
public class AWSSQSOutboundPublisher {

    AWSSQSConfig awsSQSConfig;

    @Value("${aws.outbound.queue.url}")
    private String outboundQueueURL;

    AWSSQSOutboundPublisher(AWSSQSConfig awsSQSConfig) {
        this.awsSQSConfig = awsSQSConfig;
    }

    public void pushProcessedMessage(OutboundMessage outboundMessage) throws JsonProcessingException {
        log.info("Pushing processed message: {}", outboundMessage);
        ObjectMapper mapper = new ObjectMapper();
        SendMessageRequest messageRequest = new SendMessageRequest(outboundQueueURL,  mapper.writeValueAsString(outboundMessage));
        awsSQSConfig.amazonSQSAsyncClient().sendMessage(messageRequest);
    }
}
