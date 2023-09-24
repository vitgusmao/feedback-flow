package feedback_flow.feedback_api.application.component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class QueueNameExtractor {

    public static String extractQueueName(String queueURL) {
        String regexPattern = "https:\\/\\/sqs\\..*\\.amazonaws\\.com\\/\\d+\\/((\\w*|\\d*)\\.fifo)";
        Pattern pattern = Pattern.compile(regexPattern);
        Matcher matcher = pattern.matcher(queueURL);

        if (matcher.find()) {
            return matcher.group(1);
        } else {

            return "";
        }
    }

}
