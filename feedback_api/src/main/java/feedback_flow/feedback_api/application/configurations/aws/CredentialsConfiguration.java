package feedback_flow.feedback_api.application.configurations.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CredentialsConfiguration {

    private final Dotenv dotenv;

    @Autowired
    public CredentialsConfiguration(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    @Bean
    public AWSCredentials awsCredentials() {
        String accessKey = dotenv.get("AWS_ACCESS_KEY");
        String secretKey = dotenv.get("AWS_SECRET_KEY");
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return credentials;
    }

}
