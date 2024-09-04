package springboot.aws.sqs.domain;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import lombok.Data;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@JsonSerialize
@JsonDeserialize
public class MessageHeaders {
    public String clientId;
    public String clientName;
    public String messageId;
    public String messageType;
    public String correlationId;
}
