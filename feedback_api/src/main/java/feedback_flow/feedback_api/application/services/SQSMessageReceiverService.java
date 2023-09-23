package feedback_flow.feedback_api.application.services;

import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import feedback_flow.feedback_api.application.configurations.FeedbackQueueMapping;
import feedback_flow.feedback_api.application.gateway.aws.SQSConsumer;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

@Service
public class SQSMessageReceiverService {

    private final SQSConsumer consumer;
    private final FeedbackQueueMapping queueMapping;

    @Autowired
    public SQSMessageReceiverService(SQSConsumer consumer, FeedbackQueueMapping queueMapping) {
        this.consumer = consumer;
        this.queueMapping = queueMapping;
    }

    private static final Logger LOG = LoggerFactory.getLogger(SQSMessageReceiverService.class);

    public void receiveMessages(CustomerFeedbackType feedbackType) {
        String queueUrl = queueMapping.getQueueUrl(feedbackType);

        ReceiveMessageResult receiveMessageResult = consumer.receiveMessages(queueUrl);
        List<Message> messages = receiveMessageResult.getMessages();

        this.processMessages(queueUrl, messages);
    }

    private void processMessages(String queueUrl, List<Message> messages) {
        for (Message message : messages) {

            LOG.info(message.getMessageId() + message.getBody());

            try {
                this.processMessage(message);
                consumer.deleteMessage(queueUrl, message.getReceiptHandle());
            } catch (Exception e) {
                LOG.error("Error processing message: " + e.getMessage());
            }
        }
    }

    private void processMessage(Message message) {
        throw new NotImplementedException();
    }

}
