package feedback_flow.feedback_api.application.gateway.aws;

import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import feedback_flow.feedback_api.application.component.QueueNameExtractor;

@Component
public class SQSConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SQSConsumer.class);

    private final AmazonSQS amazonSQS;

    private Boolean previouslyReceivedMessages = true;

    @Autowired
    public SQSConsumer(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public ReceiveMessageResult receiveMessages(String queueURL) {
        try {
            LOG.info(QueueNameExtractor.extractQueueName(queueURL));
            ReceiveMessageRequest sendMessageRequest = new ReceiveMessageRequest()
                    .withQueueUrl(queueURL)
                    .withMaxNumberOfMessages(1)
                    .withWaitTimeSeconds(5);

            ReceiveMessageResult result = amazonSQS.receiveMessage(sendMessageRequest);

            this.logMessageReceipt(queueURL, result.clone());

            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteMessage(String queueUrl, String receiptHandle) {
        amazonSQS.deleteMessage(queueUrl, receiptHandle);
    }

    private void logMessageReceipt(String queueURL, ReceiveMessageResult result) {

        int messageCount = result.getMessages().size();
        if (messageCount > 0) {
            String queueName = QueueNameExtractor.extractQueueName(queueURL);
            LOG.info("%d messages received successfully from '%s' queue.".formatted(messageCount, queueName));
            this.previouslyReceivedMessages = true;
        } else if (previouslyReceivedMessages) {
            LOG.info("No messages received.");
            this.previouslyReceivedMessages = false;
        }
    }

}
