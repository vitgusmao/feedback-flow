package feedback_flow.feedback_api.application.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
public class HomeController {

    @GetMapping("/")
    @Operation(summary = "Only redirects to swagger-ui")
    @ApiResponses(value = { @ApiResponse(responseCode = "308", description = "Successfully redirected") })
    public RedirectView home() {
        return new RedirectView("/swagger-ui/index.html");
    }
}
