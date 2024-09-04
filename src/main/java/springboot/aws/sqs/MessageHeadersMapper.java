package springboot.aws.sqs;

import com.amazonaws.services.sqs.model.MessageAttributeValue;
import lombok.extern.apachecommons.CommonsLog;
import springboot.aws.sqs.domain.MessageHeaders;

import java.util.HashMap;
import java.util.Map;

@CommonsLog
public class MessageHeadersMapper {
    public Map<String, MessageAttributeValue> map(MessageHeaders messageHeaders) {
        Map<String, MessageAttributeValue> map = new HashMap<>();

        map.put("messageType", new MessageAttributeValue().withDataType("String").withStringValue(messageHeaders.getMessageType()));
        map.put("messageId", new MessageAttributeValue().withDataType("String").withStringValue(messageHeaders.getMessageId()));
        map.put("clientName", new MessageAttributeValue().withDataType("String").withStringValue(messageHeaders.getClientName()));
        map.put("correlationId", new MessageAttributeValue().withDataType("String").withStringValue(messageHeaders.getCorrelationId()));
        map.put("clientId", new MessageAttributeValue().withDataType("String").withStringValue(messageHeaders.getClientId()));
        return map;
    }

}
