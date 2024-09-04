package springboot.aws.sqs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springboot.aws.sqs.domain.Message;
import springboot.aws.sqs.domain.OutboundMessage;
import springboot.aws.sqs.producers.AWSSQSOutboundPublisher;

import java.util.Date;

@Service
public class MessageProcessorImpl implements MessageProcessorService {

    @Autowired
    AWSSQSOutboundPublisher publisher;

    @Override
    public void process(Message message) throws JsonProcessingException {
        OutboundMessage outboundMessage = OutboundMessage.builder().processingStatus(true).personInfo(message.getPersonInfo()).createAt(new Date()).build();
        publisher.pushProcessedMessage(outboundMessage);
    }
}
