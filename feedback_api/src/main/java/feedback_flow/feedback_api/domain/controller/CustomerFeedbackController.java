package feedback_flow.feedback_api.domain.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import feedback_flow.feedback_api.domain.model.CustomerFeedback;
import feedback_flow.feedback_api.domain.repository.CustomerFeedbackRepository;
import feedback_flow.feedback_api.domain.service.SNSService;
// import io.swagger.annotations.ApiResponse;
// import io.swagger.annotations.ApiResponses;
// import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/customer-feedback")
public class CustomerFeedbackController {

    private final CustomerFeedbackRepository feedbackRepository;

    @Autowired
    private SNSService service;

    @PostMapping(path = "/publish")
    public ResponseEntity<String> publishMail(@RequestBody List<String> payload) {
        service.pubTopic(payload.get(0), payload.get(1));
        return new ResponseEntity<>("Message published", HttpStatus.OK);
    }

    @Autowired
    public CustomerFeedbackController(CustomerFeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @GetMapping
    // @Operation(summary = "Get the list off feedbacks")
    // @ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved
    // all costumers feedbacks") })
    public ResponseEntity<List<CustomerFeedback>> getAllFeedback() {
        List<CustomerFeedback> feedbackList = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbackList);
    }

    @PostMapping
    // @Operation(summary = "Create a new feedback")
    // @ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved
    // all costumers feedbacks") })
    public ResponseEntity<String> submitFeedback(@RequestBody CustomerFeedback feedback) {
        // Implement logic to save the feedback to your database
        // Return an appropriate response (e.g., success message or error)

        return new ResponseEntity<>("Feedback received", HttpStatus.CREATED);
    }

}
