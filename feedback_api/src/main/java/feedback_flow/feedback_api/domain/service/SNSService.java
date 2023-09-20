package feedback_flow.feedback_api.domain.service;

import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.PublishRequest;


@Service
public class SNSService {

    @Autowired
    private AmazonSNS sns;

    @Value("${topic.arn}")
    private String topicARN;

    public void pubTopic(String subject, String message) {

        try {
            PublishRequest request = new PublishRequest(topicARN, message);
            request.setSubject(subject);
            sns.publish(request);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
