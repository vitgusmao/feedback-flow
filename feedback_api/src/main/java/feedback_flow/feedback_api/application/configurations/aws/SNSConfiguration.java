package feedback_flow.feedback_api.application.configurations.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;

@Configuration
public class SNSConfiguration {

    private final AWSCredentials awsCredentials;

    @Autowired
    public SNSConfiguration(AWSCredentials awsCredentials) {
        this.awsCredentials = awsCredentials;
    }

    @Bean
    public AmazonSNS amazonSNS() {

        AmazonSNS snsClient = AmazonSNSClient
                .builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .build();
        return snsClient;

    }
}
