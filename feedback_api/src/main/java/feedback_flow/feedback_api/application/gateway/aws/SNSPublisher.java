package feedback_flow.feedback_api.application.gateway.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Component
public class SNSPublisher {

    private static final Logger LOG = LoggerFactory.getLogger(SNSPublisher.class);

    private final AmazonSNS amazonSNS;

    private final String messageGroupId = "feedbackGroup";

    @Autowired
    public SNSPublisher(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void publishMessage(String topicARN, String message) {
        try {
            PublishRequest publishRequest = new PublishRequest()
                    .withTopicArn(topicARN)
                    .withMessage(message)
                    .withMessageGroupId(messageGroupId);

            PublishResult result = amazonSNS.publish(publishRequest);
            LOG.info("Message published successfully, ID: " + result.getMessageId());

        } catch (Exception e) {
            throw e;
        }
    }

}
