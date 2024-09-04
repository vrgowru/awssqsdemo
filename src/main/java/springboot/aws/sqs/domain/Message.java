package springboot.aws.sqs.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String messageId;
    private PersonInfo personInfo;
    private String messageType;
    private Date createdAt;
}
