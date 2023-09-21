package feedback_flow.feedback_api.domain.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class AmazonSNSService {

    private final AmazonSNS amazonSNS;

    private final Dotenv dotenv;

    @Autowired
    public AmazonSNSService(AmazonSNS amazonSNS, Dotenv dotenv) {
        this.amazonSNS = amazonSNS;
        this.dotenv = dotenv;
    }

    public PublishResult publishToSNSTopic(String message) {
        try {
            String snsTopic = dotenv.get("AWS_SNS_TOPIC_SUGGESTION");
            PublishRequest publishRequest = new PublishRequest(snsTopic, message);
            PublishResult result = amazonSNS.publish(publishRequest);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
