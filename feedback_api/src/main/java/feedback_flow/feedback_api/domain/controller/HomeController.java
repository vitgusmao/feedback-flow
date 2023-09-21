package feedback_flow.feedback_api.domain.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Get a index string")
    @ApiResponses({ @ApiResponse(code = 200, message = "Successfully retrieved index") })
    public String home() {
        // Add logic to handle the root mapping
        return "index"; // Return the name of a Thymeleaf or JSP view
    }
}
