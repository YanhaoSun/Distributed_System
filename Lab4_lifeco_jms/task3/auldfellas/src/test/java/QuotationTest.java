//import jdk.javadoc.internal.tool.Main;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.ClientInfo;
import service.message.ClientMessage;
import service.message.QuotationMessage;

public class QuotationTest {
    @Test
    public void testService() throws Exception {
        Main.main(new String[0]);

        ConnectionFactory factory =
                new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        Connection connection = factory.createConnection();
        connection.setClientID("test");
        Session session =
                connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue("QUOTATIONS");
        Topic topic = session.createTopic("APPLICATIONS");
        MessageConsumer consumer = session.createConsumer(queue);
        MessageProducer producer = session.createProducer(topic);
        connection.start();

        System.out.println("123");
        producer.send(
                session.createObjectMessage(
                        new ClientMessage(1L, new ClientInfo("Niki Collier",
                                ClientInfo.FEMALE, 49, 1.5494, 80, false,
                                false))));
        System.out.println("456");
        Message message = consumer.receive();
        System.out.println("789");
        QuotationMessage quotationMessage =
                (QuotationMessage) ((ObjectMessage) message).getObject();

        System.out.println("token: " + quotationMessage.getToken());
        System.out.println("quotation: " + quotationMessage.getQuotation());
        message.acknowledge();

        assertEquals(1L, quotationMessage.getToken());
    }
}