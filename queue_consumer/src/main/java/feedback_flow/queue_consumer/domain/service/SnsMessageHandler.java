package feedback_flow.queue_consumer.domain.service;

import org.springframework.stereotype.Service;

import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.SubscribeRequest;

@Service
public class SnsMessageHandler {
    private final AmazonSNS amazonSNS;

    public SnsMessageHandler(AmazonSNS amazonSNS) {
        this.amazonSNS = amazonSNS;
    }

    public void subscribeToTopic(String topicARN, String endpoint) {
        SubscribeRequest request = new SubscribeRequest().withTopicArn(topicARN);
        // SubscribeResult result =
        amazonSNS.subscribe(request);

    }

    public void handleMessage(String message) {

    }
}
