package feedback_flow.queue_consumer.domain.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;

@Service
public class SqsMessageListener {

    private final LoggerService loggerService;

    private final AmazonSQS sqsClient;

    private final String queueUrl;

    public SqsMessageListener(AmazonSQS sqsClient, LoggerService loggerService, String queueUrl) {
        this.sqsClient = sqsClient;
        this.loggerService = loggerService;
        this.queueUrl = queueUrl;
    }

    public List<Message> listenMessages() {
        ReceiveMessageRequest receiveMessageRequest = new ReceiveMessageRequest(this.queueUrl);
        List<Message> messages = sqsClient.receiveMessage(receiveMessageRequest).getMessages();

        for (Message message : messages) {
            loggerService.logMessage(this.queueUrl, message.getBody());
        }

        return messages;
    }

}