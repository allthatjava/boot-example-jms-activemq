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

        String response = null;
        if( message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            Person person = mapper.readValue(textMessage.getText(), Person.class);
            response = "Hello "+person.getName();
            // You can also use mapper to map the received
//            Map map = mapper.readValue(textMessage.getText(), Map.class);
//            response = "Hello "+map.get("name");

            System.out.println("Sending back Message: "+response);
        }

        return response;
    }
}
