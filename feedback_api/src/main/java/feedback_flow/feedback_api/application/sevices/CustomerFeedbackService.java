package feedback_flow.feedback_api.application.sevices;

import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishResult;

import feedback_flow.feedback_api.application.configurations.FeedbackTopicMappingConfiguration;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

import java.util.List;

@Service
public class CustomerFeedbackService extends AmazonSNSService {

    private final FeedbackTopicMappingConfiguration topicMapping;

    @Autowired
    public CustomerFeedbackService(AmazonSNS amazonSNS, FeedbackTopicMappingConfiguration topicMapping) {
        super(amazonSNS);
        this.topicMapping = topicMapping;
    }

    @Autowired
    private CustomerFeedbackRepository repository;

    public PublishResult publishFeedback(CustomerFeedbackType feedbackType, String message) {
        String topicArn = topicMapping.getTopicArn(feedbackType);
        return this.publishToSNSTopic(topicArn, message);
    }

    public CustomerFeedback findCustomerFeedbackById(Long id) throws Exception{
        return this.repository.findCustomerFeedbackById(id).orElseThrow(() -> new Exception("Customer Feedback not found"));
    }

    public CustomerFeedback saveCustomerFeedback(CustomerFeedback customerFeedback) {
        return this.repository.save(customerFeedback);
    }

    public List<CustomerFeedback> showAllCustomerFeedback() {
        return this.repository.findAll();
    }
}
