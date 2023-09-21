package feedback_flow.feedback_api.domain.request;

import feedback_flow.feedback_api.domain.model.CustomerFeedback;
import feedback_flow.feedback_api.domain.model.CustomerFeedbackType;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerFeedbackRequest {

    private String message;
    private CustomerFeedbackType type;

    public CreateCustomerFeedbackRequest(CustomerFeedback feedback) {
        this.message = feedback.getMessage();
        this.type = feedback.getType();
    }

}
