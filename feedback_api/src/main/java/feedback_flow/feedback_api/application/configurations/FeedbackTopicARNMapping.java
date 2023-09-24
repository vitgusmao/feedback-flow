package feedback_flow.feedback_api.application.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

@Configuration
public class FeedbackTopicARNMapping extends FeedbackTypeMapping {

    @Bean
    public FeedbackTopicARNMapping topicARNMapping() {
        return new FeedbackTopicARNMapping();
    }

    @Override
    public String getConstant(CustomerFeedbackType feedbackType) {
        return super.getConstant(feedbackType, "AWS_SNS_TOPIC_%s");
    }
}