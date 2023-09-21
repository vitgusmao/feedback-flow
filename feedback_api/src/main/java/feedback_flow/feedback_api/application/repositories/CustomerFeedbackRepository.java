package feedback_flow.feedback_api.application.repositories;

import feedback_flow.feedback_api.domain.customer_feedback.CustomerFeedback;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
    Optional<CustomerFeedback> findCustomerFeedbackById(Long id);
}
