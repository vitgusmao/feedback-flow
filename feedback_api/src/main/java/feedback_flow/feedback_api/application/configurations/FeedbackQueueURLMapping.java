package feedback_flow.feedback_api.application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

@Configuration
public class FeedbackQueueURLMapping extends FeedbackTypeMapping {

    @Bean
    public FeedbackQueueURLMapping queueURLMapping() {
        return new FeedbackQueueURLMapping();
    }

    @Override
    public String getConstant(CustomerFeedbackType feedbackType) {
        return super.getConstant(feedbackType, "AWS_SQS_QUEUE_%s");
    }
}