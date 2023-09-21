package feedback_flow.feedback_api.application.dtos;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackStatus;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record CustomerFeedbackDTO(
        Long id,
        String message,
        @Enumerated(EnumType.STRING)
        CustomerFeedbackType type
) {
}