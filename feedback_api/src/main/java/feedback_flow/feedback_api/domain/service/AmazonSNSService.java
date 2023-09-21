package feedback_flow.feedback_api.domain.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
public class AmazonSNSService {

    private final AmazonSNS amazonSNS;

    @Value("${aws.sns.topic.suggestion}")
    private String snsTopic;

    @Autowired
    public AmazonSNSService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public PublishResult publishToSNSTopic(String message) {
        try {
            PublishRequest publishRequest = new PublishRequest(snsTopic, message);
            PublishResult result = amazonSNS.publish(publishRequest);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
