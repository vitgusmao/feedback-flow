package feedback_flow.queue_consumer.domain.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.amazonaws.services.sqs.AmazonSQS;

import feedback_flow.queue_consumer.domain.model.FeedbackType;
import feedback_flow.queue_consumer.domain.service.LoggerService;
import feedback_flow.queue_consumer.domain.service.SqsMessageListener;

import java.util.ArrayList;
import java.util.List;

public class SqsListenersConfig {

    private final FeedbackSqsQueueMapping feedbackSqsQueueMapping;

    private final LoggerService loggerService;

    @Autowired
    public SqsListenersConfig(FeedbackSqsQueueMapping feedbackSqsQueueMapping, LoggerService loggerService) {
        this.feedbackSqsQueueMapping = feedbackSqsQueueMapping;
        this.loggerService = loggerService;
    }

    @Bean
    public List<SqsMessageListener> sqsMessageListeners(AmazonSQS sqsClient) {
        List<SqsMessageListener> listeners = new ArrayList<>();

        FeedbackType[] sqsTopicTypes = FeedbackType.values();

        for (FeedbackType topicType : sqsTopicTypes) {
            String queueUrl = feedbackSqsQueueMapping.getQueueUrl(topicType);
            listeners.add(new SqsMessageListener(sqsClient, loggerService, queueUrl));
        }

        return listeners;
    }
}
