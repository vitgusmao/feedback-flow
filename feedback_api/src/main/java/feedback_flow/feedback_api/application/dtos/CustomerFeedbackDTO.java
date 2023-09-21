package feedback_flow.feedback_api.application.dtos;

import feedback_flow.feedback_api.domain.customer_feedback.CustomerFeedbackStatus;
import feedback_flow.feedback_api.domain.customer_feedback.CustomerFeedbackType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record CustomerFeedbackDTO(
        Long id,
        String message,
        @Enumerated(EnumType.STRING)
        CustomerFeedbackType type
) {
}
