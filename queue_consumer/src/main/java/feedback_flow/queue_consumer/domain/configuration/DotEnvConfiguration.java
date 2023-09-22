package feedback_flow.queue_consumer.domain.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class DotEnvConfiguration {

    @Bean
    public Dotenv dotenv() {
        return Dotenv.configure().directory("./").ignoreIfMalformed().load();
    }
}
