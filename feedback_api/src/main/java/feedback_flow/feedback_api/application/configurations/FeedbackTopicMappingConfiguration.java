package feedback_flow.feedback_api.application.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class FeedbackTopicMappingConfiguration {

    private final Dotenv dotenv;

    @Autowired
    public FeedbackTopicMappingConfiguration(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public String getTopicArn(CustomerFeedbackType feedbackType) {
        switch (feedbackType) {
            case SUGGESTION:
                return dotenv.get("AWS_SNS_TOPIC_SUGGESTION");
            case COMPLIMENT:
                return dotenv.get("AWS_SNS_TOPIC_COMPLIMENT");
            case CRITIQUE:
                return dotenv.get("AWS_SNS_TOPIC_CRITIQUE");
            default:
                throw new IllegalArgumentException("Unsupported feedback type: " + feedbackType);
        }
    }
}