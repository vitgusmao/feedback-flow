package feedback_flow.feedback_api.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import feedback_flow.feedback_api.domain.model.CustomerFeedback;

public interface CustomerFeedbackRepository extends JpaRepository<CustomerFeedback, Long> {
}
