package springboot.aws.sqs.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OutboundMessage {
    private boolean processingStatus;
    private PersonInfo personInfo;
    private Date createAt;
}
