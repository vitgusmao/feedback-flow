package feedback_flow.feedback_api.application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;

public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
}
