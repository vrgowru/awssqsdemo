package springboot.aws.sqs.config;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.EnableSqs;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.support.PayloadMethodArgumentResolver;
import org.springframework.messaging.handler.invocation.HandlerMethodArgumentResolver;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Configuration
@EnableSqs
public class AWSSQSConfig {
    @Value("${aws.access.key}")
    private String accessKeyId;
    @Value("${aws.secret.key}")
    private String secretAccessKey;
    @Value("${aws.us.region}")
    private String region;
    @Value("${cloud.aws.end-point.uri}")
    private String endpointUri;

    @Bean
    @Primary
    public AmazonSQSAsync amazonSQSAsyncClient() {
        log.info("Creating AmazonSQS client");
        log.info("Access Key: {}", accessKeyId);
        log.info("Secret Key: {}", secretAccessKey);
        log.info("Region: {}", region);
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyId, secretAccessKey);
        AWSCredentialsProvider awsCredentialsProvider = new AWSStaticCredentialsProvider(awsCredentials);
        return AmazonSQSAsyncClientBuilder.standard().withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(endpointUri, region)).withCredentials(awsCredentialsProvider).build();
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync sqs) {
        return new QueueMessagingTemplate(sqs);
    }

    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(ObjectMapper objectMapper,
                                                                 AmazonSQSAsync amazonSQSAsync) {
        MappingJackson2MessageConverter messageConverter = new MappingJackson2MessageConverter();
        messageConverter.setObjectMapper(objectMapper);
        messageConverter.setStrictContentTypeMatch(false);

        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setAmazonSqs(amazonSQSAsync);

        List<HandlerMethodArgumentResolver> resolvers = List.of(
                new PayloadMethodArgumentResolver(messageConverter,null, false));
        factory.setArgumentResolvers(resolvers);

        return factory;
    }

}
