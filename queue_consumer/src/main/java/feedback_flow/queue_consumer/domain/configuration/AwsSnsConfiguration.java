package feedback_flow.queue_consumer.domain.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class AwsSnsConfiguration {

    private final Dotenv dotenv;

    @Autowired
    public AwsSnsConfiguration(Dotenv dotenv) {
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
        return AmazonSNSClientBuilder.standard().withRegion(Regions.EU_SOUTH_1)
                .withCredentials(new AWSStaticCredentialsProvider(readCredentials())).build();
    }
}
