package feedback_flow.feedback_api.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Get a index string")
    @ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved index") })
    public String home() {
        // Add logic to handle the root mapping
        return "index"; // Return the name of a Thymeleaf or JSP view
    }
}