package service.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import service.core.ClientInfo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import service.core.ProductList;
import service.core.PurchaseInfo;
import service.core.PurchaseResult;
import service.message.ClientMessage;
import service.message.ProductMessage;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;


@RestController
public class BrokerController {
    @Value("${server.port}") 
    private int port;

    @Value("${HOST:localhost}")  // Default to 'localhost' if not set
    private String host;

    @PostMapping(value = "/products", consumes = "application/json")
    public ResponseEntity<ProductList> getProductList(@RequestBody ClientInfo clientInfo) {
        System.out.println("Broker has received client info of " + clientInfo.name);
        ProductList productList = new ProductList(clientInfo);
        // ArrayList<Product> products = new ArrayList<>();
        CountDownLatch latch = new CountDownLatch(3);

        // ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
             
            Queue queue = session.createQueue("PRODUCTS");
            Topic topic = session.createTopic("CLIENTINFO");
            MessageProducer producer = session.createProducer(topic);
            MessageConsumer consumer = session.createConsumer(queue);

            connection.start();
            producer.send(session.createObjectMessage(new ClientMessage(productList.id, clientInfo)));
            System.out.println("Broker has sent client info of " + clientInfo.name);

            consumer.setMessageListener(message -> {
                try {
                    ProductMessage productMessage = (ProductMessage) ((ObjectMessage) message).getObject();
                    System.out.println("Broker has received the product from " + productMessage.getProduct().name);
                    message.acknowledge();
                    productList.products.add(productMessage.getProduct());
                    System.out.println("Broker has added the product from " + productMessage.getProduct().name);
                    System.out.println("The size of product list now is : " + productList.products.size());
                    latch.countDown();  // Decrease count on receiving each message
                    if (latch.getCount() == 0) {
                        System.out.println("Received all expected messages, releasing latch.");
                        consumer.close();  // Close consumer to stop listening for more messages
                        session.close();   // Optionally close session
                        connection.close(); // Close connection
                        System.out.println("Closed JMS resources after receiving enough messages.");
                        System.out.println("-----------------------------------------");
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

            // Wait for the latch to count down or timeout
            boolean completed = latch.await(30, TimeUnit.SECONDS);
            if (!completed) {
                System.out.println("Processing did not complete in time, remaining count: " + latch.getCount());
            } else {
                System.out.println("Processing complete, proceeding with response.");
            }

        } catch (JMSException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }

        String url = "http://" + getHost() + "/products/" + productList.id;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", url)
                .header("Content-Location", url)
                .body(productList);
    }

    @PostMapping(value = "/purchase", consumes = "application/json")
    public ResponseEntity<PurchaseResult> performPurchase(@RequestBody PurchaseInfo purchaseInfo) {
        System.out.println("Broker has received purchase info of user id " + purchaseInfo.userId);
        String productName = purchaseInfo.productName;
        PurchaseResult result = new PurchaseResult(purchaseInfo.purchaseId, purchaseInfo.userId,productName,"Failed purchase");

        CountDownLatch latch = new CountDownLatch(1);

        // ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Queue purchaseRequest;
            switch (productName) {
                case "bonds":
                    purchaseRequest = session.createQueue("PURCHASEBONDS");
                    break;
                case "funds":
                    purchaseRequest = session.createQueue("PURCHASEFUNDS");
                    break;
                case "stocks":
                    purchaseRequest = session.createQueue("PURCHASESTOCKS");
                    break;
                default:
                    throw new Exception("Invalid product type");
            }
//            Queue queue = session.createQueue("PRODUCTS");
            Queue purchaseResponse = session.createQueue("PURCHASERESULT");
            MessageProducer producer = session.createProducer(purchaseRequest);
            MessageConsumer consumer = session.createConsumer(purchaseResponse);

            connection.start();
            producer.send(session.createObjectMessage(purchaseInfo));
            System.out.println("Broker has sent purchase info to " + purchaseInfo.productName);

            consumer.setMessageListener(message -> {
                try {
                    String response = (String) ((ObjectMessage) message).getObject();
                    System.out.println("Broker has received the purchase response from " + purchaseInfo.productName + " for user " + purchaseInfo.userId);
                    message.acknowledge();
                    result.result = response;
                    latch.countDown();  // Decrease count on receiving each message
                    if (latch.getCount() == 0) {
                        System.out.println("Received all expected messages, releasing latch.");
                        consumer.close();  // Close consumer to stop listening for more messages
                        session.close();   // Optionally close session
                        connection.close(); // Close connection
                        System.out.println("Closed JMS resources after receiving enough messages.");
                        System.out.println("-----------------------------------------");
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });

            // Wait for the latch to count down or timeout
            boolean completed = latch.await(5, TimeUnit.SECONDS);
            if (!completed) {
                System.out.println("Processing did not complete in time, remaining count: " + latch.getCount());
            } else {
                System.out.println("Processing complete, proceeding with response.");
            }

        } catch (JMSException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        String url = "http://" + getHost() + "/products/" + result.userId;
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", url)
                .header("Content-Location", url)
                .body(result);
    }

    @PostMapping(value = "/failure", consumes = "application/json")
    public ResponseEntity<Boolean> FailureHandle(@RequestBody PurchaseInfo purchaseInfo) {
        System.out.println("Broker has received failure handle request of user id " + purchaseInfo.userId);
        String productName = purchaseInfo.productName;

        // ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://localhost:61616");
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("failover://tcp://" + host + ":61616");
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);

            Queue failureHandleRequest;
            switch (productName) {
                case "bonds":
                    failureHandleRequest = session.createQueue("FAILUREBONDS");
                    break;
                case "funds":
                    failureHandleRequest = session.createQueue("FAILUREFUNDS");
                    break;
                case "stocks":
                    failureHandleRequest = session.createQueue("FAILURESTOCKS");
                    break;
                default:
                    throw new Exception("Invalid product type for failure handling");
            }
            MessageProducer producer = session.createProducer(failureHandleRequest);

            connection.start();
            producer.send(session.createObjectMessage(purchaseInfo));
            System.out.println("Broker has sent the failure handle request to " + purchaseInfo.productName);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("Location", (String) null)
                .header("Content-Location", (String) null)
                .body(true);
    }

    private String getHost() {
        try {
            return InetAddress.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            return "localhost:" + port;
        }
    }
}
