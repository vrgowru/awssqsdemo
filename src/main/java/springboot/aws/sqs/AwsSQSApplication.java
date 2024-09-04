package springboot.aws.sqs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springboot.aws.sqs.producers.TestDataProducer;

@SpringBootApplication
public class AwsSQSApplication {

    public static void main(String[] args) {
        SpringApplication.run(AwsSQSApplication.class, args);
    }

    @Bean
    public ApplicationRunner run(TestDataProducer testDataProducer) {
        return args -> {
            Thread.sleep(3000);
            testDataProducer.publishTestData(15);
        };
    }

}
