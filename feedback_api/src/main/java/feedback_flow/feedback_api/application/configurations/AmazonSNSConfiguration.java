package feedback_flow.feedback_api.application.configurations;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClient;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class AmazonSNSConfiguration {

    private final Dotenv dotenv;

    @Autowired
    public AmazonSNSConfiguration(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public AWSCredentials readCredentials() {
        String accessKey = dotenv.get("AWS_ACCESS_KEY");
        String secretKey = dotenv.get("AWS_SECRET_KEY");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return credentials;
    }

    @Bean
    public AmazonSNS amazonSNS() {
        AmazonSNS snsClient = AmazonSNSClient.builder()
                .withRegion(Regions.US_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(readCredentials()))
                .build();
        return snsClient;
    }
}
