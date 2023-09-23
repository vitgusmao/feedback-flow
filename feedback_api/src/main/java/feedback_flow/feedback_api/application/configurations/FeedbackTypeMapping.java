package feedback_flow.feedback_api.application.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;
import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public abstract class FeedbackTypeMapping {

    @Autowired
    private Dotenv dotenv;

    public abstract String getConstant(CustomerFeedbackType feedbackType);

    public String getConstant(CustomerFeedbackType feedbackType, String constantStringFormat) {

        String typeString = feedbackType.toString();
        String feedbackTypeConstant = dotenv.get(constantStringFormat.formatted(typeString));

        if (feedbackTypeConstant == null) {
            throw new IllegalArgumentException("Unsupported feedback type: " + feedbackType);
        }
        return feedbackTypeConstant;
    }

}
