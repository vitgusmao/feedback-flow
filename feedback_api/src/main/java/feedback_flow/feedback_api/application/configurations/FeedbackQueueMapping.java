package feedback_flow.feedback_api.application.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class FeedbackQueueMapping {

    @Autowired
    private Dotenv dotenv;

    @Bean
    public FeedbackQueueMapping queueMapping() {
        return new FeedbackQueueMapping();
    }

    public String getQueueUrl(CustomerFeedbackType feedbackType) {

        String typeString = feedbackType.toString();
        String queueUrl = dotenv.get("AWS_SQS_QUEUE_" + typeString);

        if (queueUrl == null) {
            throw new IllegalArgumentException("Unsupported feedback type: " + feedbackType);
        }

        return queueUrl;
    }
}