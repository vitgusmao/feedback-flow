package feedback_flow.feedback_api.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.services.sns.model.PublishResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.application.requests.CreateCustomerFeedbackRequest;
import feedback_flow.feedback_api.application.sevices.CustomerFeedbackService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/customer-feedback")
public class CustomerFeedbackController {

    private final CustomerFeedbackRepository feedbackRepository;

    private final CustomerFeedbackService feedbackService;

    @Autowired
    public CustomerFeedbackController(CustomerFeedbackRepository feedbackRepository,
            CustomerFeedbackService feedbackService) {
        this.feedbackRepository = feedbackRepository;
        this.feedbackService = feedbackService;
    }

    @GetMapping
    @Operation(summary = "Get the list off feedbacks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all costumers feedbacks")
    })

    public ResponseEntity<List<CustomerFeedback>> getAllFeedback() {
        List<CustomerFeedback> feedbackList = feedbackRepository.findAll();
        return ResponseEntity.ok(feedbackList);
    }

    @PostMapping
    @Validated
    @Operation(summary = "Create a new feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all costumers feedbacks") })
    public ResponseEntity<String> submitFeedback(@Valid @RequestBody CreateCustomerFeedbackRequest feedback) {
        CustomerFeedbackType feedBackType = feedback.getType();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String feedBackJsonString = objectMapper.writeValueAsString(feedback);
            PublishResult result = feedbackService.publishFeedback(feedBackType, feedBackJsonString);
            return new ResponseEntity<>("Feedback received with ID: " + result.getMessageId(), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
