package brian.example.boot.jms.client;

import brian.example.boot.jms.client.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.Enumeration;

@EnableScheduling
@Component
public class Client {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;

    @Autowired
    public Client(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.mapper = new ObjectMapper();
    }

    @Scheduled(fixedRate = 5000)
    public void sendAndReceiveMessage() throws JMSException {

        LocalTime time = LocalTime.now();

        Person person = Person.builder().name("Brian " + time.getHour() + ":" + time.getMinute() + ":" + time.getSecond())
                .createdDateTime(new Date()).build();
        System.out.println(String.format("Sending Message: %s", person.toString()));

//        TextMessage receivedMsg = (TextMessage) jmsTemplate.sendAndReceive("inbound.queue", new MessageCreator() {
//            @Override
//            public Message createMessage(Session session) throws JMSException {
//                try {
//                    return session.createTextMessage(mapper.writeValueAsString(person));
//                } catch (JsonProcessingException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });
        TextMessage receivedMsg = (TextMessage) jmsTemplate.sendAndReceive("inbound.queue",
                                        session -> {
                                            try {
                                                return session.createTextMessage(mapper.writeValueAsString(person));
                                            } catch (JsonProcessingException e) {
                                                throw new RuntimeException(e);
                                            }
                                        });

        if (receivedMsg != null) {
            try {
                Person updatedPerson = mapper.readValue(receivedMsg.getText(), Person.class);
                System.out.println(String.format("Returned Message: %s", updatedPerson.toString()));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }else
            System.out.println("Received null message");
    }
}