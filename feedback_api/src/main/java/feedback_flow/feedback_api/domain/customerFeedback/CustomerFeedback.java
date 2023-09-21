package feedback_flow.feedback_api.domain.customerFeedback;

import feedback_flow.feedback_api.application.dtos.CustomerFeedbackDTO;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CustomerFeedback {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 250, nullable = false)
    private String message;

    @Column(nullable = false)
    private CustomerFeedbackType type;

    @Column(nullable = false)
    private CustomerFeedbackStatus status;

    public CustomerFeedback(CustomerFeedbackDTO customerFeedbackDTO) {
        this.message = customerFeedbackDTO.message();
        this.type = customerFeedbackDTO.type();
        this.status = CustomerFeedbackStatus.RECEIVED;
    }
}
