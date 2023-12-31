package feedback_flow.feedback_api.application.services;

import feedback_flow.feedback_api.application.dtos.CustomerFeedbackDTO;
import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feedback_flow.feedback_api.application.configurations.FeedbackTopicARNMapping;
import feedback_flow.feedback_api.application.gateway.aws.SNSPublisher;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

import java.util.List;

@Service
public class CustomerFeedbackService {

    private final FeedbackTopicARNMapping topicARNMapping;
    private final SNSPublisher publisher;
    private final CustomerFeedbackRepository repository;

    @Autowired
    public CustomerFeedbackService(
            CustomerFeedbackRepository repository,
            SNSPublisher publisher,
            FeedbackTopicARNMapping topicARNMapping) {
        this.repository = repository;
        this.publisher = publisher;
        this.topicARNMapping = topicARNMapping;
    }

    public void publishFeedback(CustomerFeedbackType feedbackType, String message, Long id) {
        String topicARN = topicARNMapping.getConstant(feedbackType);
        publisher.publishMessage(topicARN, message, id);
    }

    public CustomerFeedback findCustomerFeedbackById(Long id) throws Exception {
        return this.repository.findCustomerFeedbackById(id)
                .orElseThrow(() -> new Exception("Customer Feedback not found"));
    }

    public CustomerFeedback saveCustomerFeedback(CustomerFeedback customerFeedback) {
        return this.repository.save(customerFeedback);
    }

    public void updateCustomerFeedback(CustomerFeedbackDTO customerFeedbackDTO, CustomerFeedbackStatus newStatus) throws Exception{
        CustomerFeedback customerFeedback = this.findCustomerFeedbackById(customerFeedbackDTO.id());

        customerFeedback.setStatus(newStatus);

        this.repository.save(customerFeedback);
    }

    public List<CustomerFeedback> showAllCustomerFeedback() {
        return this.repository.findAll();
    }
}
