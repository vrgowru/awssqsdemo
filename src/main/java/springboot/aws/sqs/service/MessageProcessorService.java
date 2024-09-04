package springboot.aws.sqs.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import springboot.aws.sqs.domain.Message;

public interface MessageProcessorService {
    void process(Message message) throws JsonProcessingException;
}
