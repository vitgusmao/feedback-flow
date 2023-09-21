package feedback_flow.feedback_api.application.sevices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;

import feedback_flow.feedback_api.application.configurations.FeedbackTopicMappingConfiguration;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

@Service
public class CustomerFeedbackService extends AmazonSNSService {

    private final FeedbackTopicMappingConfiguration topicMapping;

    @Autowired
    public CustomerFeedbackService(AmazonSNS amazonSNS, FeedbackTopicMappingConfiguration topicMapping) {
        super(amazonSNS);
        this.topicMapping = topicMapping;
    }

    public PublishResult publishFeedback(CustomerFeedbackType feedbackType, String message) {
        String topicArn = topicMapping.getTopicArn(feedbackType);
        return this.publishToSNSTopic(topicArn, message);
    }
}
