package brian.example.boot.jms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJms
public class BootExampleJmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootExampleJmsApplication.class, args);
    }

}
