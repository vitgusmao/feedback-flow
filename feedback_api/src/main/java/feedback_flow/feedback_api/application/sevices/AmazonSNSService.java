package feedback_flow.feedback_api.application.sevices;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
public class AmazonSNSService {

    private final AmazonSNS amazonSNS;

    @Autowired
    public AmazonSNSService(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public PublishResult publishToSNSTopic(String topicARN, String message) {
        try {
            PublishRequest publishRequest = new PublishRequest(topicARN, message);
            PublishResult result = amazonSNS.publish(publishRequest);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
