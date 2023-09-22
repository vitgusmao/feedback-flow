package feedback_flow.queue_consumer.domain.service;

import org.springframework.stereotype.Service;

@Service
public class LoggerService {

    public void logMessage(String topic, String message) {
        System.out.println("Received message from " + topic + ": " + message);
    }
}
