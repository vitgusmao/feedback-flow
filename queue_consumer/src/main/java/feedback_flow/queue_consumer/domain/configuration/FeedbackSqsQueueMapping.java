package feedback_flow.queue_consumer.domain.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import feedback_flow.queue_consumer.domain.model.FeedbackType;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class FeedbackSqsQueueMapping {

    private final Dotenv dotenv;

    @Autowired
    public FeedbackSqsQueueMapping(Dotenv dotenv) {
        this.dotenv = dotenv;
    }

    public String getQueueUrl(FeedbackType feedbackType) {

        String typeString = feedbackType.toString();
        String sqsTopic = dotenv.get("AWS_SQS_TOPIC_" + typeString);

        if (sqsTopic == null) {
            throw new IllegalArgumentException("Unsupported feedback type: " + feedbackType);
        }

        return sqsTopic;

    }
}