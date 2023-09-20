package feedback_flow.feedback_api.domain.configuration;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;

import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sns.AmazonSNS;

@Configuration
public class SNSConfiguration {

    @Value("${accesskey}")
    private String accesskey;
    @Value("${secretey}")
    private String secretey;

    public  AWSCredentials  credenciais(){
        AWSCredentials  credenciais = new BasicAWSCredentials(accesskey, secretey);
        return credenciais;
    }

    public AmazonSNS  amazonSNS(){
        AmazonSNS  sns = AmazonSNSClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credenciais())).withRegion(Regions.AP_SOUTH_1)
                .build();
        return sns;
    }
}
