package feedback_flow.feedback_api.application.services;

import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.domain.customer_feedback.CustomerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerFeedbackService {

    @Autowired
    private CustomerFeedbackRepository repository;

    public CustomerFeedback findCustomerFeedbackById(Long id) throws Exception{
        return this.repository.findCustomerFeedbackById(id).orElseThrow(() -> new Exception("Customer Feedback not found"));
    }

    public CustomerFeedback saveCustomerFeedback(CustomerFeedback customerFeedback) {
        return this.repository.save(customerFeedback);
    }

    public List<CustomerFeedback> showAllCustomerFeedback() {
        return repository.findAll();
    }
}
