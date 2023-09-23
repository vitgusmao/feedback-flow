package feedback_flow.feedback_api.application.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class FeedbackTopicMappingConfiguration {

    @Autowired
    private Dotenv dotenv;

    public String getTopicArn(CustomerFeedbackType feedbackType) {

        String typeString = feedbackType.toString();
        String topicARN = dotenv.get("AWS_SNS_TOPIC_" + typeString);

        if (topicARN == null) {
            throw new IllegalArgumentException("Unsupported feedback type: " + feedbackType);
        }
        return topicARN;
    }
}