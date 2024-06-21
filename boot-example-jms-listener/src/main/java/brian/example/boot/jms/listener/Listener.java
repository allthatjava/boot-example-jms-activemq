package brian.example.boot.jms.listener;

import brian.example.boot.jms.model.Person;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.util.Date;
import java.util.Map;

@Component
public class Listener {

    ObjectMapper mapper;

    public Listener(){
        mapper = new ObjectMapper();
    }

    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public String receiveMessage(final Message message) throws JsonProcessingException, JMSException {

        System.out.println("Listener Received Message: "+message);

        Person person = null;
        if( message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            person = mapper.readValue(textMessage.getText(), Person.class);
            person.setReceivedDateTime(new Date());
            // You can also use mapper to map the received
//            Map map = mapper.readValue(textMessage.getText(), Map.class);
//            response = "Hello "+map.get("name");

            System.out.println("Sending back Message: "+person);
        }

        return mapper.writeValueAsString(person);
    }
}
