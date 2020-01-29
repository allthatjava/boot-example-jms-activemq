# Spring Boot JMS Example

This example has two parts. Listener and Client. The Listener uses Apache Active MQ to take the request from client then returned processed value.
Client send a request through Listener's MQ.

### Apache ActiveMQ
Used Apache ActiveMQ docker image.

### JMS Listener (boot-example-jms-listener)
Takes the JSON format request and convert to Person.java object. Then returns modified data.

##### application.properties
These are the minimum configuration for the Listener side. 
```properties
spring.activemq.broker-url=tcp://{Apache ActiveMQ Server IP}:{port}
spring.activemq.user=admin
spring.activemq.password=admin
```

##### Listener.java
@JmsListner and @SendTo annotation most of the heavy lifting.
```java
    @JmsListener(destination = "inbound.queue")
    @SendTo("outbound.queue")
    public String receiveMessage(final Message message) throws JsonProcessingException, JMSException {
    ...
    }
```


### JMS Client (boot-example-jms-client)
Command line application. Run then scheduler will kick in then send a request to MQ server every 5 seconds.

##### application.properties
You can setup these properties in JmsTemplate as well.
```properties
spring.activemq.broker-url=tcp://{Apache ActiveMQ Server IP}:{port}
spring.jms.template.default-destination=inbound.queue
spring.jms.template.receive-timeout=2s
```

##### Client.java
Use JmsTemplate to send a request to MQ Server. Also, implement the CallBack method to process returned message.
```java
...
TextMessage receivedMsg = (TextMessage) jmsTemplate.sendAndReceive("inbound.queue", new MessageCreator(){
    @Override
    public Message createMessage(Session session) throws JMSException {
        return session.createTextMessage(strMsg);
    }
});
...
```



### References
Apache ActiveMQ docker image: https://hub.docker.com/r/rmohr/activemq
