package feedback_flow.feedback_api.application.gateway.aws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

@Component
public class SQSConsumer {

    private static final Logger LOG = LoggerFactory.getLogger(SQSConsumer.class);

    private final AmazonSQS amazonSQS;

    @Autowired
    public SQSConsumer(AmazonSQS amazonSQS) {
        this.amazonSQS = amazonSQS;
    }

    public ReceiveMessageResult receiveMessages(String queueURL) {
        try {
            ReceiveMessageRequest sendMessageRequest = new ReceiveMessageRequest()
                    .withQueueUrl(queueURL)
                    .withMaxNumberOfMessages(1)
                    .withWaitTimeSeconds(5);

            ReceiveMessageResult result = amazonSQS.receiveMessage(sendMessageRequest);
            int messageCount = result.clone().getMessages().size();
            if (messageCount > 0) {
                LOG.info("%d messages received successfully.".formatted(messageCount));
            } else {
                LOG.info("No messages received.");
            }

            return result;
        } catch (Exception e) {
            throw e;
        }
    }

    public void deleteMessage(String queueUrl, String receiptHandle) {
        amazonSQS.deleteMessage(queueUrl, receiptHandle);
    }

}
