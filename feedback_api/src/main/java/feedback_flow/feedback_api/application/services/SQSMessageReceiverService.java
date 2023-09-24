package feedback_flow.feedback_api.application.services;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import feedback_flow.feedback_api.application.dtos.CustomerFeedbackDTO;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackStatus;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import feedback_flow.feedback_api.application.configurations.FeedbackQueueURLMapping;
import feedback_flow.feedback_api.application.gateway.aws.SQSConsumer;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

@Service
public class SQSMessageReceiverService {

    private final SQSConsumer consumer;
    private final FeedbackQueueURLMapping queueURLMapping;
    private final CustomerFeedbackService feedbackService;

    private final Dotenv dotenv;

    @Autowired
    public SQSMessageReceiverService(SQSConsumer consumer, FeedbackQueueURLMapping queueURLMapping, CustomerFeedbackService feedbackService, Dotenv dotenv) {
        this.consumer = consumer;
        this.queueURLMapping = queueURLMapping;
        this.feedbackService = feedbackService;
        this.dotenv = dotenv;
    }

    private static final Logger LOG = LoggerFactory.getLogger(SQSMessageReceiverService.class);

    public void receiveMessages(CustomerFeedbackType feedbackType) {
        String queueUrl = queueURLMapping.getConstant(feedbackType);

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

    private void processMessage(Message message) throws Exception {
        LOG.info(message.getMessageId());
        ObjectMapper objectMapper = new ObjectMapper();
        CustomerFeedbackDTO customerFeedbackDTO = objectMapper.readValue(new JSONObject(message.getBody()).getString("Message"), CustomerFeedbackDTO.class);

        this.feedbackService.updateCustomerFeedback(customerFeedbackDTO, CustomerFeedbackStatus.PROCESSING);

        Thread.sleep(Long.parseLong(dotenv.get("TIME_PROCESSING_QUEUE")));

        this.feedbackService.updateCustomerFeedback(customerFeedbackDTO, CustomerFeedbackStatus.FINISHED);
    }

}
