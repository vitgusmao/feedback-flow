package feedback_flow.feedback_api.application.controllers;

import feedback_flow.feedback_api.application.dtos.CustomerFeedbackDTO;
import feedback_flow.feedback_api.application.services.CustomerFeedbackService;
import feedback_flow.feedback_api.application.services.SNSService;
import feedback_flow.feedback_api.domain.customer_feedback.CustomerFeedback;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer_feedback")
public class CustomerFeedbackController {

    @Autowired
    private SNSService snsService;

    @Autowired
    private CustomerFeedbackService customerFeedbackService;

    @GetMapping
    public List<CustomerFeedback> index() {
        return this.customerFeedbackService.showAllCustomerFeedback();
    }

    @PostMapping(path = "/publish")
    public ResponseEntity<String> publishMail(@RequestBody CustomerFeedbackDTO costumerFeedbackDTO) {
        CustomerFeedback newCustomerFeedback = this.customerFeedbackService.saveCustomerFeedback(new CustomerFeedback(costumerFeedbackDTO));

        snsService.pubTopic(
                newCustomerFeedback.getType().toString(),
                newCustomerFeedback.getMessage()
        );
        return new ResponseEntity<>("Message published", HttpStatus.OK);
    }

}
