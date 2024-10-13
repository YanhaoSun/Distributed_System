package service.dodgygeezers;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.Quotation;
import service.dodgygeezers.DGQService;
import service.message.ClientMessage;
import service.message.QuotationMessage;

import javax.jms.*;
public class Main {
    private static DGQService service = new DGQService();

    public static void main(String[] args) {
        ConnectionFactory factory =
                new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.setClientID("dodgygeezers");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

//          makes the service consume incoming ClientMessage message,
//          generating a Quotation and then producing a QuotationMessage message
//          that it submits to the QUOTATIONS queue.
            connection.start();
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        ClientMessage request = (ClientMessage)
                                ((ObjectMessage) message).getObject();
                        Quotation quotation = service.generateQuotation(request.getInfo());
                        Message response =
                                session.createObjectMessage(
                                        new QuotationMessage(request.getToken(), quotation));
                        producer.send(response);
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }

    }
}