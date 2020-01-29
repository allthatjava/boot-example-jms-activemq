package brian.example.boot.jms.client;

import lombok.RequiredArgsConstructor;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.time.LocalTime;

@EnableScheduling
@RequiredArgsConstructor
@Component
public class Client {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedRate = 5000)
    public void sendAndReceiveMessage() throws JMSException {

        LocalTime time = LocalTime.now();

        String strMsg = "{\"name\":\"Brian "+time.getHour()+":"+time.getMinute()+":"+time.getSecond()+"\"}";
        System.out.println(String.format("Sending Message: %s", strMsg));

        TextMessage receivedMsg = (TextMessage) jmsTemplate.sendAndReceive("inbound.queue", new MessageCreator(){

            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(strMsg);
            }
        });

        if( receivedMsg != null )
            System.out.println(String.format("Returned Message: %s", receivedMsg.getText()));
        else
            System.out.println("Received null message");
    }
}
