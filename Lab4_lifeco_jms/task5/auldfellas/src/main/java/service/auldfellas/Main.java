package service.auldfellas;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.auldfellas.AFQService;
import service.core.Quotation;
import service.message.ClientMessage;
import service.message.QuotationMessage;

import javax.jms.*;
public class Main {
    private static AFQService service = new AFQService();

    public static void main(String[] args) {
        String host = args.length == 0 ? "localhost":args[0];
        ConnectionFactory factory =
                new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.setClientID("auldfellas");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            MessageConsumer consumer = session.createConsumer(topic);
            MessageProducer producer = session.createProducer(queue);

//          makes the service consume incoming ClientMessage message,
//          generating a Quotation and then producing a QuotationMessage message
//          that it submits to the QUOTATIONS queue.
//            System.out.println("before receive message in auldfellas");
            connection.start();

            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
//                        System.out.println("application ready to receive message in auldfellas");
                        ClientMessage request = (ClientMessage)
                                ((ObjectMessage) message).getObject();
//                        System.out.println("in auldfellas, token = "+request.getToken());
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