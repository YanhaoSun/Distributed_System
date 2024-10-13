import org.apache.activemq.ActiveMQConnectionFactory;

import service.core.PurchaseInfo;
import service.stock.stockService;
import service.core.Product;
import service.message.ClientMessage;
import service.message.ProductMessage;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Message;
import javax.jms.Topic;
import javax.jms.Session;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Main {
    private static stockService service = new stockService();
    public static void main(String[] args) {
        String host = args.length == 0 ? "localhost" : args[0];
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://"+host+":61616");
        try {
            Connection connection = connectionFactory.createConnection();
            connection.setClientID("stocks"); 
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
            Queue queue = session.createQueue("PRODUCTS"); 
            Topic topic = session.createTopic("CLIENTINFO");
            Queue purchaseRequest = session.createQueue("PURCHASESTOCKS");
            Queue purchaseResponse = session.createQueue("PURCHASERESULT");
            Queue failureHandleRequest = session.createQueue("FAILURESTOCKS");
            MessageConsumer consumer = session.createConsumer(topic); 
            MessageProducer producer = session.createProducer(queue);
            MessageConsumer requestConsumer = session.createConsumer(purchaseRequest);
            MessageProducer responseProducer = session.createProducer(purchaseResponse);
            MessageConsumer failureConsumer = session.createConsumer(failureHandleRequest);

            connection.start();
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        ClientMessage request = (ClientMessage) ((ObjectMessage) message).getObject();
                        System.out.println("Stock service has received client info of " + request.getInfo().name);
                        Product product = service.generateProduct(request.getInfo());
                        Message response = session.createObjectMessage(new ProductMessage(request.getToken(), product));
                        producer.send(response);
                        System.out.println("Stock service has sent the product");
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }     
            });


            requestConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        PurchaseInfo request = (PurchaseInfo) ((ObjectMessage) message).getObject();
                        System.out.println("Stock service has received purchase info of user " + request.userId);
                        String result = "Failed purchase";

                        try (java.sql.Connection conn = DatabaseUtil.getConnection()) {
                            String sql = "INSERT INTO stock_purchases (purchase_id, user_id, purchase_time, investment) VALUES (?, ?, ?, ?)";
                            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                stmt.setInt(1, request.purchaseId);
                                stmt.setInt(2, request.userId);
                                stmt.setString(3, LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME)); // 获取当前时间
                                stmt.setDouble(4, request.investment);
                                int affectedRows = stmt.executeUpdate();
                                if (affectedRows > 0) {
                                    result = "Purchase successful";
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                            result = "Database error: " + e.getMessage();
                        }

                        Message response = session.createObjectMessage(result);
                        responseProducer.send(response);
                        System.out.println("Stock service has sent the purchase result");
                        message.acknowledge();
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            });

            failureConsumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try {
                        PurchaseInfo request = (PurchaseInfo) ((ObjectMessage) message).getObject();
                        System.out.println("Stock service has received the failure handle request of user " + request.userId);

                        try (java.sql.Connection conn = DatabaseUtil.getConnection()) {
                            String sql = "DELETE FROM stock_purchases WHERE purchase_id = ?";
                            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                                stmt.setInt(1, request.purchaseId);
                                int affectedRows = stmt.executeUpdate();
                                if (affectedRows > 0) {
                                    System.out.println("Deleted purchase record with ID: " + request.purchaseId);
                                } else {
                                    System.out.println("No record found with ID: " + request.purchaseId + " to delete.");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException("Error during database operation: " + e.getMessage(), e);
                            }
                        }


                        System.out.println("Stock service has handled the failure");
                        message.acknowledge();
                    } catch (JMSException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (JMSException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
    }
}
