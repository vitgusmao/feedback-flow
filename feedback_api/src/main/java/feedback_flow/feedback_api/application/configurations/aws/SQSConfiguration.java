package feedback_flow.feedback_api.application.configurations.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSAsyncClient;

@Configuration
public class SQSConfiguration {

    private final AWSCredentials awsCredentials;

    @Autowired
    public SQSConfiguration(AWSCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }

    @Bean
    public AmazonSQS amazonSQS() {
        AmazonSQS sqsClient = AmazonSQSAsyncClient
                .builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        return sqsClient;
    }
}
