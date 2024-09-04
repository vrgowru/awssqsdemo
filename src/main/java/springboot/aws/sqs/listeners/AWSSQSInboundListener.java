package springboot.aws.sqs.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import springboot.aws.sqs.domain.Message;
import springboot.aws.sqs.service.MessageProcessorImpl;
import springboot.aws.sqs.service.MessageProcessorService;

import java.util.Map;

@Slf4j
@Component
@EnableSqs
public class AWSSQSInboundListener {

    @Autowired
    MessageProcessorImpl messageProcessorService;

    @SqsListener(value = "${aws.inbound.queue.name}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void receiveMessage(@Payload Message payload, @Headers Map<String, Object> headers) throws JsonProcessingException {
        log.info("Received message:  \n  {} \n", payload.getPersonInfo());
        log.info("Received headers:  \n  {} \n", headers);
        messageProcessorService.process(payload);
    }
}
