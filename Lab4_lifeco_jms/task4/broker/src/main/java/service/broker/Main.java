package service.broker;

import org.apache.activemq.ActiveMQConnectionFactory;
//import service.auldfellas.AFQService;
import service.core.ClientInfo;
import service.core.Quotation;
import service.message.ClientMessage;
import service.message.OfferMessage;
import service.message.QuotationMessage;

import javax.jms.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory factory =
                new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        Connection connection = null;
        try {
            connection = factory.createConnection();
            connection.setClientID("brokerService");
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Queue queue = session.createQueue("QUOTATIONS");
            Topic topic = session.createTopic("APPLICATIONS");
            Queue offers = session.createQueue("OFFERS");

            MessageConsumer applicationConsumer = session.createConsumer(topic);
            MessageConsumer quotationConsumer = session.createConsumer(queue);
            MessageProducer offerProducer = session.createProducer(offers);

            Map<Long, OfferMessage> tempCache = new HashMap<>();
            connection.start();
            final Long[] token = new Long[1];
            applicationConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        ClientMessage request = (ClientMessage)
                                ((ObjectMessage) message).getObject();
                        token[0] = request.getToken();
                        tempCache.put(request.getToken(), new OfferMessage(request.getInfo(), new LinkedList<>()));
                        new Thread(){
                            public void run() {
                                // this code is in the thread
                                Long newToken = token[0];
                                try {
                                    Thread.sleep(3000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                try {
                                    Message response = session.createObjectMessage(tempCache.get(newToken));
                                    offerProducer.send(response);
                                } catch (JMSException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }.start();
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });
            quotationConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        QuotationMessage request = (QuotationMessage)
                                ((ObjectMessage) message).getObject();
                        System.out.println("received quotation token = "+request.getToken());

                        tempCache.get(request.getToken()).getQuotations().add(request.getQuotation());
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
