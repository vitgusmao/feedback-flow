package feedback_flow.feedback_api.application.component;

import org.springframework.stereotype.Component;

import feedback_flow.feedback_api.application.services.SQSMessageReceiverService;
import feedback_flow.feedback_api.domain.customerFeedback.CustomerFeedbackType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@Component
public class MessageEventListener {

    private static final Logger LOG = LoggerFactory.getLogger(MessageEventListener.class);

    private final SQSMessageReceiverService sqsMessageReceiverService;

    public MessageEventListener(SQSMessageReceiverService sqsMessageReceiverService) {
        this.sqsMessageReceiverService = sqsMessageReceiverService;

    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationStart() {
        this.startEventListenerThread();
    }

    private void startEventListenerThread() {
        Thread eventListenerThread = new Thread(() -> {
            while (true) {
                try {
                    this.pollMessages();
                    Thread.sleep(1000); // time in milliseconds
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });

        LOG.info("Starting SQS Consumer thread");
        eventListenerThread.start();
    }

    public void pollMessages() {
        CustomerFeedbackType[] feedbackTypes = CustomerFeedbackType.values();

        for (CustomerFeedbackType feedbackType : feedbackTypes) {
            sqsMessageReceiverService.receiveMessages(feedbackType);
        }
    }

}