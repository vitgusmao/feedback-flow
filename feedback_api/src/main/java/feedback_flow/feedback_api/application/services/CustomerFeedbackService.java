package feedback_flow.feedback_api.application.services;

import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback_flow.feedback_api.application.configurations.FeedbackTopicMappingConfiguration;
import feedback_flow.feedback_api.application.gateway.aws.SNSPublisher;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

import java.util.List;

@Service
public class CustomerFeedbackService {

    private final FeedbackTopicMappingConfiguration topicMapping;
    private final SNSPublisher publisher;
    private final CustomerFeedbackRepository repository;

    @Autowired
    public CustomerFeedbackService(CustomerFeedbackRepository repository, SNSPublisher publisher,
            FeedbackTopicMappingConfiguration topicMapping) {
        this.repository = repository;
        this.publisher = publisher;
        this.topicMapping = topicMapping;
    }

    public void publishFeedback(CustomerFeedbackType feedbackType, String message) {
        String queueUrl = topicMapping.getTopicArn(feedbackType);
        publisher.publishMessage(queueUrl, message);
    }

    public CustomerFeedback findCustomerFeedbackById(Long id) throws Exception {
        return this.repository.findCustomerFeedbackById(id)
                .orElseThrow(() -> new Exception("Customer Feedback not found"));
    }

    public CustomerFeedback saveCustomerFeedback(CustomerFeedback customerFeedback) {
        return this.repository.save(customerFeedback);
    }

    public List<CustomerFeedback> showAllCustomerFeedback() {
        return this.repository.findAll();
    }
}
