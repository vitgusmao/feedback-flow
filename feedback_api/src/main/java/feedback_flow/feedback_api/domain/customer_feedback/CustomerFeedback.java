package feedback_flow.feedback_api.domain.customer_feedback;
import feedback_flow.feedback_api.application.dtos.CustomerFeedbackDTO;
import jakarta.persistence.*;
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

    private CustomerFeedbackType type;

    private CustomerFeedbackStatus status;

    public CustomerFeedback(CustomerFeedbackDTO customerFeedbackDTO) {
        this.message = customerFeedbackDTO.message();
        this.type = customerFeedbackDTO.type();
        this.status = CustomerFeedbackStatus.RECEIVED;
    }
}
