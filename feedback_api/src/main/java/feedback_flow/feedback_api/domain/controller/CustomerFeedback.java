package feedback_flow.feedback_api.domain.controller;

import feedback_flow.feedback_api.domain.service.SNSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer_feedback")
public class CustomerFeedback {

    @GetMapping
    public String index() {
        return "index route";
    }


    @Autowired
    private SNSService service;

    @PostMapping(path = "/publish")
    public ResponseEntity<String> publishMail(@RequestBody List<String> payload) {

        service.pubTopic(payload.get(0), payload.get(1));
        return new ResponseEntity<>("Message published", HttpStatus.OK);
    }

}
