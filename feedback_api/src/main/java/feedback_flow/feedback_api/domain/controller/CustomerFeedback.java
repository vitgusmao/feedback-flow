package feedback_flow.feedback_api.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer_feedback")
public class CustomerFeedback {

    @GetMapping
    public String index() {
        return "index route";
    }

}
