package feedback_flow.feedback_api.application.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedback;
import feedback_flow.feedback_api.application.repositories.CustomerFeedbackRepository;
import feedback_flow.feedback_api.application.requests.CreateCustomerFeedbackRequest;
import feedback_flow.feedback_api.application.services.CustomerFeedbackService;
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

    @GetMapping("/history")
    @Operation(summary = "Get the list off feedbacks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all costumers feedbacks")
    })
    public List<CustomerFeedback> getAllFeedbackOfHistory() {
        return feedbackService.showAllCustomerFeedback();
    }

    @GetMapping("/history/{customerFeedBackId}")
    @Operation(summary = "Get a specific feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the specific costumer feedback")
    })
    public CustomerFeedback show(@PathVariable Long customerFeedBackId) throws Exception {
        return feedbackService.findCustomerFeedbackById(customerFeedBackId);
    }

    @PostMapping
    @Validated
    @Operation(summary = "Create a new feedback")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all costumers feedbacks") })
    public ResponseEntity<String> submitFeedback(@Valid @RequestBody CreateCustomerFeedbackRequest feedback) {

        CustomerFeedback customerFeedback = this.feedbackService.saveCustomerFeedback(new CustomerFeedback(feedback));

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String feedBackJsonString = objectMapper.writeValueAsString(customerFeedback);
            feedbackService.publishFeedback(customerFeedback.getType(), feedBackJsonString, customerFeedback.getId());

            return new ResponseEntity<>(objectMapper.writeValueAsString(customerFeedback), HttpStatus.CREATED);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(e.getMessage());
        }

    }

}
